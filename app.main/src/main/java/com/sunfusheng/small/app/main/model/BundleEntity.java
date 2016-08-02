package com.sunfusheng.small.app.main.model;

import java.io.Serializable;

/**
 * Created by sunfusheng on 16/8/2.
 */
public class BundleEntity implements Serializable {

    private String uri;
    private String pkg;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

}
