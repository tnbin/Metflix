package com.example.metflix;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class JoinActivity extends AppCompatActivity {

    DB mydb;
    SQLiteDatabase mdb;
    Cursor cursor;
    EditText jid, jpw, jpwok;
    Intent intent;
    String juser_id, juser_pw, juser_pwok;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);
        jid = (EditText) findViewById(R.id.et_jid);
        jpw = (EditText) findViewById(R.id.et_jpw);
        jpwok = (EditText) findViewById(R.id.et_jpwok);
        Button btn_jjoin = (Button) findViewById(R.id.btn_jjoin);

        mydb = new DB(this, "mydb.db", null, 1);

        mdb = mydb.getWritableDatabase();
        DataBaseController dc = new DataBaseController(cursor, mdb);
        btn_jjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                juser_id = jid.getText().toString();
                juser_pw = jpw.getText().toString();
                juser_pwok = jpwok.getText().toString();

                switch (dc.joinConfirm(juser_id, juser_pw, juser_pwok)) {
                    case 1:
                        Toast.makeText(JoinActivity.this, "아이디 다섯 글자 이상 \n" + "비밀번호 다섯 글자 이상" +
                                "\n 입력해주세요.", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(JoinActivity.this, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(JoinActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        intent = new Intent(JoinActivity.this, MainActivity.class);
                        setResult(RESULT_OK);
                        startActivity(intent);
                        finish();
                        break;
                }
            }
        });
    }
}