package com.example.egorgoshasigninup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Custom.DbBitmapUtility;

public class DataBaseHelper extends SQLiteOpenHelper {

    static final String dbName = "LoginDB6";

    static final String userTable = "User";
    static final String colUserEmail  = "Email";
    static final String colUserPass  = "Password";
    static final String colUserRoleName  = "RoleName";
    static final String colUserThumbnail = "Thumbnail";
    static final String colUserOriginalImagePath = "OriginalImagePath";

    static final String roleTable = "Role";
    static final String colRoleName  = "Name";


    public DataBaseHelper(Context context) {
        super(context, dbName, null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + userTable + "( "+
                colUserEmail + " TEXT PRIMARY KEY, " +
                colUserPass + " TEXT NOT NULL, " +
                colUserRoleName + " TEXT NOT NULL,"+
                colUserThumbnail + " BLOB," +
                colUserOriginalImagePath + " TEXT, " +
                        " FOREIGN KEY(" + colUserRoleName + ") REFERENCES " + roleTable + "(" + colRoleName + "));"
                );
        db.execSQL("CREATE TABLE IF NOT EXISTS " + roleTable + "( "+
                colRoleName + " TEXT PRIMARY KEY)"
                );
        db.execSQL("INSERT INTO " + roleTable + " VALUES('Manager');"  );
        db.execSQL("INSERT INTO " + roleTable + " VALUES('Employer');"  );
    }

    public String getImageCount()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT COUNT(*) as 'count' FROM " + userTable;
        Cursor c = db.rawQuery(selectQuery, null);
        Integer count = 0;
        if(c.moveToFirst())
        {
            count = c.getInt(c.getColumnIndex("count"));
        }
        return String.valueOf(count+1);
    }

    public List<User> getUsersByRole(String role)
    {
        ArrayList<User> usersData = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + userTable + " WHERE " + colUserRoleName + "='" + role + "'";
        Cursor c = db.rawQuery(selectQuery, null);
        if(c.moveToFirst())
        {
            do {

//                User newUser=new User(colUserEmail,colUserPass,colUserRoleName,colUserOriginalImagePath,colUserThumbnail);
                String uEmail=c.getString(c.getColumnIndex(colUserEmail));
                String uPass=c.getString(c.getColumnIndex(colUserPass));
                String uRole=c.getString(c.getColumnIndex(colUserRoleName));
                String uOIP=c.getString(c.getColumnIndex(colUserOriginalImagePath));
                byte[] uThumbnail=c.getBlob(c.getColumnIndex(colUserThumbnail));

                User user=new User();//(uEmail,uPass,uRole,uOIP,uThumbnail);
                user.setEmail(uEmail);
                user.setPassword(uPass);
                user.setRoleName(uRole);
                user.setOriginalImagePath(uOIP);
                user.setThumbnail(uThumbnail);

                usersData.add(user);



            }while(c.moveToNext());
        }

        return usersData;


    }

    public long createUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colUserEmail ,user.getEmail());
        cv.put(colUserPass ,user.getPassword());
        cv.put(colUserRoleName, user.getRoleName());
        cv.put(colUserOriginalImagePath,user.getOriginalImagePath());
        cv.put(colUserThumbnail,user.getThumbnail());
        return db.insert(userTable, null, cv);
    }

    public List<String> getRoles()
    {
        List<String> roles = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + colRoleName + " FROM " + roleTable;
        Cursor c = db.rawQuery(selectQuery, null);
        if(c.moveToFirst())
        {
            do {
                roles.add(c.getString(c.getColumnIndex(colRoleName)));
            }while(c.moveToNext());
        }

        return roles;
    }

    public String checkUser(String Email, String Pass)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery ="SELECT * FROM " + userTable + " WHERE " + colUserEmail + "='" + Email + "'" +
                "AND " + colUserPass + "='" + Pass + "'";
        Cursor c = db.rawQuery(selectQuery,null);
        if(c.moveToFirst())
            return c.getString(c.getColumnIndex(colUserRoleName));
        else
            return "";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        onCreate(db);
    }
}
