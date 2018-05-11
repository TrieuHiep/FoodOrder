package com.foodorder.tatsuya.foodorder.controller;

import android.content.Context;
import android.os.AsyncTask;

import com.foodorder.tatsuya.foodorder.model.foodpkg.Food;
import com.foodorder.tatsuya.foodorder.task.OnTaskCompleted;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by hiennv on 4/13/18.
 */

public class LoadAllFoodTask extends BasicTask<Void, Food, List<Food>> {

    public LoadAllFoodTask(Context context, OnTaskCompleted<List<Food>> listener) {
        super(context, listener);
        super.URL = super.HOST + "/accountws/accountws?WSDL";
        super.METHOD_NAME = "";
        super.SOAP_ACTION = super.NAMESPACE + super.METHOD_NAME;
    }

    @Override
    protected List<Food> doInBackground(Void... voids) {
        List<Food> foodList = new ArrayList<>();
        SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME);

        SoapSerializationEnvelope envelope =  new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE httpTransportSE = new HttpTransportSE(URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            Vector<SoapObject> result = (Vector<SoapObject>) envelope.getResponse();
            for(int i = 0; i< result.size();i++){
                SoapObject object = result.get(i);
                Food food = new Food();
                food.setId(Integer.parseInt(object.getProperty("id").toString()));
                food.setProductName(object.getProperty("productName").toString());
                food.setPrice(Long.parseLong(object.getProperty("price").toString()));
                food.setQuantity(Integer.parseInt(object.getProperty("quantity").toString()));
                food.setImageURL(object.getProperty("imageURL").toString());
                foodList.add(food);
                publishProgress(food);
            }
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return foodList;
    }

    @Override
    protected void onPostExecute(List<Food> foods) {
        //
        super.listener.handle(foods);
    }
}
