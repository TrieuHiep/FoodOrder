package com.foodorder.tatsuya.foodorder.task;

import android.content.Context;
import android.util.Log;

import com.foodorder.tatsuya.foodorder.model.ctmpkg.Customer;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by hiennv on 4/21/18.
 */

public class RegisterTask extends BasicTask<Customer, Void, Boolean> {
    public RegisterTask(Context context, OnTaskCompleted<Boolean> listener) {
        super(context, listener);
        super.URL = super.HOST_NAME + "/customerws/customerws?WSDL";
        super.METHOD_NAME = "addCustomer";
        super.SOAP_ACTION = this.NAMESPACE + this.METHOD_NAME;
    }

    @Override
    protected Boolean doInBackground(Customer... customers) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            PropertyInfo accountProp = new PropertyInfo();
            accountProp.setName("customer");
            accountProp.setValue(customers[0]);
            accountProp.setType(Customer.class);
            request.addProperty(accountProp);

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
