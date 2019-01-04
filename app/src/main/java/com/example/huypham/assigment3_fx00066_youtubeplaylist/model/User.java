package com.example.huypham.assigment3_fx00066_youtubeplaylist.model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    public static String TABLE_NAME ="user";
    public static String COLUMN_ID = "userID";
    public static String COLUMN_NAME = "name";
    public static String COLUMN_PASSWORD = "password";
    private static ArrayList<Video> listVideoHistory;
    private String userName;
    private int id;
    private String password;
    public User() {
    }

    public User(String userName, int id, String password) {
        this.userName = userName;
        this.id = id;
        this.password = password;
    }

    public static String getTableName(){
        return TABLE_NAME;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Creat table SQL query
    public static final String CREATE_TABLE =  "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_PASSWORD + " TEXT"
            + ")";
    public static ArrayList<Video> getListVideoHistory() {
        return listVideoHistory;
    }

    public static void setListVideoHistory(ArrayList<Video> listVideoHistory) {
        User.listVideoHistory = listVideoHistory;
    }
}
