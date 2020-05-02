package com.example.assignment.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
 * 수정하기 클릭시 나오는 화면이다.
 */
public class ModifyActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText titleView, contentView;
    Button modifyBtn, cancelBtn;
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

    private String name, content, date;
    private int _id, emoticonIdx;
    private TextView txtCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        mDBHelper = new DBHelper(ModifyActivity.this);
        db = mDBHelper.getWritableDatabase();
        mDBHelper.onCreate(db);


        Intent intent = getIntent();
        _id = intent.getIntExtra("_id", -1);
        name = intent.getStringExtra("name");
        content = intent.getStringExtra("content");
        date = intent.getStringExtra("date");
        emoticonIdx = intent.getIntExtra("emoticon", -1);

        /** 달력 결과 텍스트 **/
        txtCalendar = findViewById(R.id.txtCalendar);

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
        /** 제목과 내용 버튼 **/
        titleView = (EditText) findViewById(R.id.titleView);
        contentView = (EditText) findViewById(R.id.contentView);
        modifyBtn = (Button) findViewById(R.id.modifyBtn);
        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = titleView.getText().toString();
                String content = contentView.getText().toString();

                if (txtCalendar.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "날짜를 기입하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (titleView.getText().length() == 0 || contentView.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "제목 또는 내용이 비어있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                /** SQLite Update **/
                db.execSQL("UPDATE tb_memo SET title='" + title + "', content='" + content + "', now_emoticon_state='" + now_emoticon_state +"', date='" + txtCalendar.getText().toString() +"' WHERE _id='" + _id +"';");
                Toast.makeText(getApplicationContext(), "수정이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        /** 취소 버튼 **/
        cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        /** 각 변수마다 텍스트 불러올 것을 설정 **/
        titleView.setText(name);
        contentView.setText(content);
        txtCalendar.setText(date);
        basic_emoticon.setImageResource(basic_emoticon_image[emoticonIdx]);

    }
    /** 제목과 내용 설정 **/
    public void onClick(View v) {
        String title = titleView.getText().toString();
        String content = contentView.getText().toString();

        db.execSQL("insert into tb_memo(title, content) values(?,?)",
                new String[]{title, content});
        db.close();

        Intent intent = new Intent(this, ListViewActivity.class);
        startActivity(intent);
    }

    /** 갤러리 이미지 불러왔을 때 결과 **/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImageUri = data.getData();
            imageView.setImageURI(selectedImageUri);

        }

    }


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
