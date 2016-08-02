package com.sunfusheng.small.app.main.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunfusheng on 16/8/2.
 */
public class PluginEntity implements Serializable {

    private int manifest_code;
    private int updates_code;
    private int additions_code;
    private ManifestEntity manifest;
    private List<BundleEntity> updates;
    private List<BundleEntity> additions;

    public int getManifest_code() {
        return manifest_code;
    }

    public void setManifest_code(int manifest_code) {
        this.manifest_code = manifest_code;
    }

    public int getUpdates_code() {
        return updates_code;
    }

    public void setUpdates_code(int updates_code) {
        this.updates_code = updates_code;
    }

    public int getAdditions_code() {
        return additions_code;
    }

    public void setAdditions_code(int additions_code) {
        this.additions_code = additions_code;
    }

    public ManifestEntity getManifest() {
        return manifest;
    }

    public void setManifest(ManifestEntity manifest) {
        this.manifest = manifest;
    }

    public List<BundleEntity> getUpdates() {
        return updates;
    }

    public void setUpdates(List<BundleEntity> updates) {
        this.updates = updates;
    }

    public List<BundleEntity> getAdditions() {
        return additions;
    }

    public void setAdditions(List<BundleEntity> additions) {
        this.additions = additions;
    }

}
