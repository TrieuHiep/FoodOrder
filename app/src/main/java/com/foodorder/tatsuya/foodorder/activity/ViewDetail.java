package com.foodorder.tatsuya.foodorder.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.foodorder.tatsuya.foodorder.R;
import com.foodorder.tatsuya.foodorder.model.foodpkg.Food;
import com.foodorder.tatsuya.foodorder.task.AddToMeal;
import com.foodorder.tatsuya.foodorder.task.ImageRenderOnline;
import com.foodorder.tatsuya.foodorder.task.OnTaskCompleted;

public class ViewDetail extends AppCompatActivity implements OnTaskCompleted<Boolean> {
    private TextView textViewPrice;
    private TextView textViewName;
    private ImageView imgProduct;
    private Button btnAddToMeal, btnShare;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        this.textViewPrice = super.findViewById(R.id.hihiTextView);
        this.textViewName = super.findViewById(R.id.tv_name);
        this.btnAddToMeal = super.findViewById(R.id.btnAddToMeal);
        this.imgProduct = super.findViewById(R.id.img_food);
        this.btnShare = super.findViewById(R.id.btn_share);

        shareDialog = new ShareDialog(ViewDetail.this);


        Intent intent = getIntent();
        Food foodItem = (Food) intent.getSerializableExtra("foodDetail");
        System.out.println(foodItem);
        this.textViewName.setText(foodItem.getProductName());
        textViewPrice.setText(String.valueOf(foodItem.getPrice()));
        new ImageRenderOnline(this.imgProduct)
                .execute(foodItem.getImageURL());

        this.btnAddToMeal.setOnClickListener(view -> {
            new AddToMeal(ViewDetail.this, ViewDetail.this)
                    .execute(foodItem);
        });

        btnShare.setOnClickListener(view -> {
            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("https://facebook.com/trieuhiepptit"))
                    .build();
            shareDialog.show(content);
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
