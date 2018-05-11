package com.foodorder.tatsuya.foodorder.test;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by hiennv on 4/13/18.
 */

public class TestProperties {
    public void getContext(Context context) {
        Properties prop = new Properties();
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("filename.txt")));
            // load a properties file
            prop.load(reader);

            // get the property value and print it out
            Toast.makeText(context, prop.getProperty("host"), Toast.LENGTH_SHORT).show();
            Toast.makeText(context, prop.getProperty("namespace"), Toast.LENGTH_SHORT).show();
            Log.i("host = ", prop.getProperty("host"));
            Log.i("namespace", prop.getProperty("namespace"));

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
