package com.zp.myhomemanager.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.zp.myhomemanager.utils.MyLog;

public class HomeFaceView extends View {

    private int backColor = 0xFFFFFFFF;


    private int vWidth, vHeight;

    private Paint ringPaint, circlePaint, linePaint;

    //move参数
    private int moveLeftX,moveLeftXMax,moveLeftY,moveLeftXMaY,moveRightX,moveRightXMax,moveRightY,moveRightXMaY;
    private int moveLeftXSpeed,moveLeftYSpeed,moveRightXSpeed,moveRightYSpeed;
    private int animDelayTime = 50;

    //line参数
    private Point[] leftPoints, rightPoints;
    private int leftLineType, rightLineType; //0-不封闭 1-封闭

    public static enum FACE_ANIM{NORMAL, LISTEN, HAPPY, LOOK_RIGHT, SUCCESS, MOVE, UNHAPPY};

    private FACE_ANIM status = FACE_ANIM.NORMAL;

    public HomeFaceView(Context context) {
        super(context);
    }

    public HomeFaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeFaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FACE_ANIM getStatus() {
        return status;
    }

    public void setStatus(FACE_ANIM status) {
        this.status = status;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (vWidth == 0) {
            vWidth = getWidth();
            vHeight = getHeight();

            ringPaint = new Paint();
            ringPaint.setColor(0xFF000000);
            ringPaint.setStyle(Paint.Style.STROKE);
            ringPaint.setStrokeWidth(3);

            circlePaint = new Paint();
            circlePaint.setColor(0xFF000000);
            circlePaint.setStyle(Paint.Style.FILL);

            linePaint = new Paint();
            linePaint.setColor(0xFF000000);
            linePaint.setStyle(Paint.Style.FILL);
            linePaint.setStrokeWidth(10);
            linePaint.setAntiAlias(true);
        }

        if(status == FACE_ANIM.NORMAL)
        {
            //normal模式
            float ringRadius = vHeight * 0.25f ;
            canvas.drawCircle(vWidth*0.5f+ringRadius, vHeight * 0.5f , ringRadius * 0.5f , ringPaint);
            canvas.drawCircle(vWidth*0.5f-ringRadius, vHeight * 0.5f , ringRadius * 0.5f , ringPaint);

            float cirRadius = ringRadius * 0.5f;
            canvas.drawCircle(vWidth*0.5f+ringRadius, vHeight * 0.5f , cirRadius * 0.5f , circlePaint);
            canvas.drawCircle(vWidth*0.5f-ringRadius, vHeight * 0.5f , cirRadius * 0.5f , circlePaint);
        }
        else if(status == FACE_ANIM.LOOK_RIGHT)
        {
            //look right模式
            float ringRadius = vHeight * 0.25f ;
            canvas.drawCircle(vWidth*0.33f+ringRadius, vHeight * 0.5f , ringRadius * 0.5f , ringPaint);
            canvas.drawCircle(vWidth*0.33f-ringRadius, vHeight * 0.5f , ringRadius * 0.5f , ringPaint);

            float cirRadius = ringRadius * 0.5f;
            canvas.drawCircle(vWidth*0.33f+ringRadius+cirRadius * 0.5f, vHeight * 0.5f , cirRadius * 0.5f , circlePaint);
            canvas.drawCircle(vWidth*0.33f-ringRadius+cirRadius * 0.5f, vHeight * 0.5f , cirRadius * 0.5f , circlePaint);
        }
        else if(status == FACE_ANIM.MOVE)
        {
            //move模式
            float ringRadius = vHeight * 0.25f ;
            canvas.drawCircle(vWidth*0.5f+ringRadius, vHeight * 0.5f , ringRadius * 0.5f , ringPaint);
            canvas.drawCircle(vWidth*0.5f-ringRadius, vHeight * 0.5f , ringRadius * 0.5f , ringPaint);

            float cirRadius = ringRadius * 0.5f;
            if(moveLeftX > moveLeftXMax)
            {
                moveLeftX = moveLeftXMax;
                moveLeftXSpeed = -moveLeftXSpeed;
                moveRightX = moveRightXMax;
                moveRightXSpeed = -moveRightXSpeed;
            }
            else if(moveLeftX < -moveLeftXMax)
            {
                moveLeftX = -moveLeftXMax;
                moveLeftXSpeed = -moveLeftXSpeed;
                moveRightX = -moveRightXMax;
                moveRightXSpeed = -moveRightXSpeed;
            }
            else
            {
                moveLeftX += moveLeftXSpeed;
                moveRightX += moveRightXSpeed;
            }

            canvas.drawCircle(vWidth*0.5f-ringRadius+moveLeftX, vHeight * 0.5f , cirRadius * 0.5f , circlePaint);
            canvas.drawCircle(vWidth*0.5f+ringRadius+moveRightX, vHeight * 0.5f , cirRadius * 0.5f , circlePaint);

            try {
                Thread.sleep(animDelayTime);
                updateCanvas();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else if(status == FACE_ANIM.UNHAPPY)
        {
            //unhappy模式
            float ringRadius = vHeight * 0.25f ;
            canvas.drawCircle(vWidth*0.5f+ringRadius, vHeight * 0.5f , ringRadius * 0.5f , ringPaint);
            canvas.drawCircle(vWidth*0.5f-ringRadius, vHeight * 0.5f , ringRadius * 0.5f , ringPaint);

            for(int i=0; i<leftPoints.length-1; i++)
            {
                canvas.drawLine(leftPoints[i].x, leftPoints[i].y, leftPoints[i+1].x, leftPoints[i+1].y , linePaint );
                canvas.drawLine(rightPoints[i].x, rightPoints[i].y, rightPoints[i+1].x, rightPoints[i+1].y , linePaint );
            }
        }
        else if(status == FACE_ANIM.LISTEN)
        {
            //listen模式
            float ringRadius = vHeight * 0.25f ;
            canvas.drawCircle(vWidth*0.5f+ringRadius, vHeight * 0.5f , ringRadius * 0.5f , ringPaint);
            canvas.drawCircle(vWidth*0.5f-ringRadius, vHeight * 0.5f , ringRadius * 0.5f , ringPaint);

            float cirRadius = ringRadius * 0.5f;
            canvas.drawCircle(vWidth*0.5f+ringRadius, vHeight * 0.5f - cirRadius * 0.5f , cirRadius * 0.5f , circlePaint);
            canvas.drawCircle(vWidth*0.5f-ringRadius, vHeight * 0.5f - cirRadius * 0.5f, cirRadius * 0.5f , circlePaint);
        }

    }

    public void updateCanvas() {

        invalidate();
    }

    public void SetFaceAnim(FACE_ANIM face_anim)
    {
        status = face_anim;
        MyLog.e(face_anim.name());
        switch (face_anim)
        {
            case LOOK_RIGHT:
                updateCanvas();
                break;

            case NORMAL:
                updateCanvas();
                break;

            case MOVE:
                moveLeftX = 0;
                moveRightX = 0;
                moveLeftXMax = (int) (vHeight * 0.25f * 0.5f * 0.5f);
                moveRightXMax = (int) (vHeight * 0.25f * 0.5f * 0.5f);
                moveLeftXSpeed = 5;
                moveRightXSpeed = 5;
                animDelayTime = 50;
                updateCanvas();
                break;

            case UNHAPPY:
                float ringRadius = vHeight * 0.25f ;
                leftPoints = new Point[3];
                leftLineType = 0;
                leftPoints[0] = new Point((int) (vWidth*0.5f-ringRadius-ringRadius*0.25f*0.71f),
                        (int) (vHeight * 0.5f-ringRadius*0.25f*0.71f));
                leftPoints[2] = new Point((int) (vWidth*0.5f-ringRadius-ringRadius*0.25f*0.71f),
                        (int) (vHeight * 0.5f+ringRadius*0.25f*0.71f));
                leftPoints[1] = new Point((int) (vWidth*0.5f-ringRadius+ringRadius*0.25f),
                        (int) (vHeight * 0.5f));

                rightPoints = new Point[3];
                rightLineType = 0;
                rightPoints[0] = new Point((int) (vWidth*0.5f+ringRadius+ringRadius*0.25f*0.71f),
                        (int) (vHeight * 0.5f-ringRadius*0.25f*0.71f));
                rightPoints[2] = new Point((int) (vWidth*0.5f+ringRadius+ringRadius*0.25f*0.71f),
                        (int) (vHeight * 0.5f+ringRadius*0.25f*0.71f));
                rightPoints[1] = new Point((int) (vWidth*0.5f+ringRadius-ringRadius*0.25f),
                        (int) (vHeight * 0.5f));
                updateCanvas();
                break;
                
            case LISTEN:
                updateCanvas();
                break;
        }
    }
}
