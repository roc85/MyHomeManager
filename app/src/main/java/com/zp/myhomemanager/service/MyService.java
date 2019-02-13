package com.zp.myhomemanager.service;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import com.baidu.speech.asr.SpeechConstant;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.google.gson.Gson;
import com.zp.myhomemanager.beans.FaceSwitchBean;
import com.zp.myhomemanager.beans.SwitchModeBean;
import com.zp.myhomemanager.beans.TestStrBean;
import com.zp.myhomemanager.beans.TrainDataBean;
import com.zp.myhomemanager.beans.WeatherDataBean;
import com.zp.myhomemanager.beans.ZhidaoData;
import com.zp.myhomemanager.global.App;
import com.zp.myhomemanager.global.GlobalParams;
import com.zp.myhomemanager.global.SettingSharedPreference;
import com.zp.myhomemanager.net.NetDataBase;
import com.zp.myhomemanager.recog.MyRecognizer;
import com.zp.myhomemanager.recog.listener.IRecogListener;
import com.zp.myhomemanager.recog.listener.MessageStatusRecogListener;
import com.zp.myhomemanager.screen_off.ScreenOffAdminReceiver;
import com.zp.myhomemanager.tts.AutoCheck;
import com.zp.myhomemanager.tts.InitConfig;
import com.zp.myhomemanager.tts.MySyntherizer;
import com.zp.myhomemanager.tts.NonBlockSyntherizer;
import com.zp.myhomemanager.tts.OfflineResource;
import com.zp.myhomemanager.tts.UiMessageListener;
import com.zp.myhomemanager.ui.activities.MainActivity;
import com.zp.myhomemanager.ui.activities.MainLifeActivity;
import com.zp.myhomemanager.ui.activities.MainWorkActivity;
import com.zp.myhomemanager.ui.views.HomeFaceView;
import com.zp.myhomemanager.utils.MyLog;
import com.zp.myhomemanager.utils.NetUtil;
import com.zp.myhomemanager.utils.TimeDateUtil;
import com.zp.myhomemanager.wakeup.IWakeupListener;
import com.zp.myhomemanager.wakeup.MyWakeup;
import com.zp.myhomemanager.wakeup.RecogWakeupListener;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.zp.myhomemanager.recog.IStatus.STATUS_FINISHED;
import static com.zp.myhomemanager.recog.IStatus.STATUS_READY;
import static com.zp.myhomemanager.recog.IStatus.STATUS_RECOGNITION;
import static com.zp.myhomemanager.recog.IStatus.STATUS_SPEAKING;
import static com.zp.myhomemanager.recog.IStatus.STATUS_SPEAK_FINISHED;
import static com.zp.myhomemanager.wakeup.IStatus.STATUS_WAKEUP_SUCCESS;
import static com.zp.myhomemanager.wakeup.IStatus.WHAT_MESSAGE_STATUS;

public class MyService extends Service implements GlobalParams {

    //region 百度语音相关变量

    //语音识别
    private MyWakeup myWakeup;
    protected MyRecognizer myRecognizer;
    /**
     * 0: 方案1， backTrackInMs > 0,唤醒词说完后，直接接句子，中间没有停顿。
     * 开启回溯，连同唤醒词一起整句识别。推荐4个字 1500ms
     * backTrackInMs 最大 15000，即15s
     * <p>
     * >0 : 方案2：backTrackInMs = 0，唤醒词说完后，中间有停顿。
     * 不开启回溯。唤醒词识别回调后，正常开启识别。
     * <p>
     */
    private int backTrackInMs = 0;

    //语音合成
    // TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
    protected TtsMode ttsMode = TtsMode.MIX;

    // 离线发音选择，VOICE_FEMALE即为离线女声发音。
    // assets目录下bd_etts_common_speech_m15_mand_eng_high_am-mix_v3.0.0_20170505.dat为离线男声模型；
    // assets目录下bd_etts_common_speech_f7_mand_eng_high_am-mix_v3.0.0_20170512.dat为离线女声模型
    protected String offlineVoice = OfflineResource.VOICE_MALE;

