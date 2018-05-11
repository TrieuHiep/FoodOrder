package com.foodorder.tatsuya.foodorder.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodorder.tatsuya.foodorder.R;
import com.foodorder.tatsuya.foodorder.model.foodpkg.Food;
import com.foodorder.tatsuya.foodorder.model.personpkg.Account;
import com.foodorder.tatsuya.foodorder.task.ImageRenderOnline;
import com.foodorder.tatsuya.foodorder.task.RemoveItemTask;
import com.foodorder.tatsuya.foodorder.utils.EndPoint;
import com.foodorder.tatsuya.foodorder.utils.UserSession;

import java.util.List;

/**
 * Created by tatsuya on 13/04/2018.
 */

public class MealAdapter extends ArrayAdapter<Food> {
    private Context context;
    private int resource;
    private List<Food> foodList;

    public MealAdapter(@NonNull Context context, int resource, @NonNull List<Food> foodList) {
        super(context, resource, foodList);
        this.context = context;
        this.resource = resource;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_meal_line, parent, false);
        ImageView imageFood = convertView.findViewById(R.id.imageProduct);
        TextView productName = convertView.findViewById(R.id.productName);
        TextView priceProduct = convertView.findViewById(R.id.productPrice);
        TextView numOfQuantity = convertView.findViewById(R.id.numOfQuantity);
        Button btnRemove = convertView.findViewById(R.id.deleteBtn);
        TextView plusQuantity = convertView.findViewById(R.id.plus_quantity);
        TextView subQuantity = convertView.findViewById(R.id.minus_quantity);

        Food food = this.foodList.get(position);
        System.out.println(food.getImageURL());

        new ImageRenderOnline(imageFood).execute(food.getImageURL());
        productName.setText(food.getProductName());
        priceProduct.setText(Double.valueOf(food.getPrice()).toString());
        numOfQuantity.setText(Integer.valueOf(food.getQuantity()).toString());

        plusQuantity.setOnClickListener(view -> {
            int newQuantity = Math.min(20,
                    Integer.parseInt(numOfQuantity.getText().toString().trim()) + 1);
            numOfQuantity.setText(newQuantity + "");
            food.setQuantity(newQuantity);
        });

        subQuantity.setOnClickListener(view -> {
            int newQuantity = Math.max(1,
                    Integer.parseInt(numOfQuantity.getText().toString().trim()) - 1);
            System.out.println("decrease quantity...");
            numOfQuantity.setText(newQuantity + "");
            food.setQuantity(newQuantity);
        });

        btnRemove.setOnClickListener(view -> {
            Account loggedAccount = UserSession.getInstance().getLoggedAccount();
            new RemoveItemTask(context,new EndPoint<>(),loggedAccount,position).execute();
            foodList.remove(position);
            MealAdapter.this.notifyDataSetChanged(); //Updates adapter to new changes
        });
        return convertView;
    }
}
