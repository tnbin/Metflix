package com.example.metflix;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends AppCompatActivity {
    DB mydb;
    Intent intent;
    TextView tvmv;
    Cursor cursor;
    SQLiteDatabase mdb;
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moviemy);

        //DB 생성
        mydb = new DB(this, "mydb.db",null, 1);

        //read, write 수행할 db를 mdb로 생성
        mdb = mydb.getWritableDatabase();

        //sql 관련 함수 사용을 위한 DataBaseController 클래스 선언.
        DataBaseController dbc = new DataBaseController(cursor, mdb);

        Button back_btn = findViewById(R.id.btn_myback);
        back_btn.setOnClickListener(listener);

        //MainActivity에서 넘겨준 Intent
        intent = getIntent();
        //Main에서 userid로 넘겨준 user_id 값 가져오기
        user_id = intent.getStringExtra("userid");
        Log.i("leehj", user_id);

        TextView tv = findViewById(R.id.tv_mv);
        tv.setText(user_id + "님 환영합니다.");

        String[] data = dbc.myTicketData(user_id);
        //data[0]: user_id, data[1]: img_res, data[2]: movie_name, data[3]: user_ticket

        List<String> list = new ArrayList<String>();
        ImageView iv=findViewById(R.id.iv_mymv);
        TextView tv1=findViewById(R.id.tv_mvid);
        TextView tv2=findViewById(R.id.tv_mvname);
        TextView tv3=findViewById(R.id.tv_mvseat);
        tv1.setText(data[0]);

        int img_res = Integer.parseInt(data[1]);
        iv.setImageResource(img_res);
        tv2.setText(data[2]);
        //u_ticket에 저장된 #제거후 String
        String[] user_ticket = data[3].split("#");
        for(int i = 0; i<user_ticket.length; i++) {
            list.add(user_ticket[i] + "번");
        }
        String s2 = list.toString();
        //리스트 생성시 만들어지는 [] 제거.
        String s1 = s2.replaceAll("(?:\\[|null|\\]| +)", "");
        tv3.setText(s1);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            intent = new Intent(MyActivity.this, MainActivity.class);
            setResult(RESULT_OK, intent);
            finish();
        }
    };
}