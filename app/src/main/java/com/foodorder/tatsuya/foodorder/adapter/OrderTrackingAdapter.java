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
import com.foodorder.tatsuya.foodorder.model.orderpkg.FoodOrder;
import com.foodorder.tatsuya.foodorder.task.ImageRenderOnline;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hiennv on 4/21/18.
 */

public class OrderTrackingAdapter extends ArrayAdapter<FoodOrder> {

    private Context context;
    private int resource;
    private List<FoodOrder> foodOrderList= new ArrayList<>();

    public OrderTrackingAdapter(@NonNull Context context, int resource, List<FoodOrder> foodOrderList) {
        super(context, resource, foodOrderList);
        this.context = context;
        this.resource = resource;
        this.foodOrderList = foodOrderList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_order_tracking_line, parent, false);
        ImageView imageFood = convertView.findViewById(R.id.imageProduct_order);
        TextView productName = convertView.findViewById(R.id.productName_order);
        TextView orderID = convertView.findViewById(R.id.tv_id_order);
        TextView status = convertView.findViewById(R.id.tv_status);

        FoodOrder foodOrder = this.foodOrderList.get(position);
       // System.out.println(food.getImageURL());

//       new ImageRenderOnline(imageFood).execute(foodOrder.getMeal().getFood().getImageURL());
//        productName.setText(foodOrder.getMeal().getFood().getProductName());
 //       orderID.setText(foodOrder.getID()+"");
  //      status.setText(foodOrder.getStatus()+"");
        return convertView;
    }
}
