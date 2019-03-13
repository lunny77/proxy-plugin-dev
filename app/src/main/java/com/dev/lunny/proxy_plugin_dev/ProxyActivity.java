package com.dev.lunny.proxy_plugin_dev;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.dev.lunny.basecomponent.Remote;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import dalvik.system.DexClassLoader;

public class ProxyActivity extends AppCompatActivity {
    public static final String TAG_DEX_PATH = "dex_path";
    public static final String TAG_CLASS_NAME = "class_name";

    private Remote mRemote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null) {
            String dexPath = intent.getStringExtra(TAG_DEX_PATH);
            String className = intent.getStringExtra(TAG_CLASS_NAME);
            if (TextUtils.isEmpty(dexPath) || TextUtils.isEmpty(className)) {
                throw new RuntimeException("dex path and class name could not be empty!");
            }

            try {
                setupRemoteProxyActivity(dexPath, className);
                if (mRemote != null) {
                    mRemote.onCreate(savedInstanceState);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * load plugin activity and set remote proxy.
     *
     * @param dexPath   plugin's path
     * @param className class name that will be loaded
     * @throws Exception
     */
    private void setupRemoteProxyActivity(String dexPath, String className) throws Exception {
        DexClassLoader dexClassLoader = new DexClassLoader(dexPath, getOptimizedPath(), null, getClassLoader());
        try {
            Class<? extends Remote> remoteClass = (Class<? extends Remote>) dexClassLoader.loadClass(className);
            Constructor constructor = remoteClass.getConstructor(new Class[]{});
            Object instance = constructor.newInstance(new Object[]{});
            if (!(instance instanceof Remote)) {
                throw new Exception("plugin activity must implement Remote interface!");
            }
            mRemote = (Remote) instance;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new Exception("plugin apk is not exist, or class is not in dex!");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private String getOptimizedPath() {
        File cacheDir = getCacheDir();
        return cacheDir.getAbsolutePath() + File.separator + "plugin-optimized";
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mRemote != null) {
            mRemote.onStart();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mRemote.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRemote.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRemote.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRemote.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRemote.onDestroy();
    }
}
