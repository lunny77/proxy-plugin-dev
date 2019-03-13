package com.dev.lunny.basecomponent;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;

public abstract class StubActivity extends Activity {

    public abstract String getDexPath();

    public abstract void startActivity(String dexPath, String className);

}
