package com.foodorder.tatsuya.foodorder.task;

import android.content.Context;
import android.util.Log;

import com.foodorder.tatsuya.foodorder.model.personpkg.Account;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by tatsuya on 15/04/2018.
 */

public class MealSaver extends BasicTask<Account, Void, Boolean> {

    private String address;
    private String paymentType;

    public MealSaver(Context context, OnTaskCompleted<Boolean> listener,
                     String address, String paymentType) {
        super(context, listener);
        super.URL = super.HOST_NAME + "/mealws/mealws?WSDL";
        super.METHOD_NAME = "saveMealToDB";
        super.SOAP_ACTION = this.NAMESPACE + this.METHOD_NAME;
        this.address = address;
        this.paymentType = paymentType;
    }

    @Override
    protected Boolean doInBackground(Account... accounts) {
        try {
            System.out.println("address = " + address);
            System.out.println("paymentType = " + paymentType);
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            PropertyInfo accountProp = new PropertyInfo();
            accountProp.setName("account");
            accountProp.setValue(accounts[0]);
            accountProp.setType(Account.class);
            request.addProperty(accountProp);

            PropertyInfo addressProp = new PropertyInfo();
            addressProp.setName("address");
            addressProp.setValue(this.address);
            addressProp.setType(String.class);
            request.addProperty(addressProp);

            PropertyInfo paymentProp = new PropertyInfo();
            paymentProp.setName("paymentType");
            paymentProp.setValue(this.paymentType);
            paymentProp.setType(String.class);
            request.addProperty(paymentProp);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, 60000);
            Log.i("bitching", "guys");

            androidHttpTransport.call(SOAP_ACTION, envelope);
            Log.i("fuck you", "bitch");

            SoapPrimitive result = (SoapPrimitive) envelope.getResponse();
            System.out.println(result);

            return Boolean.valueOf(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.listener.handle(aBoolean);
    }
}