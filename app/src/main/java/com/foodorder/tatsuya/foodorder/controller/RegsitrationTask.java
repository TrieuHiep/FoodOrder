package com.foodorder.tatsuya.foodorder.controller;

import android.content.Context;
import android.os.AsyncTask;

import com.foodorder.tatsuya.foodorder.model.personpkg.Account;
import com.foodorder.tatsuya.foodorder.model.personpkg.Person;
import com.foodorder.tatsuya.foodorder.task.OnTaskCompleted;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by hiennv on 11/04/2018.
 */

public class RegsitrationTask extends BasicTask<Person, Void, Boolean> {
    public RegsitrationTask(Context context, OnTaskCompleted<Boolean> listener) {
        super(context, listener);
        super.URL = super.HOST + "/personws/personws?WSDL";
        super.METHOD_NAME = "addPerson";
        super.SOAP_ACTION =super.NAMESPACE + super.METHOD_NAME;
    }


    @Override
    protected Boolean doInBackground(Person... people) {
        SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME);

        PropertyInfo propertyInfo = new PropertyInfo();
        propertyInfo.setName("person");
        propertyInfo.setValue(people[0]);
        propertyInfo.setType(Person.class);

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
    protected void onPostExecute(Boolean aBoolean) {
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
