package com.example.egorgoshasigninup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import Custom.CustomAdapter;
import Custom.DbBitmapUtility;

import static Custom.CustomMethods.returnUserOI;

public class Manager extends AppCompatActivity {

    ListView lstv;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        db=new DataBaseHelper(this);

        lstv = findViewById(R.id.lstv);

        final List<User> users=db.getUsersByRole("Employer");
       CustomAdapter adapter=new CustomAdapter(db.getUsersByRole("Employer"),getBaseContext());
        lstv.setAdapter(adapter);



        lstv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Bitmap UserOriginalImage
                Intent i = new Intent(getApplicationContext(),OriginalImage.class);
                i.putExtra("selectedUser", users.get(position).getOriginalImagePath());
                startActivity(i);
            }
        });



    }


}
