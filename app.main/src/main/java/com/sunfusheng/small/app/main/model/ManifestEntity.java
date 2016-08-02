package com.sunfusheng.small.app.main.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunfusheng on 16/8/2.
 */
public class ManifestEntity implements Serializable {

    private String version;

    private List<BundleEntity> bundles;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<BundleEntity> getBundles() {
        return bundles;
    }

    public void setBundles(List<BundleEntity> bundles) {
        this.bundles = bundles;
    }

}
