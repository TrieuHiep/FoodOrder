package com.foodorder.tatsuya.foodorder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodorder.tatsuya.foodorder.model.orderpkg.Meal;
import com.foodorder.tatsuya.foodorder.utils.AsyncTaskLoadImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hiennv on 06/04/2018.
 */

public class AdapterMeal extends BaseAdapter {
    private Activity context;
    private List<Meal> listMeal;
    private TextView tvName, tvQuantity, tvPrice;
    private ImageView imgFood;

    public AdapterMeal(Activity context, List<Meal> listMeal) {
        this.context = context;
        this.listMeal = listMeal;
    }

    @Override
    public int getCount() {
        return listMeal.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.viewlistmeal_row, null);

        imgFood = row.findViewById(R.id.imgFood);
        tvName = row.findViewById(R.id.tvName);
        tvPrice = row.findViewById(R.id.tvPrice);
        tvQuantity = row.findViewById(R.id.tvQuantity);


        Meal meal = listMeal.get(i);
        String url = meal.getFood().getImageURL();
        tvName.setText(meal.getFood().getProductName());
        tvPrice.setText(meal.getFood().getPrice()+"");
        tvQuantity.setText(meal.getQuantity()+"");


        new AsyncTaskLoadImage(imgFood).execute(url);

        return row;
    }
}
