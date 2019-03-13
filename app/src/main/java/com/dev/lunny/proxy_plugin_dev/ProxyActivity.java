package com.dev.lunny.proxy_plugin_dev;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.dev.lunny.basecomponent.IRemoteActivity;
import com.dev.lunny.basecomponent.StubActivity;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

import static com.dev.lunny.proxy_plugin_dev.utils.FileUtils.getPluginRepoPath;

public class ProxyActivity extends StubActivity {
    public static final String TAG_DEX_PATH = "dex_path";
    public static final String TAG_CLASS_NAME = "class_name";

    private String mDexPath;

    private IRemoteActivity mRemote;
    private Resources mResource;
    private AssetManager mAssetManager;
    private Resources.Theme mTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null) {
            mDexPath = intent.getStringExtra(TAG_DEX_PATH);
            String className = intent.getStringExtra(TAG_CLASS_NAME);
            if (TextUtils.isEmpty(mDexPath) || TextUtils.isEmpty(className)) {
                throw new RuntimeException("dex path and class name could not be empty!");
            }

            try {
                setupRemoteProxyActivity(mDexPath, className);
                setupAssetManager(mDexPath);
                if (mRemote != null) {
                    mRemote.setStubActivity(this);
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
            Class<? extends IRemoteActivity> remoteClass = (Class<? extends IRemoteActivity>) dexClassLoader.loadClass(className);
            Constructor constructor = remoteClass.getConstructor(new Class[]{});
            Object instance = constructor.newInstance(new Object[]{});
            if (!(instance instanceof IRemoteActivity)) {
                throw new Exception("plugin activity must implement IRemoteActivity interface!");
            }
            mRemote = (IRemoteActivity) instance;

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

    /**
     * add plugin resource to assert manager.
     *
     * @param dexPath plugin's path
     */
    private void setupAssetManager(String dexPath) {
        Resources resources = super.getResources();
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            method.setAccessible(true);
            method.invoke(assetManager, dexPath);
            mAssetManager = assetManager;
            mResource = new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
            mTheme = mResource.newTheme();
            mTheme.setTo(super.getTheme());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resources getResources() {
        if (mResource == null) {
            return super.getResources();
        }
        return mResource;
    }

    @Override
    public AssetManager getAssets() {
        if (mAssetManager == null) {
            return super.getAssets();
        }
        return mAssetManager;
    }

    @Override
    public Resources.Theme getTheme() {
        if (mTheme == null) {
            return super.getTheme();
        }
        return mTheme;
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
        if (mRemote != null) {
            mRemote.onRestart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRemote != null) {
            mRemote.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRemote.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mRemote != null) {
            mRemote.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRemote != null) {
            mRemote.onDestroy();
        }
    }

    @Override
    public String getDexPath() {
        return mDexPath;
    }

    @Override
    public void startActivity(String dexPath, String className) {
        Intent intent = new Intent(this, ProxyActivity.class);
        intent.putExtra(ProxyActivity.TAG_DEX_PATH, dexPath);
        intent.putExtra(ProxyActivity.TAG_CLASS_NAME, className);
        startActivity(intent);
    }
}
