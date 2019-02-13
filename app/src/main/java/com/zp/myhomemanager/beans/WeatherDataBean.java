package com.zp.myhomemanager.beans;

import java.util.List;

public class WeatherDataBean {


    /**
     * msg : success
     * result : [{"airCondition":"中度污染","airQuality":{"aqi":155,"city":"南京","district":"南京","fetureData":[{"aqi":166,"date":"2018-11-27","quality":"中度污染"},{"aqi":135,"date":"2018-11-28","quality":"轻度污染"},{"aqi":121,"date":"2018-11-29","quality":"轻度污染"},{"aqi":104,"date":"2018-11-30","quality":"轻度污染"},{"aqi":109,"date":"2018-12-01","quality":"轻度污染"}],"hourData":[{"aqi":155,"dateTime":"2018-11-26 12:00:00"},{"aqi":124,"dateTime":"2018-11-26 11:00:00"},{"aqi":109,"dateTime":"2018-11-26 10:00:00"},{"aqi":103,"dateTime":"2018-11-26 09:00:00"},{"aqi":107,"dateTime":"2018-11-26 08:00:00"},{"aqi":122,"dateTime":"2018-11-26 07:00:00"},{"aqi":135,"dateTime":"2018-11-26 06:00:00"},{"aqi":144,"dateTime":"2018-11-26 05:00:00"},{"aqi":155,"dateTime":"2018-11-26 04:00:00"},{"aqi":166,"dateTime":"2018-11-26 03:00:00"},{"aqi":178,"dateTime":"2018-11-26 02:00:00"},{"aqi":185,"dateTime":"2018-11-26 01:00:00"},{"aqi":192,"dateTime":"2018-11-26 00:00:00"},{"aqi":190,"dateTime":"2018-11-25 23:00:00"},{"aqi":193,"dateTime":"2018-11-25 22:00:00"},{"aqi":186,"dateTime":"2018-11-25 21:00:00"},{"aqi":170,"dateTime":"2018-11-25 20:00:00"},{"aqi":139,"dateTime":"2018-11-25 19:00:00"},{"aqi":120,"dateTime":"2018-11-25 18:00:00"},{"aqi":115,"dateTime":"2018-11-25 17:00:00"},{"aqi":122,"dateTime":"2018-11-25 16:00:00"},{"aqi":122,"dateTime":"2018-11-25 15:00:00"},{"aqi":132,"dateTime":"2018-11-25 14:00:00"},{"aqi":149,"dateTime":"2018-11-25 13:00:00"}],"no2":74,"pm10":173,"pm25":118,"province":"江苏","quality":"中度污染","so2":7,"updateTime":"2018-11-26 13:00:00"},"city":"南京","coldIndex":"低发期","date":"2018-11-26","distrct":"南京","dressingIndex":"夹衣类","exerciseIndex":"非常适宜","future":[{"date":"2018-11-26","dayTime":"多云","night":"多云","temperature":"18°C / 8°C","week":"今天","wind":"南风 小于3级"},{"date":"2018-11-27","dayTime":"多云","night":"多云","temperature":"18°C / 7°C","week":"星期二","wind":"东风 小于3级"},{"date":"2018-11-28","dayTime":"多云","night":"多云","temperature":"17°C / 8°C","week":"星期三","wind":"东南风 小于3级"},{"date":"2018-11-29","dayTime":"多云","night":"多云","temperature":"18°C / 9°C","week":"星期四","wind":"东风 3～4级"},{"date":"2018-11-30","dayTime":"多云","night":"阴","temperature":"17°C / 9°C","week":"星期五","wind":"东南风 3～4级"},{"date":"2018-12-01","dayTime":"多云","night":"多云","temperature":"17°C / 9°C","week":"星期六","wind":"东北风 3～4级"}],"humidity":"湿度：53%","pollutionIndex":"155","province":"江苏","sunrise":"05:45","sunset":"18:19","temperature":"17℃","time":"13:41","updateTime":"20181126135946","washIndex":"不适宜","weather":"晴","week":"周一","wind":"西南风1级"}]
     * retCode : 200
     */

