package com.example.cyhtest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.security.PublicKey;

/**
 * Created by Lociation on 2016/11/19.
 */

public class ManageSQL {
    public myDB mDB;
    public ManageSQL(Context context){
        this.mDB = new myDB(context);
    }

    public void insertSQL(String name, String pwd){
        SQLiteDatabase db = mDB.getWritableDatabase();
        String sql = "insert into passSQL(name, pwd) values(?, ?)";
        db.execSQL(sql, new String[]{name, pwd});
    }

    public void updateSQL(String name, String pwd){
        SQLiteDatabase db = mDB.getWritableDatabase();
        String sql = "update passSQL set pwd=? where name=?";
        Log.d("Tag", sql);
        db.execSQL(sql, new String[]{pwd, name});
    }

    public Boolean selectSQL(String name){
        //判断名字是否在数据库之中
        SQLiteDatabase db = mDB.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from passSQL where name=?", new String[]{name});
        if (cursor.moveToFirst()){
            cursor.close();
            return  true;
        }
        return false;
    }

    public String repwd(String name){
        //判断名字是否在数据库之中
        String pwd;
        SQLiteDatabase db = mDB.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from passSQL where name=?", new String[]{name});
        if (cursor.moveToFirst()){
            pwd = cursor.getString(cursor.getColumnIndex("pwd"));
            cursor.close();
            return pwd;
        }
        return null;
    }

    public void deleteSQL(String name){
        SQLiteDatabase db = mDB.getWritableDatabase();
        String sql = "delete from passSQL where name = ?";
        db.execSQL(sql, new String[]{name});
    }
}
