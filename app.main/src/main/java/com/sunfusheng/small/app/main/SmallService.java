package com.sunfusheng.small.app.main;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.sunfusheng.small.app.main.model.PluginEntity;
import com.sunfusheng.small.lib.framework.sharedpreferences.SettingsSharedPreferences;
import com.sunfusheng.small.lib.framework.util.FastJsonUtil;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import de.devland.esperandro.Esperandro;

/**
 * Created by sunfusheng on 16/8/2.
 */
public class SmallService extends IntentService {

    public static final String SMALL_CHECK_UPDATE = "small_check_update";
    public static final String SMALL_DOWNLOAD_PLUGINS = "small_download_plugins";
    public static final String SMALL_UPDATE_BUNDLES = "small_update_bundles";

    private boolean isRunning = true;
    private LocalBroadcastManager mLocalBroadcastManager;
    private PluginEntity mPluginEntity;

    public SmallService() {
        super("SmallService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        Log.d("------>", "SmallService onCreate()");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        isRunning = true;
        String small = intent.getStringExtra("small");
        Log.d("------>", "onHandleIntent() small: "+small);
        switch (small) {
            case SMALL_CHECK_UPDATE:
                smallCheckUpdate();
                break;
            case SMALL_DOWNLOAD_PLUGINS:

                break;
            case SMALL_UPDATE_BUNDLES:

                break;
        }

        isRunning = false;
    }

    private boolean smallCheckUpdate() {
        try {
            URL url = new URL("https://github.com/sfsheng0322/DroidSmall/blob/master/small/bundles.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            StringBuilder sb = new StringBuilder();
            InputStream is = conn.getInputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, length));
            }
            String plugin_bundles = sb.toString();
            if (TextUtils.isEmpty(plugin_bundles)) return false;
            Log.d("------>", "plugin_bundles: "+plugin_bundles);
            mPluginEntity = FastJsonUtil.parseJson(plugin_bundles, PluginEntity.class);
            if (mPluginEntity == null) return false;
            getSettingsSharedPreferences().plugin_bundles(plugin_bundles);
            boolean updateManifest = mPluginEntity.getManifest_code() > getSettingsSharedPreferences().manifest_code();
            boolean hasUpdates = mPluginEntity.getUpdates_code() > getSettingsSharedPreferences().updates_code();
            boolean hasAdditions = mPluginEntity.getAdditions_code() > getSettingsSharedPreferences().additions_code();
            if (!updateManifest && !hasUpdates && !hasAdditions) return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("------>", "SmallService onDestroy()");
    }

    // 发送服务状态信息
    private void sendServiceStatus(String status) {
        Intent intent = new Intent();
        intent.putExtra("status", status);
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    protected <P> P getSharedPreferences(Class<P> spClass) {
        return Esperandro.getPreferences(spClass, this);
    }

    protected SettingsSharedPreferences getSettingsSharedPreferences() {
        return getSharedPreferences(SettingsSharedPreferences.class);
    }

}