    // ===============初始化参数设置完毕，更多合成参数请至getParams()方法中设置 =================

    // 主控制类，所有合成控制方法从这个类开始
    protected MySyntherizer synthesizer;

    protected String appId = "";

    protected String appKey = "";

    protected String secretKey = "";

    //endregion

    public static final String TAG = "MyService";

    private App myApp;

    private FunctionThread functionThread;

    private boolean isWakeUp;
    private long wakeUpTime;

    private int errorOrder = 0, errorOrderMax = 2; //未识别指令，请求重说的次数

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyLog.i("启动服务MyService");

        myApp = (App) getApplication();

        initWakeUp();

        initialTts();

        //开启线程
        functionThread = new FunctionThread();
        functionThread.isRun = true;
        functionThread.start();

        ScreenWake(true);
    }

    @Override
    public void onDestroy() {

        functionThread.isRun = false;

        stop();
        stopTts();
        myRecognizer.release();
        myWakeup.release();
        synthesizer.release();
        MyLog.i("退出服务MyService");
        super.onDestroy();
    }


    //region 唤醒方法相关

    private void initWakeUp() {
        IWakeupListener listener = new RecogWakeupListener(mHandler);
        myWakeup = new MyWakeup(this, listener);

        IRecogListener recogListener = new MessageStatusRecogListener(mHandler);
        // 改为 SimpleWakeupListener 后，不依赖handler，但将不会在UI界面上显示
        myRecognizer = new MyRecognizer(this, recogListener);

        IWakeupListener listener2 = new RecogWakeupListener(mHandler);
        myWakeup.setEventListener(listener2); // 替换原来的 listener

        start();
    }

    // 点击“开始识别”按钮
    private void start() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(SpeechConstant.WP_WORDS_FILE, "assets:///WakeUp.bin");
        // "assets:///WakeUp.bin" 表示WakeUp.bin文件定义在assets目录下

        // params.put(SpeechConstant.ACCEPT_AUDIO_DATA,true);
        // params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME,true);
        // params.put(SpeechConstant.IN_FILE,"res:///com/baidu/android/voicedemo/wakeup.pcm");
        // params里 "assets:///WakeUp.bin" 表示WakeUp.bin文件定义在assets目录下
        myWakeup.start(params);
    }


    protected void stop() {
        myWakeup.stop();
        myRecognizer.stop();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        /*
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0:

                    break;

                case STATUS_WAKEUP_SUCCESS:
                    MyLog.i("SUCCESS", "唤醒成功");
                    //屏幕唤醒
                    PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                    PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
                            | PowerManager.SCREEN_DIM_WAKE_LOCK, "StartupReceiver");//最后的参数是LogCat里用的Tag
                    wl.acquire();

                    //屏幕解锁
                    KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
                    KeyguardManager.KeyguardLock kl = km.newKeyguardLock("StartupReceiver");//参数是LogCat里用的Tag
                    kl.disableKeyguard();

                    ScreenWake(true);

                    int mode = SettingSharedPreference.getDataInt(getApplicationContext(), MODE);
                    if (mode == 0) {
                        //家庭模式
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else if (mode == 1) {
                        //办公室工作模式
                        startActivity(new Intent(getApplicationContext(), MainWorkActivity.class));
                    } else if (mode == 2) {
                        //日常模式
                        startActivity(new Intent(getApplicationContext(), MainLifeActivity.class));
                    }

                    //开始语音识别流程
                    StartListenVoice();
                    break;

                case WHAT_MESSAGE_STATUS:
//                    MyLog.i("WHAT_MESSAGE_STATUS", (String) msg.obj);
                    break;

                case STATUS_READY:
                    MyLog.i("STATUS_READY", (String) msg.obj, 2);
                    break;
                case STATUS_RECOGNITION:
                    MyLog.i("STATUS_RECOGNITION", (String) msg.obj, 2);
                    break;
                case STATUS_SPEAKING:
                    MyLog.i("STATUS_SPEAKING", (String) msg.obj, 2);
                    break;
                case STATUS_SPEAK_FINISHED:
                    if (errorOrder > 0) {
                        errorOrder--;
                        StartListenVoice();
                    }

                    break;
                case STATUS_FINISHED:
                    MyLog.i("STATUS_FINISHED", (String) msg.obj, 5);

                    EventBus.getDefault().post(new FaceSwitchBean(HomeFaceView.FACE_ANIM.NORMAL));

                    String res = (String) msg.obj;
                    if (res.startsWith("res:")) {
                        res = res.substring(4);

                        res = res.replace("\n", "");

                        ProcessOrders(res);

//                        EventBus.getDefault().post(new TestStrBean(res));
                    }
                    break;

                default:

                    break;
            }
        }

    };

    //设置屏幕亮起熄灭的标志和时间
    private void ScreenWake(boolean wake) {
        isWakeUp = wake;
        wakeUpTime = System.currentTimeMillis();
    }

    //endregion

    //region 语音合成相关

    /**
     * 初始化引擎，需要的参数均在InitConfig类里
     * <p>
     * DEMO中提供了3个SpeechSynthesizerListener的实现
     * MessageListener 仅仅用log.i记录日志，在logcat中可以看见
     * UiMessageListener 在MessageListener的基础上，对handler发送消息，实现UI的文字更新
     * FileSaveListener 在UiMessageListener的基础上，使用 onSynthesizeDataArrived回调，获取音频流
     */
    @SuppressLint("HandlerLeak")
    protected void initialTts() {
        LoggerProxy.printable(true); // 日志打印在logcat中
        // 设置初始化参数
        // 此处可以改为 含有您业务逻辑的SpeechSynthesizerListener的实现类
        SpeechSynthesizerListener listener = new UiMessageListener(mHandler);

        Map<String, String> params = getParams();

        ApplicationInfo appInfo = null;
        try {
            appInfo = this.getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            appId = appInfo.metaData.getString("com.baidu.speech.APP_ID");
            appKey = appInfo.metaData.getString("com.baidu.speech.API_KEY");
            secretKey = appInfo.metaData.getString("com.baidu.speech.SECRET_KEY");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // appId appKey secretKey 网站上您申请的应用获取。注意使用离线合成功能的话，需要应用中填写您app的包名。包名在build.gradle中获取。
        InitConfig initConfig = new InitConfig(appId, appKey, secretKey, ttsMode, params, listener);

        // 如果您集成中出错，请将下面一段代码放在和demo中相同的位置，并复制InitConfig 和 AutoCheck到您的项目中
        // 上线时请删除AutoCheck的调用
        AutoCheck.getInstance(getApplicationContext()).check(initConfig, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 100) {
                    AutoCheck autoCheck = (AutoCheck) msg.obj;
                    synchronized (autoCheck) {
                        String message = autoCheck.obtainDebugMessage();
                        MyLog.w("AutoCheckMessage", message);
                    }
                }
            }

        });
        synthesizer = new NonBlockSyntherizer(this, initConfig, mHandler); // 此处可以改为MySyntherizer 了解调用过程
    }


    /**
     * 合成的参数，可以初始化时填写，也可以在合成前设置。
     *
     * @return
     */
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        // 以下参数均为选填
        // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        params.put(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 设置合成的音量，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_VOLUME, "9");
        // 设置合成的语速，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_SPEED, "5");
        // 设置合成的语调，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_PITCH, "5");

        params.put(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        // 该参数设置为TtsMode.MIX生效。即纯在线模式不生效。
        // MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线

        // 离线资源文件， 从assets目录中复制到临时目录，需要在initTTs方法前完成
        OfflineResource offlineResource = createOfflineResource(offlineVoice);
        // 声学模型文件路径 (离线引擎使用), 请确认下面两个文件存在
        params.put(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, offlineResource.getTextFilename());
        params.put(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE,
                offlineResource.getModelFilename());
        return params;
    }


    protected OfflineResource createOfflineResource(String voiceType) {
        OfflineResource offlineResource = null;
        try {
            offlineResource = new OfflineResource(this, voiceType);
        } catch (IOException e) {
            // IO 错误自行处理
            e.printStackTrace();
            MyLog.e("【error】:copy files from assets failed." + e.getMessage());
        }
        return offlineResource;
    }

    /**
     * speak 实际上是调用 synthesize后，获取音频流，然后播放。
     * 获取音频流的方式见SaveFileActivity及FileSaveListener
     * 需要合成的文本text的长度不能超过1024个GBK字节。
     */
    private void speak(String text) {
        // 需要合成的文本text的长度不能超过1024个GBK字节。

        // 合成前可以修改参数：
        // Map<String, String> params = getParams();
        // synthesizer.setParams(params);
        int result = synthesizer.speak(text);
        checkResult(result, "speak");
    }


    /**
     * 合成但是不播放，
     * 音频流保存为文件的方法可以参见SaveFileActivity及FileSaveListener
     */
    private void synthesize() {
        String text = "";
        int result = synthesizer.synthesize(text);
        checkResult(result, "synthesize");
    }

    /**
     * 批量播放
     */
    private void batchSpeak() {
        List<Pair<String, String>> texts = new ArrayList<Pair<String, String>>();
        texts.add(new Pair<String, String>("开始批量播放，", "a0"));
        texts.add(new Pair<String, String>("123456，", "a1"));
        texts.add(new Pair<String, String>("欢迎使用百度语音，，，", "a2"));
        texts.add(new Pair<String, String>("重(chong2)量这个是多音字示例", "a3"));
        int result = synthesizer.batchSpeak(texts);
        checkResult(result, "batchSpeak");
    }


    /**
     * 切换离线发音。注意需要添加额外的判断：引擎在合成时该方法不能调用
     */
    private void loadModel(String mode) {
        offlineVoice = mode;
        OfflineResource offlineResource = createOfflineResource(offlineVoice);
        MyLog.i("切换离线语音：" + offlineResource.getModelFilename());
        int result = synthesizer.loadModel(offlineResource.getModelFilename(), offlineResource.getTextFilename());
        checkResult(result, "loadModel");
    }

    private void checkResult(int result, String method) {
        if (result != 0) {
            MyLog.e("error code :" + result + " method:" + method + ", 错误码文档:http://yuyin.baidu.com/docs/tts/122 ");
        }
    }


    /**
     * 暂停播放。仅调用speak后生效
     */
    private void pause() {
        int result = synthesizer.pause();
        checkResult(result, "pause");
    }

    /**
     * 继续播放。仅调用speak后生效，调用pause生效
     */
    private void resume() {
        int result = synthesizer.resume();
        checkResult(result, "resume");
    }

    /*
     * 停止合成引擎。即停止播放，合成，清空内部合成队列。
     */
    private void stopTts() {
        int result = synthesizer.stop();
        checkResult(result, "stop");
    }


    //endregion

    //region 工作线程方法

    private class FunctionThread extends Thread {
        //用于控制线程开启关闭
        protected boolean isRun;

        @Override
        public void run() {
            super.run();

            //完成线程内功能
            while (isRun) {
                long nowTime = System.currentTimeMillis();
                if (isWakeUp && nowTime - wakeUpTime > WAKE_UP_WAIT_TIME) {
                    if(SettingSharedPreference.getDataInt(getApplication(), MODE) == 0)
                    {
                        ScreenOff();
                    }

                }

            }
        }

    }

    //endregion

    //region 语音识别相关
    private void StartListenVoice() {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
        params.put(SpeechConstant.VAD, SpeechConstant.VAD_DNN);
        // 如识别短句，不需要需要逗号，使用1536搜索模型。其它PID参数请看文档
        params.put(SpeechConstant.PID, 19361);
        if (backTrackInMs > 0) {
            // 方案1  唤醒词说完后，直接接句子，中间没有停顿。开启回溯，连同唤醒词一起整句识别。
            // System.currentTimeMillis() - backTrackInMs ,  表示识别从backTrackInMs毫秒前开始
            params.put(SpeechConstant.AUDIO_MILLS, System.currentTimeMillis() - backTrackInMs);
        }
        myRecognizer.cancel();
        myRecognizer.start(params);

        EventBus.getDefault().post(new FaceSwitchBean(HomeFaceView.FACE_ANIM.LISTEN));
    }

    //endregion

    private void ScreenOff() {
        DevicePolicyManager policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName adminReceiver = new ComponentName(MyService.this, ScreenOffAdminReceiver.class);
        boolean admin = policyManager.isAdminActive(adminReceiver);
        if (admin) {
            policyManager.lockNow();
            ScreenWake(false);
        } else {
            EventBus.getDefault().post(new TestStrBean("没有设备管理权限!"));
        }
    }

    //指令分析处理方法
    private void ProcessOrders(String res) {

        //去除逗号
        res = res.replace(",", "");
        res = res.replace("，", "");
        String orderParam = null;
        switch (AnalyseStr(res)) {
            case WEATHER:
                //天气查询指令
                orderParam = res.replaceFirst(WEATHER_QUERY_ORDER[0], "");
                orderParam = orderParam.replaceFirst("的" + WEATHER_QUERY_ORDER[1], "");
                orderParam = orderParam.replaceFirst(WEATHER_QUERY_ORDER[1], "");

                if (!TextUtils.isEmpty(orderParam)) {
                    if (orderParam.equals(ORDER_TODAY)) {
                        orderParam = myApp.getLocalCityName();
                    }

                    GetWeatherDatas(orderParam);
                }
                break;

            case ERROR:
//                NotUseOrder();
                if(SettingSharedPreference.getDataBoolean(getApplication(), BAIDU_ZHIDAO_MODE, true))
                {
                    SearchZhidao(res);
                }
                else
                {
                    NotUseOrder();
                }

                break;

            case DATE:
                //现在时间指令
                speak("今天是" + TimeDateUtil.formatDateTime(System.currentTimeMillis(), TimeDateUtil.TALK_YYYY_MM_DD) + TimeDateUtil.getWeek(System.currentTimeMillis()));
                break;

            case EXIT:
                //息屏指令
                ScreenOff();
                break;

            case TIME:
                //现在时间指令
                speak("现在时间是" + TimeDateUtil.formatDateTime(System.currentTimeMillis(), TimeDateUtil.TALK_HH_MM_SS));
                break;

            case TRAIN:
                //火车票查询指令
                orderParam = res.replaceFirst(TRAIN_QUERY_ORDER[0], "");
                orderParam = orderParam.replaceFirst(TRAIN_QUERY_ORDER[1], "");
                if (!TextUtils.isEmpty(orderParam)) {
                    String[] params = orderParam.split("到");
                    if (params.length == 2 && !TextUtils.isEmpty(params[0]) && !TextUtils.isEmpty(params[1])) {
                        GetTrainDatas(params);
                    }

                }
                break;
            case SWITCH_MODE:
                int modeTmp = -1;
                for(int i=0; i<ORDER_SWITCH_MODE.length; i++ )
                {
                    if(res.contains(ORDER_SWITCH_MODE[i]))
                    {
                        modeTmp = i;
                        break;
                    }
                }

                if(modeTmp>=0 && modeTmp<ORDER_SWITCH_MODE.length)
                {
                    if(modeTmp == SettingSharedPreference.getDataInt(getApplication(), MODE))
                    {
                        speak("当前就在该模式下，无需切换");
                    }
                    else
                    {
                        ShowModeSwitchConfirm(modeTmp);
                    }

                }
                else
                {
                    NotUseOrder();
                }
                break;
            default:
                NotUseOrder();
                break;
        }


    }

    //判断指令所属类型
    private ORDER_TYPE AnalyseStr(String res) {
        if (IsThisOrder(res, WEATHER_QUERY_ORDER, 1)) {
            return ORDER_TYPE.WEATHER;
        } else if (IsThisOrder(res, ORDER_BYEBYE, 0)) {
            return ORDER_TYPE.EXIT;
        } else if (IsThisOrder(res, NOW_TIME_ORDER, 2)) {
            return ORDER_TYPE.TIME;
        } else if (IsThisOrder(res, NOW_DATE_ORDER, 2)) {
            return ORDER_TYPE.DATE;
        } else if (IsThisOrder(res, ORDER_SWITCH_MODE, 2)) {

            return ORDER_TYPE.SWITCH_MODE;
        }

        else {
            return ORDER_TYPE.ERROR;
        }
    }

    /*
     *  判断命令与预留指令间的关系
     *  @params type 0-以相关指令开头 1-包含该指令所有参数 2-包含该指令某一个参数
     */
    private boolean IsThisOrder(String res, Object o, int type) {
        boolean tf = false;
        String[] orders = null;
        if (o instanceof String[]) {
            orders = (String[]) o;
        } else if (o instanceof String) {
            orders = new String[]{(String) o};

        } else {
            return false;
        }

        if (type == 0) {
            for (int i = 0; i < orders.length; i++) {
                if (res.startsWith(orders[i])) {
                    tf = true;
                    break;
                }
            }

        } else if (type == 1) {
            tf = true;
            for (int i = 0; i < orders.length; i++) {
                if (!res.contains(orders[i])) {
                    tf = false;
                    break;
                }
            }
        } else if (type == 2) {

            for (int i = 0; i < orders.length; i++) {
                if (res.contains(orders[i])) {
                    tf = true;
                    break;
                }
            }
        }

        return tf;
    }

    //获取火车票数据
    private void GetTrainDatas(String[] params) {
        NetDataBase.TransDatas(MOB_TRAIN_URL + params[0] + MOB_TRAIN_URL2 + params[1], null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                NotUseOrder();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                MyLog.i(TAG + ":weather", res);

                Gson gson = new Gson();
                TrainDataBean tdb = gson.fromJson(res, TrainDataBean.class);

                if ("success".equals(tdb.getMsg())) {
                    //查询成功
                    MyLog.i(TAG, "查询火车票成功");
                    EventBus.getDefault().post(tdb);
                } else {
                    NotUseOrder();
                }
            }
        });
    }

    //获取天气数据
    private void GetWeatherDatas(String orderParam) {
        NetDataBase.TransDatas(MOB_WEATHER_URL + orderParam, null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                NotUseOrder();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                MyLog.i(TAG + ":weather", res);

                Gson gson = new Gson();
                WeatherDataBean wdb = gson.fromJson(res, WeatherDataBean.class);

                if ("success".equals(wdb.getMsg())) {
                    //查询成功
                    MyLog.i(TAG, "查询天气成功");
                    EventBus.getDefault().post(wdb);
                } else {
                    NotUseOrder();
                }


            }
        });
    }

    private void NotUseOrder() {
        EventBus.getDefault().post(new TestStrBean("指令无法识别或有误!"));

        if (errorOrder == 0) {
            errorOrder = errorOrderMax;
            speak("我没有听清，请重说");
        } else if (errorOrder == 1) {
            errorOrder = 0;
            speak("还是没有听清，再见了");
        }

    }

    private void SearchZhidao(String res) {
        GetHtml(res);
    }

    //获取网页源代码，依据百度知道代码格式获取有效信息
    private void GetHtml(final String keyword) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<ZhidaoData> resList = new ArrayList<>();
                    String res =
                            NetUtil.getHTML("https://zhidao.baidu.com/search?ie=utf-8&word=" + keyword, "UTF-8");
                    int index = res.indexOf("t-slist");
                    while (index >= 0) {
                        res = res.substring(index);
                        index = res.indexOf("</div>");
                        ZhidaoData zdd = new ZhidaoData(res.substring(0, index));
                        if (zdd.isValid()) {
                            resList.add(zdd);
                        }
                        res = res.substring(index + 6);

                        index = res.indexOf("t-slist");
                    }
                    Log.e("", res);

                    int i = (int) (resList.size() * Math.random());
                    speak(resList.get(i).getCon());
                    EventBus.getDefault().post(resList.get(i));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //确认是否进行模式切换
    private void ShowModeSwitchConfirm(final int modeIndex) {
        EventBus.getDefault().post(new SwitchModeBean(modeIndex));

    }
}

