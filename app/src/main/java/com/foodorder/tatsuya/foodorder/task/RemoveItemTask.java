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
 * Created by tatsuya on 08/05/2018.
 */

public class RemoveItemTask extends BasicTask<Void, Void, Boolean> {
    private Account loggedAccount;
    private int itemIndex;

    public RemoveItemTask(Context context, OnTaskCompleted<Boolean> listener,
                          Account loggedAccount, int itemIndex) {
        super(context, listener);
        this.loggedAccount = loggedAccount;
        this.itemIndex = itemIndex;
        super.URL = super.HOST_NAME + "/mealws/mealws?WSDL";
        super.METHOD_NAME = "removeProduct";
        super.SOAP_ACTION = this.NAMESPACE + this.METHOD_NAME;
    }


    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            PropertyInfo accountProp = new PropertyInfo();
            accountProp.setName("account");
            accountProp.setValue(this.loggedAccount);
            accountProp.setType(Account.class);
            request.addProperty(accountProp);

            PropertyInfo itemIndexProp = new PropertyInfo();
            itemIndexProp.setName("index");
            itemIndexProp.setValue(Integer.valueOf(this.itemIndex));
            itemIndexProp.setType(Integer.class);
            request.addProperty(itemIndexProp);

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
}
