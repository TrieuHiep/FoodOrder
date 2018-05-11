package com.foodorder.tatsuya.foodorder.task;

import android.content.Context;
import android.os.AsyncTask;

import com.foodorder.tatsuya.foodorder.utils.ConfigReader;

/**
 * Created by tatsuya on 14/04/2018.
 */

public abstract class BasicTask<I, M, O> extends AsyncTask<I, M, O> {
    /*
    for AsyncTask
    I: Input
    M: Middle Result
    O: Output
     */
    protected String HOST_NAME;
    protected String NAMESPACE;
    protected String URL;
    protected String METHOD_NAME;
    protected String SOAP_ACTION;
    protected ConfigReader configReader; // read config.properties file
    protected OnTaskCompleted<O> listener;

    public BasicTask(Context context, OnTaskCompleted<O> listener) {
        this.listener = listener;
        this.configReader = ConfigReader.getInstance();
        this.NAMESPACE = configReader.getProperty(context, "name.space");
        this.HOST_NAME = configReader.getProperty(context, "host.name");
    }

    @Override
    protected void onPostExecute(O o) {
        super.onPostExecute(o);
        this.listener.handle(o);
    }
}
