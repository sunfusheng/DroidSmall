package com.sunfusheng.small.app.main;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.sunfusheng.small.app.main.model.PluginEntity;
import com.sunfusheng.small.app.main.model.SmallEntity;
import com.sunfusheng.small.lib.framework.sharedpreferences.SettingsSharedPreferences;
import com.sunfusheng.small.lib.framework.util.FastJsonUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.devland.esperandro.Esperandro;

/**
 * Created by sunfusheng on 16/8/2.
 */
public class SmallService extends IntentService {

    public static final String SMALL_CHECK_UPDATE = "small_check_update";
    public static final String SMALL_DOWNLOAD_PLUGINS = "small_download_plugins";
    public static final String SMALL_UPDATE_BUNDLES = "small_update_bundles";

    private SmallEntity mSmallEntity;
    private List<PluginEntity> mPluginEntity;

    public SmallService() {
        super("SmallService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("------>", "SmallService onCreate()");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String small = intent.getStringExtra("small");
        Log.d("------>", "SmallService onHandleIntent() small: "+small);
        switch (small) {
            case SMALL_CHECK_UPDATE:
                smallCheckUpdate();
                break;
            case SMALL_DOWNLOAD_PLUGINS:
                smallDownloadPlugins();
                break;
            case SMALL_UPDATE_BUNDLES:

                break;
        }
    }

    // Small 插件检查更新
    private boolean smallCheckUpdate() {
        try {
            URL url = new URL("http://sunfusheng.com/assets/small/bundles.json");
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
            mSmallEntity = FastJsonUtil.parseJson(plugin_bundles, SmallEntity.class);
            if (mSmallEntity == null) return false;

            getSettingsSharedPreferences().plugin_bundles(plugin_bundles);
            boolean updateManifest = mSmallEntity.getManifest_code() > getSettingsSharedPreferences().manifest_code();
            boolean hasUpdates = mSmallEntity.getUpdates_code() > getSettingsSharedPreferences().updates_code();
            boolean hasAdditions = mSmallEntity.getAdditions_code() > getSettingsSharedPreferences().additions_code();
            if (!hasUpdates && !hasAdditions) return false;

            if (mPluginEntity == null) {
                mPluginEntity = new ArrayList<>();
            }
            if (hasUpdates) {
                mPluginEntity.addAll(mSmallEntity.getUpdates());
            }
            if (hasAdditions) {
                mPluginEntity.addAll(mSmallEntity.getAdditions());
            }

            Intent intent = new Intent(this, SmallService.class);
            intent.putExtra("small", SmallService.SMALL_DOWNLOAD_PLUGINS);
            startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    // 下载 Small 插件
    private void smallDownloadPlugins() {
        int count = mPluginEntity.size();
        String filePath;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filePath = Environment.getExternalStorageDirectory() + File.separator + "DroidSmall" + File.separator;
        } else {
            filePath = Environment.getDownloadCacheDirectory() + File.separator + "DroidSmall" + File.separator;
        }

        File destDir = new File(filePath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        for (int i=0; i<count; i++) {
            try {
                PluginEntity pluginEntity = mPluginEntity.get(i);
                String fileName = getFileNameByUrl(pluginEntity.getUrl());
                if (TextUtils.isEmpty(fileName)) continue;
                Log.d("------>", "filePath fileName: "+filePath+fileName);

                URL url = new URL(pluginEntity.getUrl());
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                InputStream is = urlConn.getInputStream();
                OutputStream os = new FileOutputStream(filePath + fileName);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) != -1) {
                    os.write(buffer, 0, length);
                }
                os.flush();
                os.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getFileNameByUrl(String url) {
        if (TextUtils.isEmpty(url) || !url.endsWith(".so")) return null;
        String[] split = url.split("/");
        if (split.length == 0) return null;
        return split[split.length - 1];
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("------>", "SmallService onDestroy()");
    }

    protected <P> P getSharedPreferences(Class<P> spClass) {
        return Esperandro.getPreferences(spClass, this);
    }

    protected SettingsSharedPreferences getSettingsSharedPreferences() {
        return getSharedPreferences(SettingsSharedPreferences.class);
    }

}
