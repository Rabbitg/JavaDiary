package com.example.assignment.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment.Util.DBHelper;
import com.example.assignment.Fragment.DatePickerFragment;
import com.example.assignment.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
/***
 * 게시글 작성$ 클릭시 나오는 화면이다.
 */
public class PostActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText titleView, contentView;
    Button postBtn;
    ImageView basic_emoticon, emoticon1, emoticon2, emoticon3, emoticon4, emoticon5, imageView;
    LinearLayout basic_emoticon_layout;
    public static DBHelper mDBHelper;
    public static SQLiteDatabase db;

    private final int GET_GALLERY_IMAGE = 200;
    int basic_layout_state = 0;
    /** 지금 감정 상태 리스트뷰에 뿌려주기 **/
    int emoticon_state[] = {0, 1, 2, 3, 4};
    int now_emoticon_state = 0;
    private String currentDateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mDBHelper = new DBHelper(PostActivity.this);
        db = mDBHelper.getWritableDatabase();
        mDBHelper.onCreate(db);


        /** 이모티콘 버튼 눌렀을 때 **/
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
        /** 객체 인스턴스화를 위한 id 저장 **/
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
        /** 제목과 내용을 입력하고 난 후 작성 버튼 후 **/
        titleView = (EditText) findViewById(R.id.titleView);
        contentView = (EditText) findViewById(R.id.contentView);
        postBtn = (Button) findViewById(R.id.writeBtn);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = titleView.getText().toString();
                String content = contentView.getText().toString();

                if (currentDateString == null) {
                    Toast.makeText(getApplicationContext(), "날짜를 기입하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (titleView.getText().length() == 0 || contentView.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "제목 또는 내용이 비어있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                /** SQLite INSERT **/
                db.execSQL("INSERT INTO tb_memo(title, content, now_emoticon_state, date) VALUES('" + title + "','" + content + "', '" + now_emoticon_state + "' , '" + currentDateString + "');");
                /** 리스트뷰 결과 **/
                Intent intent = new Intent(PostActivity.this, ListViewActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /** 달력 버튼 **/
        Button picker_btn = (Button) findViewById(R.id.picker_btn);
        picker_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
                Log.e("curDate", datePicker.toString());
            }
        });

        /** 갤러리 버튼 **/
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });
    }


    /** 갤러리 이미지 불러온 결과 **/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImageUri = data.getData();
            imageView.setImageURI(selectedImageUri);

        }

    }

    /** 달력 날짜 불러오기 **/
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        currentDateString = new SimpleDateFormat("yyyy-MM-dd-E").format(c.getTime());

        TextView txtCalendar = (TextView) findViewById(R.id.txtCalendar);
        txtCalendar.setText(currentDateString);
        Log.e("TEST HONG", currentDateString);
    }
}
