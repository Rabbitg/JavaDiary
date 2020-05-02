package com.example.assignment.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.assignment.Item.MemoListItem;
import com.example.assignment.Util.DBHelper;
import com.example.assignment.Adapter.MemoListAdapter;
import com.example.assignment.R;

import java.util.ArrayList;
/***
 * 글 작성 클릭시 나오는 리스트뷰 화면이다.
 */

public class ListViewActivity extends AppCompatActivity {
    private ListView mListView;
    public static final int DATABASE_VERSION = 1;
                                                            /** 이미지 배열에 추가 **/
    final int basic_emoticon_image[] = {R.drawable.emotion_sad, R.drawable.emotion_surprise, R.drawable.emotion_love, R.drawable.emotion_happy, R.drawable.emotion_sleepy};
    private SQLiteDatabase db;
    private DBHelper helper;
    private MemoListAdapter memoListAdapter;
    private ArrayList<MemoListItem> list = new ArrayList<>();
    private MemoListItem myItem;

    int resId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        /** 위젯과 멤버변수 참조 획득 **/
        mListView = (ListView) findViewById(R.id.listview);
        /** 메모 리스트 어댑터 **/
        memoListAdapter = new MemoListAdapter();
        /** 리스트뷰 액티비티 DBHelper **/
        helper = new DBHelper(ListViewActivity.this);
        db = helper.getWritableDatabase();

        dataSetting();
        /** 아이템 추가 및 어댑터 등록 **/
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int _id = list.get(position).get_id();
                String name = list.get(position).getName();
                String content = list.get(position).getContent();
                String date = list.get(position).getDate();
                int emoticon = list.get(position).getEmoticon();
                /** 로그 기록 **/
                Log.e("click _id", String.valueOf(_id));
                Log.e("click name", name);
                Log.e("click content", content);
                Log.e("click date", date);
                Log.e("click emoticon", String.valueOf(emoticon));
                /** 읽기 전용 액티비티 **/
                Intent intent = new Intent(getApplicationContext(), ReadOnlyActivity.class);
                intent.putExtra("_id",_id);
                intent.putExtra("name",name);
                intent.putExtra("content",content);
                intent.putExtra("date",date);
                intent.putExtra("emoticon",emoticon);
                startActivity(intent);
                finish();

            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();

    }
                                                            /** 리스트뷰에 보일 날짜 설정 **/
    private void dataSetting() {
        list.clear(); /** 최신화 될때 처리를 위한 리스트뷰 초기화 **/
        Cursor cursor = db.rawQuery("select * from tb_memo", null);
        while (cursor.moveToNext()) {
            /** 변수 추가 **/
            int _id = cursor.getInt(0);
            String name = cursor.getString(1);
            String content = cursor.getString(2);
            int emoction = cursor.getInt(3);
            String date = cursor.getString(4);
            String splitDate[] = date.split("-");
            String week = splitDate[3];
            String day = splitDate[2];
            /** 기본 이모티콘 설정되었을 때 이미지 불러오는 것 **/
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

            /** 아이템에 id,name,content,date,emoction 불러옴 **/
            myItem = new MemoListItem();
            myItem.set_id(_id);
            myItem.setName(name);
            myItem.setContent(content);
            myItem.setDate(date);
            myItem.setEmoticon(emoction);
            list.add(myItem);

            /** 메모리스트 어댑터에 있는 아이템 추가 **/
            memoListAdapter.addItem(name, week, day, resId);
        }
        /** 리스트뷰에 어댑터 등록 **/
        mListView.setAdapter(memoListAdapter);
        mListView.setEmptyView(findViewById(R.id.emptyElement));
        mListView.setEmptyView(findViewById(R.id.img_edit));
        Button img_edit = (Button)findViewById(R.id.img_edit);
        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PostActivity.class));
            }
        });

    }


}

