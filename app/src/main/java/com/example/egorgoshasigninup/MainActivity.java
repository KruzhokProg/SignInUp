package com.example.egorgoshasigninup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import static Custom.CustomMethods.md5;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper db;
    CheckBox cb;
    String file_name = "rememberMe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DataBaseHelper(this);
        cb = findViewById(R.id.cbRememberMe);

        checkRememberMe();
    }

    public void checkRememberMe() {
        FileInputStream fin = null;
        try {
            fin = openFileInput(file_name);
            byte[] bytes = new byte[fin.available()];
            if(bytes.length != 0) {
                fin.read(bytes);
                String text = new String(bytes);
                String email = text.substring(0,text.indexOf(";"));
                String password = text.substring(text.indexOf(";")+1,text.length());
                ((EditText) findViewById(R.id.etEmail)).setText(email);
                ((EditText) findViewById(R.id.etPass)).setText(password);
                ((CheckBox)findViewById(R.id.cbRememberMe)).setChecked(true);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnSignUp_Click(View view) {
        Intent i = new Intent(this, SignUp.class);
        startActivity(i);
    }

    public void btnSignIn_Click(View view) {

            String email = ((EditText) findViewById(R.id.etEmail)).getText().toString();
            String pass = ((EditText) findViewById(R.id.etPass)).getText().toString();
            String role = db.checkUser(email, md5(pass));
            if (!role.equals("")) {
                Toast.makeText(this, "Вы успешно авторизовались как " + role, Toast.LENGTH_SHORT).show();
                if(cb.isChecked())
                    saveUserInfoToFile(email + ";" + pass);
                else
                    saveUserInfoToFile("");

             if(role.equals("Manager")){
                 Intent i =new Intent(this,Manager.class);
                 startActivity(i);
             }
            }
            else
                Toast.makeText(this, "Неправильный Email/Password", Toast.LENGTH_SHORT).show();

    }

    private void saveUserInfoToFile(String userInfo) {

        FileOutputStream fos = null;

        try {
            fos = openFileOutput(file_name, MODE_PRIVATE);
            fos.write(userInfo.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
