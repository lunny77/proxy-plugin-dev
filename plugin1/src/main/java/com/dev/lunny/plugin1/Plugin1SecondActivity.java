package com.dev.lunny.plugin1;

import android.os.Bundle;
import android.util.Log;

import com.dev.lunny.basecomponent.BasePluginActivity;

public class Plugin1SecondActivity extends BasePluginActivity {
    private static final String TAG = "Plugin_1MainActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        that.setContentView(R.layout.activity_plugin1_second);
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
    }

    @Override
    public void onRestart() {
        Log.d(TAG, "onRestart");
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
    }
}
