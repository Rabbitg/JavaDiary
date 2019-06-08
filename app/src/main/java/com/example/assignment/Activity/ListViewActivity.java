package com.example.assignment.Activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.assignment.Util.DBHelper;
import com.example.assignment.Adapter.MyAdapter;
import com.example.assignment.R;

public class ListViewActivity extends AppCompatActivity {
    private ListView mListView;
    public static final int DATABASE_VERSION = 1;
    final int basic_emoticon_image[] = {R.drawable.emotion_sad, R.drawable.emotion_surprise, R.drawable.emotion_love, R.drawable.emotion_happy, R.drawable.emotion_sleepy};

    int resId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        /* 위젯과 멤버변수 참조 획득 */
        mListView = (ListView) findViewById(R.id.listview);

        /* 아이템 추가 및 어댑터 등록 */

        dataSetting();
    }

    private void dataSetting() {

        MyAdapter mMyAdapter = new MyAdapter();
        DBHelper helper = new DBHelper(ListViewActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();

        int cnt = 0;
        Cursor cursor = db.rawQuery("select * from tb_memo", null);
        cnt = cursor.getCount();

        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            String content = cursor.getString(2);
            int emoction = cursor.getInt(3);
            String date = cursor.getString(4);
            String splitDate[] = date.split("-");
            String week = splitDate[3];
            String day = splitDate[2];
            if (emoction == 0) {
                resId = basic_emoticon_image[0];
            } else if (emoction == 1) {
                resId = basic_emoticon_image[1];
            } else if (emoction == 2) {
                resId = basic_emoticon_image[2];
            } else if (emoction == 3) {
                resId = basic_emoticon_image[3];
            } else if (emoction == 4) {
                resId = basic_emoticon_image[4];
            }
            Log.e("name", name);
            Log.e("content", content);
            Log.e("week", week);
            Log.e("day", day);
            Log.e("res id", String.valueOf(emoction));
            mMyAdapter.addItem(name, week, day, resId);
        }

        /* 리스트뷰에 어댑터 등록 */
        mListView.setAdapter(mMyAdapter);
    }

}

