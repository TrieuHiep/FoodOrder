package com.foodorder.tatsuya.foodorder.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.foodorder.tatsuya.foodorder.R;
import com.foodorder.tatsuya.foodorder.model.foodpkg.Food;
import com.foodorder.tatsuya.foodorder.task.AddToMeal;
import com.foodorder.tatsuya.foodorder.task.ImageRenderOnline;
import com.foodorder.tatsuya.foodorder.task.OnTaskCompleted;
import com.squareup.picasso.Picasso;

import java.lang.annotation.Target;

public class ViewDetail extends AppCompatActivity implements OnTaskCompleted<Boolean> {
    private TextView textViewPrice;
    private TextView textViewName;
    private Button btnAddToMeal;
    private ImageView imgFood, imgShareFB;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    private com.squareup.picasso.Target target = new com.squareup.picasso.Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            SharePhoto sharePhoto = new SharePhoto.Builder().setBitmap(bitmap).build();
            if (ShareDialog.canShow(SharePhotoContent.class)) {
                SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(sharePhoto).build();
                shareDialog.show(content);
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_view_detail);
        this.textViewPrice = super.findViewById(R.id.tv_price_detail);
        this.textViewName = super.findViewById(R.id.tv_name);
        this.btnAddToMeal = super.findViewById(R.id.btnAddToMeal);
        this.imgFood = super.findViewById(R.id.img_food_detail);
        this.imgShareFB = super.findViewById(R.id.img_share_facebook);

        Intent intent = getIntent();
        Food foodItem = (Food) intent.getSerializableExtra("foodDetail");
        System.out.println(foodItem);
        this.textViewName.setText(foodItem.getProductName());
        textViewPrice.setText(String.valueOf(foodItem.getPrice()));
        new ImageRenderOnline(imgFood).execute(foodItem.getImageURL());
        this.btnAddToMeal.setOnClickListener(view -> {
            new AddToMeal(ViewDetail.this, ViewDetail.this)
                    .execute(foodItem);
        });

        //shareFB();

    }

    private void shareFB() {
        this.callbackManager = CallbackManager.Factory.create();
        this.shareDialog = new ShareDialog(this);

        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(ViewDetail.this, "Share OK", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(ViewDetail.this, "Share failed", Toast.LENGTH_SHORT).show();

            }
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
