package com.dev.lunny.basecomponent;

import android.app.Activity;
import android.os.Bundle;

public class BasePluginActivity implements IRemoteActivity {
    public StubActivity that;

    @Override
    public void setStubActivity(StubActivity stubActivity) {
        that = stubActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onRestart() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onDestroy() {
    }
}
