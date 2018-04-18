package com.foodorder.tatsuya.foodorder.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by tatsuya on 18/03/2018.
 */

public class ImageRenderOnline extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;

    public ImageRenderOnline(ImageView imageView) {
        this.imageView = imageView;
//        Toast.makeText(null, "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
    }

    protected Bitmap doInBackground(String... urls) {
        String imageURL = urls[0];
        Bitmap bimage = null;
        try {
            InputStream in = new java.net.URL(imageURL).openStream();
            bimage = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error Message", e.getMessage());
            e.printStackTrace();
        }
        return bimage;
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}
//using for client:
//new ImageRenderOnline((ImageView) findViewById(R.id.image_view))
//        .execute("http://192.168.1.77:8080/images/burger/bigstar.png");
