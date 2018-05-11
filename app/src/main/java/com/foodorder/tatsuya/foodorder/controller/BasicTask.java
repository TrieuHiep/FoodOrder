package com.foodorder.tatsuya.foodorder.controller;

import android.content.Context;
import android.os.AsyncTask;

import com.foodorder.tatsuya.foodorder.task.OnTaskCompleted;
import com.foodorder.tatsuya.foodorder.utils.ConfigReader;

/**
 * Created by hiennv on 4/14/18.
 */

public abstract class BasicTask<I, M, O> extends AsyncTask<I, M, O> {
    protected String URL;
    protected String HOST;
    protected String NAMESPACE;
    protected String SOAP_ACTION;
    protected String METHOD_NAME;
    protected OnTaskCompleted<O> listener;
    protected ConfigReader configReader;

    public BasicTask(Context context, OnTaskCompleted<O> listener) {
        this.configReader = ConfigReader.getInstance();
        this.listener = listener;
        this.HOST = configReader.getProperty(context, "host");
        this.NAMESPACE = configReader.getProperty(context, "namespace");
    }
}
