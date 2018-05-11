package com.foodorder.tatsuya.foodorder.controller;

import android.content.Context;

import com.foodorder.tatsuya.foodorder.model.foodpkg.Food;
import com.foodorder.tatsuya.foodorder.model.personpkg.Account;
import com.foodorder.tatsuya.foodorder.task.OnTaskCompleted;
import com.foodorder.tatsuya.foodorder.utils.UserSession;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by hiennv on 4/14/18.
 */

public class AddToMealTask extends BasicTask<Food, Void, Boolean>{
    public AddToMealTask(Context context, OnTaskCompleted<Boolean> listener) {
        super(context, listener);
        super.URL = super.HOST + "/accountws/accountws?WSDL";
        super.METHOD_NAME = "validateAccount";
        super.SOAP_ACTION = super.NAMESPACE + super.METHOD_NAME;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        //super.onPostExecute(aBoolean);
        super.listener.handle(aBoolean);
    }

    @Override
    protected Boolean doInBackground(Food... foods) {
        SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME);

        PropertyInfo propertyInfo = new PropertyInfo();

        propertyInfo.setName("account");
        propertyInfo.setValue(UserSession.getInstance().getLoggedAccount());
        propertyInfo.setType(Account.class);
        soapObject.addProperty(propertyInfo);

        propertyInfo.setName("account");
        propertyInfo.setValue(foods[0]);
        propertyInfo.setType(Food.class);
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
}
