package com.foodorder.tatsuya.foodorder.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.foodorder.tatsuya.foodorder.model.personpkg.Account;
import com.foodorder.tatsuya.foodorder.task.OnTaskCompleted;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by hiennv on 10/04/2018.
 */

public class LoginTask extends BasicTask<Account, Void, Boolean>{

    public LoginTask(Context context, OnTaskCompleted<Boolean> listener) {
        super(context, listener);

        super.URL = super.HOST + "/accountws/accountws?WSDL";
        super.METHOD_NAME = "validateAccount";
        super.SOAP_ACTION = super.NAMESPACE + super.METHOD_NAME;
    }


    @Override
    protected Boolean doInBackground(Account... accounts) {

        SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME);

        PropertyInfo propertyInfo = new PropertyInfo();
        propertyInfo.setName("account");
        propertyInfo.setValue(accounts[0]);
        propertyInfo.setType(Account.class);

        soapObject.addProperty(propertyInfo);

        SoapSerializationEnvelope envelope =  new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE httpTransportSE = new HttpTransportSE(URL);

        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive)envelope.getResponse();
            return new Boolean(soapPrimitive.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        //super.onPostExecute(aBoolean);
        super.listener.handle(aBoolean);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Boolean aBoolean) {
        super.onCancelled(aBoolean);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
