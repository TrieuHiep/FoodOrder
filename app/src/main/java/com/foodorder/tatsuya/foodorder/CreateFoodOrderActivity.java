package com.foodorder.tatsuya.foodorder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateFoodOrderActivity extends Activity {
    private Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        init();
    }

    private void init() {
        btnFinish = findViewById(R.id.btn_order);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CreateFoodOrderActivity.this, PaymentActivity.class);
                startActivity(i);
            }
        });
    }
}
