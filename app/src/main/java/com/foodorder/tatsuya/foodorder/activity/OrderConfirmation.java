package com.foodorder.tatsuya.foodorder.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.foodorder.tatsuya.foodorder.R;
import com.foodorder.tatsuya.foodorder.UserSession;
import com.foodorder.tatsuya.foodorder.model.personpkg.Account;
import com.foodorder.tatsuya.foodorder.task.MealSaver;
import com.foodorder.tatsuya.foodorder.utils.ConfigReader;
import com.foodorder.tatsuya.foodorder.utils.EndPoint;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;

public class OrderConfirmation extends AppCompatActivity {
    private AutoCompleteTextView txtCity;
    private AutoCompleteTextView txtDistrict;
    private EditText txtAddress;
    private Button btnPayment;
    private Account loggedAccount;
    private String[] cityList = {"Ha Noi", "Hai Phong", "Nam Dinh"};
    private String[] districtList = {"Hoang Mai", "Thanh Xuan", "Dong Da"};

    //for Paypal payment
    private int PAYPAL_REQUEST_CODE = 6969;
    private PayPalConfiguration configuration;
    private int amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        this.txtAddress = super.findViewById(R.id.txtAddress);
        this.txtDistrict = super.findViewById(R.id.txtDistrict);
        this.txtCity = super.findViewById(R.id.txtCity);
        this.btnPayment = super.findViewById(R.id.btnPayment);
        setCompleteTextValue(cityList, this.txtCity);
        setCompleteTextValue(districtList, this.txtDistrict);
        this.loggedAccount = UserSession.getInstance().getLoggedAccount();

        this.amount = super.getIntent().getIntExtra("amount", 0);
        this.configuration = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(
                   ConfigReader.getInstance().getProperty(this, "paypal.client.id")
                );

        super.startService(
                new Intent(this, PayPalService.class)
                        .putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration)
        );

        this.btnPayment.setOnClickListener(view -> {
            processPayment();
        });
    }

    private void processPayment() {
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(
                String.valueOf(amount)),
                "USD", "Pay for Tatsuya",
                PayPalPayment.PAYMENT_INTENT_SALE
        );

        super.startActivityForResult(
                new Intent(OrderConfirmation.this, PaymentActivity.class)
                        .putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration)
                        .putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment)
                , PAYPAL_REQUEST_CODE
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
//                        String paymentDetail = confirmation.toJSONObject().toString(4);
//                        super.startActivity(new Intent(this, PaymentDetails.class)
//                                .putExtra("PaymentDetails", paymentDetail)
//                                .putExtra("PaymentAmount", ammount)
//                        );
                    String address = this.txtAddress.getText() + "," + this.txtDistrict.getText() + "," + this.txtCity.getText();
                    new MealSaver(
                            OrderConfirmation.this, new EndPoint<Boolean>(),
                            address, "PaypalPayment"
                    ).execute(this.loggedAccount); // call service to save all Meals and create Order

                    super.startActivity(new Intent(OrderConfirmation.this, MainActivity.class)); // return to Home
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void setCompleteTextValue(String[] src, AutoCompleteTextView completeTextView) {
        ArrayAdapter adapterSearching = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, src);
        completeTextView.setAdapter(adapterSearching);
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        System.out.println("preparing to destroy.....");
        super.onDestroy();
    }

}