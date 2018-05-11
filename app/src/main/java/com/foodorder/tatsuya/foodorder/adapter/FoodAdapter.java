package com.foodorder.tatsuya.foodorder.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodorder.tatsuya.foodorder.R;
import com.foodorder.tatsuya.foodorder.model.foodpkg.Food;
import com.foodorder.tatsuya.foodorder.task.ImageRenderOnline;

import java.util.List;

/**
 * Created by tatsuya on 17/03/2018.
 */

public class FoodAdapter extends ArrayAdapter<Food> {
    private Context context;
    private int resource;
    private List<Food> foodList;

    public FoodAdapter(@NonNull Context context, int resource, @NonNull List<Food> foodList) {
        super(context, resource, foodList);
        this.context = context;
        this.resource = resource;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_food_line, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.setImageFood((ImageView) convertView.findViewById(R.id.imageProduct));
            viewHolder.setProductName((TextView) convertView.findViewById(R.id.productName));
            viewHolder.setPriceProduct((TextView) convertView.findViewById(R.id.productPrice));
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        ImageView imageFood = convertView.findViewById(R.id.imageProduct);
//        TextView productName = convertView.findViewById(R.id.productName);
//        TextView priceProduct = convertView.findViewById(R.id.productPrice);
//        TextView productDescription = convertView.findViewById(R.id.productDescription);
        Food food = this.foodList.get(position);
        System.out.println(food.getImageURL());
        new ImageRenderOnline(viewHolder.getImageFood()).execute(food.getImageURL());

//        imageFood.setText(Integer.valueOf(food.getId()).toString());
        viewHolder.getProductName().setText(food.getProductName());
        viewHolder.getPriceProduct().setText(Double.valueOf(food.getPrice()).toString());

        return convertView;
    }

    class ViewHolder {
        ImageView imageFood;
        TextView productName;
        TextView priceProduct;
        ImageView productDescription;

        public ImageView getImageFood() {
            return imageFood;
        }

        public void setImageFood(ImageView imageFood) {
            this.imageFood = imageFood;
        }

        public TextView getProductName() {
            return productName;
        }

        public void setProductName(TextView productName) {
            this.productName = productName;
        }

        public TextView getPriceProduct() {
            return priceProduct;
        }

        public void setPriceProduct(TextView priceProduct) {
            this.priceProduct = priceProduct;
        }

        public ImageView getProductDescription() {
            return productDescription;
        }

        public void setProductDescription(ImageView productDescription) {
            this.productDescription = productDescription;
        }
    }

//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_food_line,parent,false);
//        ImageView imageFood = convertView.findViewById(R.id.imageProduct);
//        TextView productName = convertView.findViewById(R.id.productName);
//        TextView priceProduct = convertView.findViewById(R.id.productPrice);
//        TextView productDescription = convertView.findViewById(R.id.productDescription);
//        Food food = this.foodList.get(position);
//        System.out.println(food.getImageURL());
//        new ImageRenderOnline(imageFood).execute(food.getImageURL());
//
////        imageFood.setText(Integer.valueOf(food.getId()).toString());
//        productName.setText(food.getProductName());
//        priceProduct.setText(Double.valueOf(food.getPrice()).toString());
//
//        return convertView;
//    }

}
//using code in activity.java
//        this.listViewProduct = super.findViewById(R.id.listViewProduct);
//                FoodAdapter foodAdapter
//                = new FoodAdapter(this, R.layout.activity_food_line, this.foodList);
//                for (int i = 1; i <= 5; i++) {
//                Food food = new Food(i, "Product " + i, 12000, 100, "");
//                foodList.add(food);
//                }
//                this.listViewProduct.setAdapter(foodAdapter);