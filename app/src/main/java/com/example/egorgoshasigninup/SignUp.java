package com.example.egorgoshasigninup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Size;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import Custom.DbBitmapUtility;
import in.mayanknagwanshi.imagepicker.ImageSelectActivity;
import Custom.CustomMethods;

import static Custom.CustomMethods.checkPassAndLogin;
import static Custom.CustomMethods.md5;

public class SignUp extends AppCompatActivity {

    EditText etEmail, etPass;
    Spinner sp;
    DataBaseHelper db;
    ImageView imgv;
    Bitmap selectedImage = null;
    String imageName = "";
    File imageFile = null;
    byte[] imageBytes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etEmail = findViewById(R.id.etEmailSignUp);
        etPass = findViewById(R.id.etPassSignUp);
        sp = findViewById(R.id.spRole);
        imgv = findViewById(R.id.imgvPhoto);

        db = new DataBaseHelper(this);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, db.getRoles());
        sp.setAdapter(adapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void btnSignUp_Click(View view) throws IOException {
        boolean Check = checkPassAndLogin(etPass.getText().toString(), etEmail.getText().toString());
        if(Check) {
            User user = new User();
            user.setEmail(etEmail.getText().toString());
            user.setPassword(md5(etPass.getText().toString()));
            user.setRoleName(sp.getSelectedItem().toString());

            if (selectedImage != null) {
//                Bitmap thumbnail = ThumbnailUtils.extractThumbnail(selectedImage, 250, 250);
//                user.setThumbnail(DbBitmapUtility.getBytes(thumbnail));
                saveImageToFile();
                user.setOriginalImagePath(imageFile.toString());
                //Size size = new Size(250,250);
                //Bitmap thumb = ThumbnailUtils.createImageThumbnail(imageFile, size, null );
                int k = getImageResizeCoeff(selectedImage);
                user.setThumbnail(DbBitmapUtility.getBytes(ThumbnailUtils.extractThumbnail(selectedImage, selectedImage.getWidth()/k, selectedImage.getHeight()/k)));
            }
                        // записать
            long index = db.createUser(user);
            if (index > 0) {
                Toast.makeText(this, "Пользователь успешно зарегистрирован!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            } else {

            }
        }
        else{
            Toast.makeText(this, "Ваш Логин/Пароль не соответствуют требованиям", Toast.LENGTH_SHORT).show();
        }
    }

    private int getImageResizeCoeff(Bitmap image) {

        int size = DbBitmapUtility.getBytes(image).length;
        return   (size > 2097152)? (size/2097152)+1:1;

    }

    public void saveImageToFile()
    {
        File dir = this.getDir("imageFolder", Context.MODE_PRIVATE);
        if(!dir.exists()){ dir.mkdir(); }
        String nextImageIndex = db.getImageCount();
        imageFile = new File(dir, imageName + nextImageIndex);
        imageBytes = DbBitmapUtility.getBytes(selectedImage);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(imageFile);
            out.write(imageBytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ;
    }

    public void btnPhoto_Click(View view) {
        Intent intent = new Intent(this, ImageSelectActivity.class);
        intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, false);//default is true
        intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);//default is true
        intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);//default is true
        startActivityForResult(intent, 1213);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1213 && resultCode == Activity.RESULT_OK) {
            String filePath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
            selectedImage = BitmapFactory.decodeFile(filePath);
            imageName = filePath.substring(filePath.lastIndexOf('/')+1 , filePath.lastIndexOf('.'));
            imgv.setImageBitmap(selectedImage);
        }
    }
}
