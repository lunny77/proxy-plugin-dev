package com.dev.lunny.proxy_plugin_dev;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dev.lunny.proxy_plugin_dev.utils.FileUtils;

import java.io.File;

import static com.dev.lunny.proxy_plugin_dev.utils.FileUtils.getPluginRepoPath;

public class MainActivity extends BaseHostActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_load_plugin1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPlugin1(FileUtils.plugins[0]);
            }
        });

        FileUtils.copyAssetPluginToCache(this);
    }

    private void loadPlugin1(String pluginName) {
        Intent intent = new Intent(this, ProxyActivity.class);
        intent.putExtra(ProxyActivity.TAG_DEX_PATH, getPluginRepoPath(this) + File.separator + pluginName);
        intent.putExtra(ProxyActivity.TAG_CLASS_NAME, "com.dev.lunny.plugin1.Plugin1MainActivity");
        startActivity(intent);
    }


}
