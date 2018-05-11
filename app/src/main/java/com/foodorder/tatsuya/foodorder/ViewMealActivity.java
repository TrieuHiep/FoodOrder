package com.foodorder.tatsuya.foodorder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.foodorder.tatsuya.foodorder.model.foodpkg.Food;
import com.foodorder.tatsuya.foodorder.model.orderpkg.Meal;

import java.util.ArrayList;
import java.util.List;

public class ViewMealActivity extends Activity {

    private Button btnCheckout;
    private AdapterMeal adapterMeal;
    private ListView listViewMeal;
    private List<Meal> listMeal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meal);
        init();
    }

    private void init() {
        listViewMeal = findViewById(R.id.listViewMeal);
        listMeal = new ArrayList<>();
        String image = "https://vignette.wikia.nocookie.net/ronaldmcdonald/images/c/c7/1955_Burger.png/revision/latest?cb=20151206183328";
        Meal meal = new Meal();
        Meal meal1 = new Meal();
        Meal meal2 = new Meal();
        Food food = new Food("hhh", 123, 12, image);
        Food food1 = new Food("hhh", 123, 12, image);
        Food food2 = new Food("hhh", 123, 12, image);
        meal.setFood(food);
        meal1.setFood(food1);
        meal2.setFood(food2);

        listMeal.add(meal);
        listMeal.add(meal1);
        listMeal.add(meal2);
        adapterMeal = new AdapterMeal(this, listMeal);
        listViewMeal.setAdapter(adapterMeal);



        btnCheckout = findViewById(R.id.btn_checkout);
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewMealActivity.this, CreateFoodOrderActivity.class);
                startActivity(i);
            }
        });
    }
}
