package com.foodorder.tatsuya.foodorder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.foodorder.tatsuya.foodorder.model.foodpkg.Food;
import com.foodorder.tatsuya.foodorder.utils.AsyncTaskLoadImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hiennv on 01/04/2018.
 */

public class AdapterFood extends BaseAdapter {
    private Activity context;

    private List<Food> listFood;
    private ImageView imgFood;
    private TextView tvName, tvPrice;
    private ImageView imgViewDetail;

    public AdapterFood(Activity context, List<Food> listFood) {
        this.context = context;
        this.listFood = listFood;
    }

    @Override
    public int getCount() {
        return listFood.size();
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
        View row = inflater.inflate(R.layout.listview_row, null);

        imgFood = row.findViewById(R.id.imgFood);
        tvName = row.findViewById(R.id.tvName);
        tvPrice = row.findViewById(R.id.tvPrice);
        imgViewDetail = row.findViewById(R.id.icon_detail);

        System.out.println(listFood.size());
        Food food1 = listFood.get(i);
        String url = food1.getImageURL();
        tvName.setText(food1.getProductName());
        tvPrice.setText(food1.getPrice()+"");

        imgFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ViewDetailActivity.class);
                context.startActivity(i);
            }
        });

//        AsyncTaskLoadImage asyncTaskLoadImage = new AsyncTaskLoadImage(imgAvatar);
//        asyncTaskLoadImage.execute(url);


        new AsyncTaskLoadImage(imgFood).execute(url);

        return row;
    }
}
