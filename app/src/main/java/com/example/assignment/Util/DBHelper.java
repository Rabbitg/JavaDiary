package com.example.assignment.Util;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/***
 * DB Helper 액티비티이다.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    public  static final String tb_info = "tb_info";
    public DBHelper(Context context) {
        super(context, "memodb", null, DATABASE_VERSION);
    }
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    /** SQLiteDatabase db **/
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS tb_memo(_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, content TEXT NOT NULL, now_emoticon_state INTEGER, date TEXT NOT NULL);");
        db.execSQL("CREATE TABLE IF NOT EXISTS tb_info(_id INTEGER PRIMARY KEY AUTOINCREMENT, id TEXT NOT NULL, pw  TEXT NOT NULL);");
    }
    /** onUpgrade**/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == DATABASE_VERSION) {
            db.execSQL("drop table tb_memo");
            onCreate(db);
        }

    }
    /** 회원가입 했을 때 실행함**/
    public void insertUser(SQLiteDatabase db, String id, String pw){
        Log.i("tag","회원가입을 했을때 실행함");
        db.beginTransaction();
        try {
            String sql = "INSERT INTO " + tb_info + "(id, pw)" + "values('"+ id +"', '"+pw+"')";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }
    /** 테이블 생성 **/
    public void createTable(SQLiteDatabase db){
        String sql = "CREATE TABLE " + tb_info + "(id text, pw text)";
        try {
            db.execSQL(sql);
        }catch (SQLException e){
        }
    }


}

