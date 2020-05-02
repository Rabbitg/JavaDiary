package com.example.assignment.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment.R;
import com.example.assignment.Util.DBHelper;
/***
 * 로그인 화면이다.
 */
public class LoginActivity extends AppCompatActivity {
    /** 변수 선언 **/
    int version = 1;
    DBHelper helper;
    SQLiteDatabase database;

    EditText idEditText, pwEditText;
    Button btn_signUp, btn_login;

    String sql;
    Cursor cursor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        idEditText = (EditText)findViewById(R.id.idEditText);
        pwEditText = (EditText)findViewById(R.id.pwEditText);
        /** tb_info에서 로그인 회원 정보 db 받아오기 **/
        helper = new DBHelper(LoginActivity.this, DBHelper.tb_info, null, version);
        database = helper.getWritableDatabase();
        /** 로그인 **/
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idEditText.getText().toString();
                String pw = pwEditText.getText().toString();

                helper.createTable(database);
                /** 아이디와 비밀번호를 입력하지 않았을 때 **/
                if(id.length() == 0 || pw.length() == 0){
                    Toast.makeText(LoginActivity.this, "아이디와 비밀번호는 필수 입력사항입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                sql = "SELECT id FROM "+ helper.tb_info + " WHERE id = '" + id + "'";
                cursor = database.rawQuery(sql, null);

                /** 아이디가 존재하지 않을 경우 **/
                if(cursor.getCount() != 1){
                    Toast.makeText(LoginActivity.this, "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                sql = "SELECT pw FROM " + helper.tb_info + " WHERE id = '" + id + "'";
                cursor = database.rawQuery(sql,null);

                /** 비밀번호 틀릴 경우와 로그인 성공 **/
                cursor.moveToNext();
                if(!pw.equals(cursor.getString(0))){
                    Toast.makeText(LoginActivity.this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                cursor.close();
            }
        });


        /** 회원가입 **/
        btn_signUp = findViewById(R.id.btn_signUp);
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
