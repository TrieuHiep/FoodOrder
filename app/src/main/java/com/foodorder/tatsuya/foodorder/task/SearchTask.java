package com.foodorder.tatsuya.foodorder.task;

import android.content.Context;
import android.util.Log;

import com.foodorder.tatsuya.foodorder.model.foodpkg.Food;
import com.foodorder.tatsuya.foodorder.model.personpkg.Account;
import com.foodorder.tatsuya.foodorder.utils.UserSession;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by tatsuya on 02/05/2018.
 */

public class SearchTask extends BasicTask<String, Void, List<Food>> {

    public SearchTask(Context context, OnTaskCompleted<List<Food>> listener) {
        super(context, listener);
        super.URL = super.HOST_NAME + "/foodws/foodws?WSDL";
        super.METHOD_NAME = "searchFoodByName";
        super.SOAP_ACTION = super.NAMESPACE + super.METHOD_NAME;
    }

    @Override
    protected List<Food> doInBackground(String... strings) {
        List<Food> foodList = new ArrayList<>();
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Pass value for fname variable of the web service
            PropertyInfo queryProp = new PropertyInfo();
            queryProp.setName("query");//Define the variable name in the web service method
            queryProp.setValue(strings[0]);//Define value for fname variable
            queryProp.setType(String.class);//Define the type of the variable
            request.addProperty(queryProp);//Pass properties to the variable

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, 60000);
            Log.i("bitching", "guys");

            androidHttpTransport.call(SOAP_ACTION, envelope);
            Log.i("fuck you", "bitch");
            Object o = envelope.getResponse();
            if(o instanceof SoapObject){
                SoapObject object = (SoapObject) o;
                Food food = new Food();
                food.setId(Integer.parseInt(object.getProperty("id").toString()));
                food.setProductName(object.getProperty("productName").toString());
                food.setPrice(Long.parseLong(object.getProperty("price").toString()));
                food.setQuantity(Integer.parseInt(object.getProperty("quantity").toString()));
                food.setImageURL(object.getProperty("imageURL").toString());
                foodList.add(food);
            }
            else if(o instanceof Vector){
                Vector<SoapObject> result = (Vector<SoapObject>) o;
                int length = result.size();
                for (int i = 0; i < length; ++i) {
                    SoapObject object = result.get(i);
                    Food food = new Food();
                    food.setId(Integer.parseInt(object.getProperty("id").toString()));
                    food.setProductName(object.getProperty("productName").toString());
                    food.setPrice(Long.parseLong(object.getProperty("price").toString()));
                    food.setQuantity(Integer.parseInt(object.getProperty("quantity").toString()));
                    food.setImageURL(object.getProperty("imageURL").toString());
                    foodList.add(food);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return foodList;
    }
}