    private String msg;
    private String retCode;
    private List<ResultBean> result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * airCondition : 中度污染
         * airQuality : {"aqi":155,"city":"南京","district":"南京","fetureData":[{"aqi":166,"date":"2018-11-27","quality":"中度污染"},{"aqi":135,"date":"2018-11-28","quality":"轻度污染"},{"aqi":121,"date":"2018-11-29","quality":"轻度污染"},{"aqi":104,"date":"2018-11-30","quality":"轻度污染"},{"aqi":109,"date":"2018-12-01","quality":"轻度污染"}],"hourData":[{"aqi":155,"dateTime":"2018-11-26 12:00:00"},{"aqi":124,"dateTime":"2018-11-26 11:00:00"},{"aqi":109,"dateTime":"2018-11-26 10:00:00"},{"aqi":103,"dateTime":"2018-11-26 09:00:00"},{"aqi":107,"dateTime":"2018-11-26 08:00:00"},{"aqi":122,"dateTime":"2018-11-26 07:00:00"},{"aqi":135,"dateTime":"2018-11-26 06:00:00"},{"aqi":144,"dateTime":"2018-11-26 05:00:00"},{"aqi":155,"dateTime":"2018-11-26 04:00:00"},{"aqi":166,"dateTime":"2018-11-26 03:00:00"},{"aqi":178,"dateTime":"2018-11-26 02:00:00"},{"aqi":185,"dateTime":"2018-11-26 01:00:00"},{"aqi":192,"dateTime":"2018-11-26 00:00:00"},{"aqi":190,"dateTime":"2018-11-25 23:00:00"},{"aqi":193,"dateTime":"2018-11-25 22:00:00"},{"aqi":186,"dateTime":"2018-11-25 21:00:00"},{"aqi":170,"dateTime":"2018-11-25 20:00:00"},{"aqi":139,"dateTime":"2018-11-25 19:00:00"},{"aqi":120,"dateTime":"2018-11-25 18:00:00"},{"aqi":115,"dateTime":"2018-11-25 17:00:00"},{"aqi":122,"dateTime":"2018-11-25 16:00:00"},{"aqi":122,"dateTime":"2018-11-25 15:00:00"},{"aqi":132,"dateTime":"2018-11-25 14:00:00"},{"aqi":149,"dateTime":"2018-11-25 13:00:00"}],"no2":74,"pm10":173,"pm25":118,"province":"江苏","quality":"中度污染","so2":7,"updateTime":"2018-11-26 13:00:00"}
         * city : 南京
         * coldIndex : 低发期
         * date : 2018-11-26
         * distrct : 南京
         * dressingIndex : 夹衣类
         * exerciseIndex : 非常适宜
         * future : [{"date":"2018-11-26","dayTime":"多云","night":"多云","temperature":"18°C / 8°C","week":"今天","wind":"南风 小于3级"},{"date":"2018-11-27","dayTime":"多云","night":"多云","temperature":"18°C / 7°C","week":"星期二","wind":"东风 小于3级"},{"date":"2018-11-28","dayTime":"多云","night":"多云","temperature":"17°C / 8°C","week":"星期三","wind":"东南风 小于3级"},{"date":"2018-11-29","dayTime":"多云","night":"多云","temperature":"18°C / 9°C","week":"星期四","wind":"东风 3～4级"},{"date":"2018-11-30","dayTime":"多云","night":"阴","temperature":"17°C / 9°C","week":"星期五","wind":"东南风 3～4级"},{"date":"2018-12-01","dayTime":"多云","night":"多云","temperature":"17°C / 9°C","week":"星期六","wind":"东北风 3～4级"}]
         * humidity : 湿度：53%
         * pollutionIndex : 155
         * province : 江苏
         * sunrise : 05:45
         * sunset : 18:19
         * temperature : 17℃
         * time : 13:41
         * updateTime : 20181126135946
         * washIndex : 不适宜
         * weather : 晴
         * week : 周一
         * wind : 西南风1级
         */

