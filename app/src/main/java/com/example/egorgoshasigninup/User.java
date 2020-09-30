package com.example.egorgoshasigninup;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;

public class User implements Parcelable {
    private String Email;
    private String Password;
    private String RoleName;
    private String OriginalImagePath;
    private byte[] Thumbnail;

    public User()
    {

    }

    public User(String email, String password, String roleName, String originalImagePath, byte[] thumbnail) {
        Email = email;
        Password = password;
        RoleName = roleName;
        OriginalImagePath = originalImagePath;
        Thumbnail = thumbnail;
    }

    protected User(Parcel in) {
        Email = in.readString();
        Password = in.readString();
        RoleName = in.readString();
        OriginalImagePath = in.readString();
        Thumbnail = in.createByteArray();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }

    public String getOriginalImagePath() {
        return OriginalImagePath;
    }

    public void setOriginalImagePath(String originalImagePath) {
        OriginalImagePath = originalImagePath;
    }

    public byte[] getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        Thumbnail = thumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Email);
        dest.writeString(Password);
        dest.writeString(RoleName);
        dest.writeString(OriginalImagePath);
        dest.writeByteArray(Thumbnail);
    }
}
