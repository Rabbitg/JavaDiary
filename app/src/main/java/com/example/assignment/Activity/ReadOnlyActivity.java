package com.example.assignment.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment.Fragment.DatePickerFragment;
import com.example.assignment.R;
import com.example.assignment.Util.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/***
 * 게시글 클릭시 나오는 화면이다.
 */
public class ReadOnlyActivity extends AppCompatActivity {

    TextView titleView, contentView, txtCalendar;
    Button modifyBtn, deleteBtn;
    ImageView basic_emoticon, emoticon1, emoticon2, emoticon3, emoticon4, emoticon5, imageView;
    LinearLayout basic_emoticon_layout;
    public static DBHelper mDBHelper;
    public static SQLiteDatabase db;

    private final int GET_GALLERY_IMAGE = 200;
    int basic_layout_state = 0;
    //지금 감정 상태 리스트뷰에 뿌려주기
    int emoticon_state[] = {0, 1, 2, 3, 4};
    int now_emoticon_state = 0;
    private String currentDateString;

    private String name, content, date;
    private int _id, emoticonIdx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readonly);

        mDBHelper = new DBHelper(ReadOnlyActivity.this);
        db = mDBHelper.getWritableDatabase();
        mDBHelper.onCreate(db);

        /** 인텐트를 불러올 때 같이 불러올 값 **/
        Intent intent = getIntent();
        _id = intent.getIntExtra("_id", -1);
        name = intent.getStringExtra("name");
        content = intent.getStringExtra("content");
        date = intent.getStringExtra("date");
        emoticonIdx = intent.getIntExtra("emoticon", -1);


        txtCalendar = findViewById(R.id.txtCalendar);

        /** 이모티콘 버튼을 누른다 **/
        basic_emoticon = (ImageView) findViewById(R.id.basic_emoticon);
        basic_emoticon_layout = (LinearLayout) findViewById(R.id.basic_emoticon_layout);
        basic_emoticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (basic_layout_state == 0) {
                    basic_emoticon_layout.setVisibility(View.VISIBLE);
                    basic_layout_state = 1;
                } else if (basic_layout_state == 1) {
                    basic_emoticon_layout.setVisibility(View.INVISIBLE);
                    basic_layout_state = 0;
                }
            }
        });

        /** 이모티콘을 선택했을 때 **/
        ImageView emoticon[] = {emoticon1, emoticon2, emoticon3, emoticon4, emoticon5};
        /**객체 인스턴스화를 위한 id 저장 **/
        final int basic_emoticon_id[] = {R.id.iv_emoticon1, R.id.iv_emoticon2, R.id.iv_emoticon3, R.id.iv_emoticon4, R.id.iv_emoticon5};
        /** 이미지가 drawable에 있는 이미지를 배열로 저장 **/
        final int basic_emoticon_image[] = {R.drawable.emotion_sad, R.drawable.emotion_surprise, R.drawable.emotion_love, R.drawable.emotion_happy, R.drawable.emotion_sleepy};
        for (int i = 0; i < 5; i++) {
            final int f_i = i;
            emoticon[i] = (ImageView) findViewById(basic_emoticon_id[i]);
            emoticon[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    basic_emoticon.setImageResource(basic_emoticon_image[f_i]);
                    basic_emoticon_layout.setVisibility(View.INVISIBLE);
                    basic_layout_state = 0;
                    /** 지금 감정상태 표현 **/
                    now_emoticon_state = emoticon_state[f_i];
                }
            });
        }

        titleView = (TextView) findViewById(R.id.titleView);
        contentView = (TextView) findViewById(R.id.contentView);

        /***
         * 수정버튼
         */
        modifyBtn = (Button) findViewById(R.id.modifyBtn);
        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modIntent = new Intent(getApplicationContext(), ModifyActivity.class);
                modIntent.putExtra("_id", _id);
                modIntent.putExtra("name", name);
                modIntent.putExtra("content", content);
                modIntent.putExtra("date", date);
                modIntent.putExtra("emoticon", emoticonIdx);
                startActivity(modIntent);
                finish();
            }
        });

        /***
         * 삭제버튼
         */
        deleteBtn = findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(ReadOnlyActivity.this);
                dialog.setContentView(R.layout.dialog_delete);
                TextView tv_dlg_title = dialog.findViewById(R.id.tv_dlg_title);
                TextView tv_dlg_content = dialog.findViewById(R.id.tv_dlg_content);
                tv_dlg_content.setText("정말 삭제하시겠습니까?");

                /** 취소 버튼 **/
                Button btn_dlg_cancel = dialog.findViewById(R.id.btn_dlg_cancel);
                btn_dlg_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                /** 확인 버튼 **/
                Button btn_dlg_ok = dialog.findViewById(R.id.btn_dlg_ok);
                btn_dlg_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.execSQL("DELETE FROM tb_memo WHERE _id='" + _id + "';");
                        Toast.makeText(getApplicationContext(), "삭제가 완료되었습니다.",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        finish();
                    }
                });
                dialog.show();

            }
        });
        /** 글 설정 **/
        titleView.setText(name);
        contentView.setText(content);
        txtCalendar.setText(date);
        basic_emoticon.setImageResource(basic_emoticon_image[emoticonIdx]);


    }


}
