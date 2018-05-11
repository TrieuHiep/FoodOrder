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
import com.foodorder.tatsuya.foodorder.model.orderpkg.FoodOrder;
import com.foodorder.tatsuya.foodorder.task.ImageRenderOnline;

import java.util.List;

/**
 * Created by tatsuya on 21/04/2018.
 */

public class OrderAdapter extends ArrayAdapter<FoodOrder> {
    private Context context;
    private int resource;
    private List<FoodOrder> foodOrderList;

    public OrderAdapter(@NonNull Context context, int resource,
                        @NonNull List<FoodOrder> foodOrderList) {
        super(context, resource, foodOrderList);
        this.context = context;
        this.resource = resource;
        this.foodOrderList = foodOrderList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater
                .from(this.context)
                .inflate(R.layout.order_line, parent, false);
        ImageView imageFood = convertView.findViewById(R.id.imageProduct_order);
        TextView productName = convertView.findViewById(R.id.productName_order);
        TextView orderID = convertView.findViewById(R.id.tv_id_order);
        TextView status = convertView.findViewById(R.id.tv_status);
        FoodOrder order = this.foodOrderList.get(position);
        String imageURL = order.getMeal().getFood().getImageURL();
        if (imageURL != null) {
            new ImageRenderOnline(imageFood).execute(imageURL);
        }
        productName.setText(order.getMeal().getFood().getProductName());
        orderID.setText("Order ID: " + order.getID());
        status.setText("Status: "+order.getStatus());
        return convertView;
    }
}
