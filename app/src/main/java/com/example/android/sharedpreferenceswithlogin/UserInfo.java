package com.example.android.sharedpreferenceswithlogin;

public class UserInfo {
    private String userName = "",password = "";

    public UserInfo(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
