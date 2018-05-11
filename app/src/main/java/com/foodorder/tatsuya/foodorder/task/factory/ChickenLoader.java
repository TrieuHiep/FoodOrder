package com.foodorder.tatsuya.foodorder.task.factory;

import android.content.Context;
import android.util.Log;

import com.foodorder.tatsuya.foodorder.model.foodpkg.Food;
import com.foodorder.tatsuya.foodorder.task.BasicTask;
import com.foodorder.tatsuya.foodorder.task.OnTaskCompleted;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by tatsuya on 02/05/2018.
 */

public class ChickenLoader extends BasicTask<Void, Food, List<Food>> {
    public ChickenLoader(Context context, OnTaskCompleted<List<Food>> listener) {
        super(context, listener);
        super.URL = super.HOST_NAME + "/chickenws/chickenws?WSDL";
        super.METHOD_NAME = "getAllChickenFood";
        super.SOAP_ACTION = super.NAMESPACE + super.METHOD_NAME;
    }

    @Override
    protected List<Food> doInBackground(Void... voids) {
        List<Food> foodList = new ArrayList<>();
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, 60000);
            Log.i("bitching", "guys");

            androidHttpTransport.call(SOAP_ACTION, envelope);
            Log.i("fuck you", "bitch");

            Vector<SoapObject> result = (Vector<SoapObject>) envelope.getResponse();
            int length = result.size();
            for (int i = 0; i < length; ++i) {
                SoapObject complexObject = result.get(i);
                SoapObject object = (SoapObject) complexObject.getProperty("food");
                Food food = new Food();
                food.setId(Integer.parseInt(object.getProperty("id").toString()));
                food.setProductName(object.getProperty("productName").toString());
                food.setPrice(Long.parseLong(object.getProperty("price").toString()));
                food.setQuantity(Integer.parseInt(object.getProperty("quantity").toString()));
                food.setImageURL(object.getProperty("imageURL").toString());
                foodList.add(food);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return foodList;
    }
}
