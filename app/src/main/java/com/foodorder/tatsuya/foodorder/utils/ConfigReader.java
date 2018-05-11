package com.foodorder.tatsuya.foodorder.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by hiennv on 4/14/18.
 */

public class ConfigReader {
    private static final ConfigReader instance = new ConfigReader();

    private static Properties properties = new Properties();

    public static ConfigReader getInstance() {
        return instance;
    }

    private ConfigReader() {

    }

    public String getProperty(Context context, String property) {
        AssetManager assetManager = context.getAssets();
        try {
            properties.load(assetManager.open("hostinfo.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(property);
    }
}
