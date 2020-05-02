package com.example.assignment.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.assignment.R;
/***
 * 로그인 클릭 후 나오는 화면이다.
 */
public class MainActivity extends AppCompatActivity {

    Button btn_edit, btn_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /***
         * 적고싶어 클릭
         */
        btn_edit = findViewById(R.id.img_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PostActivity.class));
            }
        });


        /***
         * 보고싶어 클릭
         */
        btn_list = findViewById(R.id.img_list);
        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ListViewActivity.class));
            }
        });

    }
}
