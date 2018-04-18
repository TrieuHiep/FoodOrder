package com.foodorder.tatsuya.foodorder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.foodorder.tatsuya.foodorder.R;
import com.foodorder.tatsuya.foodorder.model.foodpkg.Food;
import com.foodorder.tatsuya.foodorder.task.AddToMeal;
import com.foodorder.tatsuya.foodorder.task.OnTaskCompleted;

public class ViewDetail extends AppCompatActivity implements OnTaskCompleted<Boolean> {
    private TextView textViewPrice;
    private TextView textViewName;
    private Button btnAddToMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);
        this.textViewPrice = super.findViewById(R.id.hihiTextView);
        this.textViewName = super.findViewById(R.id.tv_name);
        this.btnAddToMeal = super.findViewById(R.id.btnAddToMeal);

        Intent intent = getIntent();
        Food foodItem = (Food) intent.getSerializableExtra("foodDetail");
        System.out.println(foodItem);
        this.textViewName.setText(foodItem.getProductName());
        textViewPrice.setText(String.valueOf(foodItem.getPrice()));
        this.btnAddToMeal.setOnClickListener(view -> {
            new AddToMeal(ViewDetail.this, ViewDetail.this)
                    .execute(foodItem);
        });
    }

    @Override
    public void handle(Boolean value) {
        if (value) {
            Toast.makeText(this, "OK!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
        }
        super.finish();
    }
}
