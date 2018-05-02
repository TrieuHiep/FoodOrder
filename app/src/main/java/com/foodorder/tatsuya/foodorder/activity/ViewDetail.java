package com.foodorder.tatsuya.foodorder.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.foodorder.tatsuya.foodorder.R;
import com.foodorder.tatsuya.foodorder.model.foodpkg.Food;
import com.foodorder.tatsuya.foodorder.task.AddToMeal;
import com.foodorder.tatsuya.foodorder.task.OnTaskCompleted;

public class ViewDetail extends AppCompatActivity implements OnTaskCompleted<Boolean> {
    private TextView textViewPrice;
    private TextView textViewName;
    private Button btnAddToMeal, btnShare;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext()); //dong nay cua t ị gach nay - ko qtrong
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_view_detail);
        this.textViewPrice = super.findViewById(R.id.hihiTextView);
        this.textViewName = super.findViewById(R.id.tv_name);
        this.btnAddToMeal = super.findViewById(R.id.btnAddToMeal);

        shareDialog = new ShareDialog(ViewDetail.this);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://www.google.com.vn/search?q=humberger&safe=active&source=lnms&tbm=isch&sa=X&ved=0ahUKEwjA8s2Dy9TaAhWIv7wKHeyYAj4Q_AUICigB&biw=1600&bih=720#imgrc=jC-ULwW9-OPkXM:"))
                        .build();

                shareDialog.show(content);

            }
        });

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
