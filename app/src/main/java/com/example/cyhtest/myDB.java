package com.example.cyhtest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lociation on 2016/11/19.
 */

public class myDB extends SQLiteOpenHelper {

    private static final String DATABASENAME = "passSQL";//数据库名称
    private static final int VERSION = 2;//数据库版本
    final String CREATE_TABLE_SQL = "create table passSQL("
            + "id integer primary key autoincrement,"
            + "name varchar(20),"
            + "pwd varchar(20),"
            + "emial varchar(20))";
    //调用父类构造器
    public myDB(Context context){
        super(context, DATABASENAME, null, VERSION);
    }
    /**
     * 当数据库首次创建时执行该方法，一般将创建表等初始化操作放在该方法中执行.
     * 重写onCreate方法，调用execSQL方法创建表
     * */
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_SQL);
    }

    //当打开数据库时传入的版本号与当前的版本号不同时会调用该方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists passSQL";
        db.execSQL(sql);
        onCreate(db);
    }
}
