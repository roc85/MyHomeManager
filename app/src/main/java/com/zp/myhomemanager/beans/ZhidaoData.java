package com.zp.myhomemanager.beans;

import android.text.TextUtils;

public class ZhidaoData {
    private String title;
    private String con;
    private String urlPath;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public boolean isValid()
    {
        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(con) && !TextUtils.isEmpty(urlPath))
        {
            return true;
        }
        return false;

    }

    public ZhidaoData() {

    }

    public ZhidaoData(String src) {
        int start = -1, end = -1;
        start = src.indexOf("<p>");
        if (start >= 0) {
            end = src.substring(start).indexOf("</p>") + start;
        }
        if (start >= 0 && end >= 0) {
            this.con = src.substring(start + 3, end);
        }

        start = src.indexOf("href=\"");
        end = src.indexOf("?");
        if (start >= 0 && end >= 0) {
            this.urlPath = src.substring(start + 6, end);
        }
        start = src.indexOf("step=1\">");
        end = src.indexOf("</a>");
        if (start >= 0 && end >= 0) {
            this.title = src.substring(start + 8, end);
            this.title = this.title.replace("</span>", "");
            this.title = this.title.replace("<span class=\"cred\">", "");

        }
    }
}
