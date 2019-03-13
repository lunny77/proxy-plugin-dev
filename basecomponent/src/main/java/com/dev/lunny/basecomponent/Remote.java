package com.dev.lunny.basecomponent;

import android.os.Bundle;

public interface Remote {

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onRestart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

}
