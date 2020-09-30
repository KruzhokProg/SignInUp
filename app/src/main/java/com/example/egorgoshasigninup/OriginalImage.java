package com.example.egorgoshasigninup;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import Custom.DbBitmapUtility;

import static Custom.CustomMethods.returnUserOI;

public class OriginalImage extends AppCompatActivity {
ImageView imageView;
Bitmap img;
User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_original_image);
        imageView=findViewById(R.id.originalImg);
        String path =getIntent().getExtras().getString("selectedUser");
        img=returnUserOI(path);
        imageView.setImageBitmap(img);
    }
}
