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

import net.wequick.small.Small;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
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
    private List<PluginEntity> mPluginEntities;

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
                smallUpdateBundles();
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
            if (initPluginEntities()) {
                Intent intent = new Intent(this, SmallService.class);
                intent.putExtra("small", SMALL_DOWNLOAD_PLUGINS);
                startService(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    // 下载 Small 插件
    private void smallDownloadPlugins() {
        int count = mPluginEntities.size();
        String filePath = getFilePathBySDCard() + File.separator;

        File destDir = new File(filePath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        for (int i=0; i<count; i++) {
            try {
                PluginEntity pluginEntity = mPluginEntities.get(i);
                String fileName = getFileNameByUrl(pluginEntity.getUrl());
                if (TextUtils.isEmpty(fileName)) continue;
                Log.d("------>", "filePath/fileName: " + filePath + fileName);

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

    // 更新 small 插件
    private boolean smallUpdateBundles() {
        try {
            String plugin_bundles = getSettingsSharedPreferences().plugin_bundles();
            if (TextUtils.isEmpty(plugin_bundles)) return false;
            mSmallEntity = FastJsonUtil.parseJson(plugin_bundles, SmallEntity.class);
            if (mSmallEntity == null) return false;
            boolean updateManifest = mSmallEntity.getManifest_code() > getSettingsSharedPreferences().manifest_code();
            if (updateManifest) {
                // 更新注册表信息
                JSONObject smallObject = new JSONObject(plugin_bundles);
                JSONObject manifestObject = smallObject.has("manifest") ? smallObject.getJSONObject("manifest") : null;
                if (manifestObject != null) {
                    Small.updateManifest(manifestObject, false);
                    Log.d("------>", "更新注册表成功");
                }
            }
            if (initPluginEntities()) {
                // 更新插件
                int count = mPluginEntities.size();
                for (int i=0; i<count; i++) {
                    PluginEntity pluginEntity = mPluginEntities.get(i);
                    String fileName = getFileNameByUrl(pluginEntity.getUrl());
                    updateBundleThenDelete(pluginEntity.getPkg(), fileName);
                }
                Log.d("------>", "更新插件成功");
            }
            getSettingsSharedPreferences().manifest_code(mSmallEntity.getManifest_code());
            getSettingsSharedPreferences().updates_code(mSmallEntity.getUpdates_code());
            getSettingsSharedPreferences().additions_code(mSmallEntity.getAdditions_code());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    // 更新插件后删除插件
    private void updateBundleThenDelete(final String pkgName, final String fileName) {
        try {
            String filePath = getFilePathBySDCard();
            File inFile = new File(filePath, fileName);
            if (!inFile.exists()) return;

            net.wequick.small.Bundle bundle = Small.getBundle(pkgName);
            File outFile = bundle.getPatchFile();

            InputStream is = new FileInputStream(inFile);
            OutputStream os = new FileOutputStream(outFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                os.write(buffer, 0, length);
            }

            os.flush();
            os.close();
            is.close();
            bundle.upgrade();
            inFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 初始化要更新的插件列表
    private boolean initPluginEntities() {
        if (mSmallEntity == null) return false;
        boolean hasUpdates = mSmallEntity.getUpdates_code() > getSettingsSharedPreferences().updates_code();
        boolean hasAdditions = mSmallEntity.getAdditions_code() > getSettingsSharedPreferences().additions_code();
        if (!hasUpdates && !hasAdditions) return false;

        if (mPluginEntities == null) {
            mPluginEntities = new ArrayList<>();
        }
        if (hasUpdates) {
            mPluginEntities.addAll(mSmallEntity.getUpdates());
        }
        if (hasAdditions) {
            mPluginEntities.addAll(mSmallEntity.getAdditions());
        }
        return true;
    }

    // 获得手机上存储插件路径
    private String getFilePathBySDCard() {
        String filePath;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filePath = Environment.getExternalStorageDirectory() + File.separator + "DroidSmall";
        } else {
            filePath = Environment.getDownloadCacheDirectory() + File.separator + "DroidSmall";
        }
        return filePath;
    }

    // 通过 URL 获得文件名
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