        private String airCondition;
        private AirQualityBean airQuality;
        private String city;
        private String coldIndex;
        private String date;
        private String distrct;
        private String dressingIndex;
        private String exerciseIndex;
        private String humidity;
        private String pollutionIndex;
        private String province;
        private String sunrise;
        private String sunset;
        private String temperature;
        private String time;
        private String updateTime;
        private String washIndex;
        private String weather;
        private String week;
        private String wind;
        private List<FutureBean> future;

        public String getAirCondition() {
            return airCondition;
        }

        public void setAirCondition(String airCondition) {
            this.airCondition = airCondition;
        }

        public AirQualityBean getAirQuality() {
            return airQuality;
        }

        public void setAirQuality(AirQualityBean airQuality) {
            this.airQuality = airQuality;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getColdIndex() {
            return coldIndex;
        }

        public void setColdIndex(String coldIndex) {
            this.coldIndex = coldIndex;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDistrct() {
            return distrct;
        }

        public void setDistrct(String distrct) {
            this.distrct = distrct;
        }

        public String getDressingIndex() {
            return dressingIndex;
        }

        public void setDressingIndex(String dressingIndex) {
            this.dressingIndex = dressingIndex;
        }

        public String getExerciseIndex() {
            return exerciseIndex;
        }

        public void setExerciseIndex(String exerciseIndex) {
            this.exerciseIndex = exerciseIndex;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getPollutionIndex() {
            return pollutionIndex;
        }

        public void setPollutionIndex(String pollutionIndex) {
            this.pollutionIndex = pollutionIndex;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getSunrise() {
            return sunrise;
        }

        public void setSunrise(String sunrise) {
            this.sunrise = sunrise;
        }

        public String getSunset() {
            return sunset;
        }

        public void setSunset(String sunset) {
            this.sunset = sunset;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getWashIndex() {
            return washIndex;
        }

        public void setWashIndex(String washIndex) {
            this.washIndex = washIndex;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getWind() {
            return wind;
        }

        public void setWind(String wind) {
            this.wind = wind;
        }

        public List<FutureBean> getFuture() {
            return future;
        }

        public void setFuture(List<FutureBean> future) {
            this.future = future;
        }

        public static class AirQualityBean {
            /**
             * aqi : 155
             * city : 南京
             * district : 南京
             * fetureData : [{"aqi":166,"date":"2018-11-27","quality":"中度污染"},{"aqi":135,"date":"2018-11-28","quality":"轻度污染"},{"aqi":121,"date":"2018-11-29","quality":"轻度污染"},{"aqi":104,"date":"2018-11-30","quality":"轻度污染"},{"aqi":109,"date":"2018-12-01","quality":"轻度污染"}]
             * hourData : [{"aqi":155,"dateTime":"2018-11-26 12:00:00"},{"aqi":124,"dateTime":"2018-11-26 11:00:00"},{"aqi":109,"dateTime":"2018-11-26 10:00:00"},{"aqi":103,"dateTime":"2018-11-26 09:00:00"},{"aqi":107,"dateTime":"2018-11-26 08:00:00"},{"aqi":122,"dateTime":"2018-11-26 07:00:00"},{"aqi":135,"dateTime":"2018-11-26 06:00:00"},{"aqi":144,"dateTime":"2018-11-26 05:00:00"},{"aqi":155,"dateTime":"2018-11-26 04:00:00"},{"aqi":166,"dateTime":"2018-11-26 03:00:00"},{"aqi":178,"dateTime":"2018-11-26 02:00:00"},{"aqi":185,"dateTime":"2018-11-26 01:00:00"},{"aqi":192,"dateTime":"2018-11-26 00:00:00"},{"aqi":190,"dateTime":"2018-11-25 23:00:00"},{"aqi":193,"dateTime":"2018-11-25 22:00:00"},{"aqi":186,"dateTime":"2018-11-25 21:00:00"},{"aqi":170,"dateTime":"2018-11-25 20:00:00"},{"aqi":139,"dateTime":"2018-11-25 19:00:00"},{"aqi":120,"dateTime":"2018-11-25 18:00:00"},{"aqi":115,"dateTime":"2018-11-25 17:00:00"},{"aqi":122,"dateTime":"2018-11-25 16:00:00"},{"aqi":122,"dateTime":"2018-11-25 15:00:00"},{"aqi":132,"dateTime":"2018-11-25 14:00:00"},{"aqi":149,"dateTime":"2018-11-25 13:00:00"}]
             * no2 : 74
             * pm10 : 173
             * pm25 : 118
             * province : 江苏
             * quality : 中度污染
             * so2 : 7
             * updateTime : 2018-11-26 13:00:00
             */

            private int aqi;
            private String city;
            private String district;
            private int no2;
            private int pm10;
            private int pm25;
            private String province;
            private String quality;
            private int so2;
            private String updateTime;
            private List<FetureDataBean> fetureData;
            private List<HourDataBean> hourData;

            public int getAqi() {
                return aqi;
            }

            public void setAqi(int aqi) {
                this.aqi = aqi;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public int getNo2() {
                return no2;
            }

            public void setNo2(int no2) {
                this.no2 = no2;
            }

            public int getPm10() {
                return pm10;
            }

            public void setPm10(int pm10) {
                this.pm10 = pm10;
            }

            public int getPm25() {
                return pm25;
            }

            public void setPm25(int pm25) {
                this.pm25 = pm25;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getQuality() {
                return quality;
            }

            public void setQuality(String quality) {
                this.quality = quality;
            }

            public int getSo2() {
                return so2;
            }

            public void setSo2(int so2) {
                this.so2 = so2;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public List<FetureDataBean> getFetureData() {
                return fetureData;
            }

            public void setFetureData(List<FetureDataBean> fetureData) {
                this.fetureData = fetureData;
            }

            public List<HourDataBean> getHourData() {
                return hourData;
            }

            public void setHourData(List<HourDataBean> hourData) {
                this.hourData = hourData;
            }

            public static class FetureDataBean {
                /**
                 * aqi : 166
                 * date : 2018-11-27
                 * quality : 中度污染
                 */

                private int aqi;
                private String date;
                private String quality;

                public int getAqi() {
                    return aqi;
                }

                public void setAqi(int aqi) {
                    this.aqi = aqi;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public String getQuality() {
                    return quality;
                }

                public void setQuality(String quality) {
                    this.quality = quality;
                }
            }

            public static class HourDataBean {
                /**
                 * aqi : 155
                 * dateTime : 2018-11-26 12:00:00
                 */

                private int aqi;
                private String dateTime;

                public int getAqi() {
                    return aqi;
                }

                public void setAqi(int aqi) {
                    this.aqi = aqi;
                }

                public String getDateTime() {
                    return dateTime;
                }

                public void setDateTime(String dateTime) {
                    this.dateTime = dateTime;
                }
            }
        }

        public static class FutureBean {
            /**
             * date : 2018-11-26
             * dayTime : 多云
             * night : 多云
             * temperature : 18°C / 8°C
             * week : 今天
             * wind : 南风 小于3级
             */

            private String date;
            private String dayTime;
            private String night;
            private String temperature;
            private String week;
            private String wind;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDayTime() {
                return dayTime;
            }

            public void setDayTime(String dayTime) {
                this.dayTime = dayTime;
            }

            public String getNight() {
                return night;
            }

            public void setNight(String night) {
                this.night = night;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getWind() {
                return wind;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }
        }
    }
}
