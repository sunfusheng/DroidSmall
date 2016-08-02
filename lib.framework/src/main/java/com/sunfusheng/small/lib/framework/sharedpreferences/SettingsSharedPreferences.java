package com.sunfusheng.small.lib.framework.sharedpreferences;

import de.devland.esperandro.SharedPreferenceActions;
import de.devland.esperandro.SharedPreferenceMode;
import de.devland.esperandro.annotations.SharedPreferences;

/**
 * Created by sunfusheng on 2015/6/25.
 */
@SharedPreferences(name = "settings", mode = SharedPreferenceMode.PRIVATE)
public interface SettingsSharedPreferences extends SharedPreferenceActions {

    String plugin_bundles();
    void plugin_bundles(String plugin_bundles);

    int manifest_code();
    void manifest_code(int manifest_code);

    int updates_code();
    void updates_code(int updates_code);

    int additions_code();
    void additions_code(int additions_code);

}
