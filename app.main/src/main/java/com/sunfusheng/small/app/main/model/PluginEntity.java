package com.sunfusheng.small.app.main.model;

import java.io.Serializable;

/**
 * Created by sunfusheng on 16/8/3.
 */
public class PluginEntity implements Serializable {

    private String pkg;
    private String url;

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
