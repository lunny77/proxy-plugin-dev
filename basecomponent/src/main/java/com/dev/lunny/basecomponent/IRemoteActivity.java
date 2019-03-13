package com.dev.lunny.basecomponent;

import android.app.Activity;
import android.os.Bundle;

public interface IRemoteActivity {

    void setStubActivity(StubActivity subActivity);

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onRestart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

}
