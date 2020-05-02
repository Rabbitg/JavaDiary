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
 * Sign Up 클릭시 나오는 화면이다.
 */
public class SignUpActivity extends AppCompatActivity {

    int version = 1;
    DBHelper helper;
    SQLiteDatabase database;

    EditText idEditText, pwEditText;
    Button btn_signUp;

    String sql;
    Cursor cursor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        idEditText = (EditText)findViewById(R.id.idEditText);
        pwEditText = (EditText)findViewById(R.id.pwEditText);

        btn_signUp = (Button)findViewById(R.id.btn_signUp);

        helper = new DBHelper(SignUpActivity.this, DBHelper.tb_info, null, version);
        database = helper.getWritableDatabase();
        /** 회원 가입 클릭 시**/
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idEditText.getText().toString();
                String pw = pwEditText.getText().toString();
                /** 아이디 비밀번호 입력하지 않을 시 **/
                if(id.length() == 0 || pw.length() == 0){
                    Toast.makeText(SignUpActivity.this, "아이디와 비밀번호는 필수 입력사항입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                sql = "SELECT id FROM "+ helper.tb_info + " WHERE id = '" + id + "'";
                cursor = database.rawQuery(sql, null);
                if(cursor.getCount() != 0){
                    /** 존재하는 아이디입니다. **/
                    Toast toast = Toast.makeText(SignUpActivity.this, "존재하는 아이디입니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    /** 가입 완료하였을 때 db에 저장하고 로그인액티비티로 전환 **/
                    helper.insertUser(database,id,pw);
                    Toast toast = Toast.makeText(SignUpActivity.this, "가입이 완료되었습니다. 로그인을 해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
