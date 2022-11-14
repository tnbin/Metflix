package com.example.metflix;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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


public class LoginActivity extends AppCompatActivity {

    final static int LOGIN_SUCCESS = 1;
    final static int WRONG_PW = 2;
    final static int WRONG_IDPW = 3;

    DB mydb;
    SQLiteDatabase mdb;
    Cursor cursor;
    EditText id, pw;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movielogin);
        mydb = new DB(this, "mydb.db",null, 1);

        mdb = mydb.getWritableDatabase();
        DataBaseController dc = new DataBaseController(cursor, mdb);

        id = (EditText) findViewById(R.id.et_id);
        pw = (EditText) findViewById(R.id.et_pw);
        Button btn_login = (Button) findViewById(R.id.btn_login);
        Button btn_join = (Button) findViewById(R.id.btn_join);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id = id.getText().toString();
                String user_pw = pw.getText().toString();

                switch (dc.ck_login(user_id, user_pw)) {
                    case LOGIN_SUCCESS:
                        Log.i("jenn", "아이디 :" + user_id + "비밀번호 :" + user_pw);
                        intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("userid", user_id);
                        setResult(RESULT_OK, intent);
                        finish();
                        break;
                    case WRONG_PW:
                        Toast.makeText(LoginActivity.this, "비밀번호 틀림", Toast.LENGTH_LONG).show();
                        break;
                    case WRONG_IDPW:
                        Toast.makeText(LoginActivity.this, "존재하지 않는 회원정보", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });
    }
}
