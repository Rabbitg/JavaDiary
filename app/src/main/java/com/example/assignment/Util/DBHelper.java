package com.example.assignment.Util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;

    public DBHelper(Context context) {
        super(context, "memodb", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String memoSQL = "create table if not exists tb_memo" +
//                "(_id integer primary key autoincrement," + "title," + "content," + "now_emoticon_state)";
//        db.execSQL(memoSQL);
        db.execSQL("CREATE TABLE IF NOT EXISTS tb_memo(_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, content TEXT NOT NULL, now_emoticon_state INTEGER, date TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == DATABASE_VERSION) {
            db.execSQL("drop table tb_memo");
            onCreate(db);
        }

    }



    public Cursor selectHomeCareInfo() {
        return mDB.rawQuery("SELECT * FROM tb_memo", null);
    }
}
