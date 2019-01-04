package com.example.huypham.assigment3_fx00066_youtubeplaylist.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.huypham.assigment3_fx00066_youtubeplaylist.model.User;
import com.example.huypham.assigment3_fx00066_youtubeplaylist.model.Video;

import java.util.ArrayList;
import java.util.List;

public class SQliteHelper extends SQLiteOpenHelper{

    private final String TAG ="SQLite_TAG";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "database";


    public SQliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(Video.CREATE_TABLE);
        db.execSQL(User.CREATE_TABLE);

        // Create Video History table
        String CREATE_TABLE_HISTORY =  "CREATE TABLE history( count INTEGER PRIMARY KEY AUTOINCREMENT, " + User.COLUMN_NAME +" TEXT, " +
         Video.COLUMN_ID+" TEXT)";
        db.execSQL(CREATE_TABLE_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+User.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ "history");
        // Upgrade Video History table
    }

    public void addUser(String name,String password) {
        //TODO: Xử lý logic lưu thông tin tài khoản
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(User.COLUMN_NAME,name);
        values.put(User.COLUMN_PASSWORD,password);
        sqLiteDatabase.insert(User.TABLE_NAME,null,values);
        sqLiteDatabase.close();

    }

    public boolean isCheckUserExist(String userName) {
        //TODO: Xử lý logic kiểm tra tài khoản đã tồn tại trong csdl hay chưa
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String selectQuery =  "SELECT " + User.COLUMN_NAME + " FROM " + User.TABLE_NAME + " WHERE " + User.COLUMN_NAME + "= '" + userName + "'";
        // sqLiteDatabase.
        Cursor  cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        if(cursor.getCount() <= 0){
                cursor.close();
                return false;
        }
        cursor.close();
        return true;

    }

    public boolean isCheckUserSignIn(String userName, String password ){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String selectQuery =  "SELECT " + User.COLUMN_NAME + " FROM " + User.TABLE_NAME + " WHERE " + User.COLUMN_NAME + " = '" + userName + "'" + "AND " + User.COLUMN_PASSWORD + "= '" + password+"'";
        Cursor  cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean isVideoExistHistory(String videoID){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + "history WHERE " + Video.COLUMN_ID + " ='"+ videoID+"'";
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        if(cursor.getCount() <=0){
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean deleteVideoHistory(String userName){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
         return sqLiteDatabase.delete("history",User.COLUMN_NAME + " = '" + userName +"'",null) >0;
    }

    public void addVideoHistory(String userName, String videoID) {
        //TODO: Xử lý logic lưu thôn\g tin video đã xem
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(User.COLUMN_NAME,userName);
        values.put(Video.COLUMN_ID,videoID);
        sqLiteDatabase.insert("history",null,values);
        sqLiteDatabase.close();
    }
    public List<String> getVideosByUser(String userName) {
        //TODO: Lấy danh sách video theo tài khoản người dùng
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        List<String> list = new ArrayList<>();
        String selectQuery =  "SELECT * FROM history WHERE " + User.COLUMN_NAME + " = '" + userName + "'";
        Cursor  cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String videoID = cursor.getString(cursor.getColumnIndex(Video.COLUMN_ID));
                list.add(videoID);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }


}
