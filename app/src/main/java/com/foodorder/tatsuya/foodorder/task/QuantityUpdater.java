package com.foodorder.tatsuya.foodorder.task;

import android.content.Context;
import android.util.Log;

import com.foodorder.tatsuya.foodorder.model.foodpkg.Food;
import com.foodorder.tatsuya.foodorder.model.personpkg.Account;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by tatsuya on 22/04/2018.
 */

public class QuantityUpdater extends BasicTask<Account, Void, Boolean> {
    private Food food;
    private Map<Integer, Integer> foodMap = new HashMap<>();

    public QuantityUpdater(Context context, OnTaskCompleted<Boolean> listener,
                           Food food) {
        super(context, listener);
        this.food = food;
        super.URL = super.HOST_NAME + "/mealws/mealws?WSDL";
        super.METHOD_NAME = "updateQuantity";
        super.SOAP_ACTION = super.NAMESPACE + super.METHOD_NAME;
    }

    @Override
    protected Boolean doInBackground(Account... accounts) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            PropertyInfo accountProp = new PropertyInfo();
            accountProp.setName("account");
            accountProp.setValue(accounts[0]);
            accountProp.setType(Account.class);
            request.addProperty(accountProp);

            PropertyInfo foodProp = new PropertyInfo();
            foodProp.setName("food");
            foodProp.setValue(this.food);
            foodProp.setType(Food.class);
            request.addProperty(foodProp);

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
