package com.example.metflix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;

public class BookActivity extends AppCompatActivity {
    DB mydb;
    SQLiteDatabase mdb;
    Cursor cursor;
    DataBaseController dbc;

    Cursor mCursor;
    Cursor uCursor;
    SimpleCursorAdapter mca;

    ListView lv_mv;
    static String seat = "";        //db 들어갈 seat
    static String temp_seat = "";       //코드 안에서 돌아갈 seat
    static int temp_mid;                //선택된 영화 id
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moviebook);
        user_id = getIntent().getStringExtra("userid");

        mydb = new DB(this, "mydb.db", null, 1);
        mdb = mydb.getWritableDatabase();
        dbc = new DataBaseController(cursor, mdb);


        mCursor = mdb.rawQuery("SELECT * FROM movie", null);
        uCursor = mdb.rawQuery("SELECT * FROM user WHERE u_name = '" + user_id + "'", null);     //로그인 시 해당 유저 정보만 긁어오도록 수정 필요
        String movieCol[] ={"m_name"};
        lv_mv = findViewById(R.id.lv_mv);
        mca = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, mCursor, movieCol,
                new int[]{android.R.id.text1}, 1);
        lv_mv.setAdapter(mca);
        lv_mv.setOnItemClickListener(lis_lv_book);

        Button btn_cancel = findViewById(R.id.btn_cancel);
        Button btn_book = findViewById(R.id.btn_book);
        btn_cancel.setOnClickListener(lis_btn_cancel);
        btn_book.setOnClickListener(lis_btn_book);

    }

    AdapterView.OnItemClickListener lis_lv_book = new AdapterView.OnItemClickListener() {
        @Override               // 리스트 아이템클릭 리스너
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            findViewById(R.id.layout_seat).setVisibility(View.VISIBLE);     //영화 선택시 좌석 선택 활성화
            temp_seat = "";
            mCursor.moveToPosition(position);
            temp_mid = mCursor.getInt(0);       //영화 id 받아옴
            temp_seat = mCursor.getString(3);       //예매된 자리 받아옴
            String[] seat_arr;                     //자리 배열 생성
            setAllVis();
            if (temp_seat != null){// 예매된 자리가 있으면 실행
                seat_arr = temp_seat.split("#");            //자리 문자열 분리
                setSelInvis(seat_arr);
            }
        }
    };

    View.OnClickListener lis_btn_book = new View.OnClickListener() {
        @Override                   // 선택 확인 버튼 리스터
        public void onClick(View view) {
            if (seat != ""){            // 좌석을 선택 했으면 db에 데이터 삽입
                dbc.update_data(user_id, seat, temp_mid);
            }
            seat = "";                  //좌석 선택 초기화

            Intent intent = new Intent(BookActivity.this, MainActivity.class);
            intent.putExtra("userid", user_id);
            setResult(RESULT_OK, intent);
            finish();
        }
    };

    View.OnClickListener lis_btn_cancel = new View.OnClickListener() { //취소버튼 리스너
        @Override
        public void onClick(View view) {
            int id = R.id.btn_seat1;
            setAllVis();
            if(temp_seat != null) {
                String[] seat_arr = temp_seat.split("#");
                setSelInvis(seat_arr);
            }
            seat="";
        }
    };

    public void btnClick(View v){  //onClick으로 버튼 클릭 시 해당 버튼에 invisible 부여
        v.setVisibility(View.INVISIBLE);
        Button btn = findViewById(v.getId());
        seat += btn.getText().toString() + "#"; //seat에 좌석 번호 + #(파싱 위한 문자) 저장

        Log.i("lsj", seat);
    }

    public void setAllVis(){
        int id = R.id.btn_seat1;
        switch (id){      //버튼 클릭 시 모든 버튼 활성화
            case R.id.btn_seat1:findViewById(R.id.btn_seat1).setVisibility(View.VISIBLE);
            case R.id.btn_seat2 : findViewById(R.id.btn_seat2).setVisibility(View.VISIBLE);
            case R.id.btn_seat3 : findViewById(R.id.btn_seat3).setVisibility(View.VISIBLE);
            case R.id.btn_seat4 : findViewById(R.id.btn_seat4).setVisibility(View.VISIBLE);
            case R.id.btn_seat5 : findViewById(R.id.btn_seat5).setVisibility(View.VISIBLE);
            case R.id.btn_seat6 : findViewById(R.id.btn_seat6).setVisibility(View.VISIBLE);
            case R.id.btn_seat7 : findViewById(R.id.btn_seat7).setVisibility(View.VISIBLE);
            case R.id.btn_seat8 : findViewById(R.id.btn_seat8).setVisibility(View.VISIBLE);
            case R.id.btn_seat9 : findViewById(R.id.btn_seat9).setVisibility(View.VISIBLE);
            case R.id.btn_seat10 : findViewById(R.id.btn_seat10).setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setSelInvis(String arr[]){
        for(int i = 0; i < arr.length;i++){            //for문 돌려 예매된 자리 선택 불가 처리
            switch (arr[i]){
                case "1" :  findViewById(R.id.btn_seat1).setVisibility(View.INVISIBLE);
                    break;
                case "2" : findViewById(R.id.btn_seat2).setVisibility(View.INVISIBLE);
                    break;
                case "3" : findViewById(R.id.btn_seat3).setVisibility(View.INVISIBLE);
                    break;
                case "4" : findViewById(R.id.btn_seat4).setVisibility(View.INVISIBLE);
                    break;
                case "5" : findViewById(R.id.btn_seat5).setVisibility(View.INVISIBLE);
                    break;
                case "6" : findViewById(R.id.btn_seat6).setVisibility(View.INVISIBLE);
                    break;
                case "7" : findViewById(R.id.btn_seat7).setVisibility(View.INVISIBLE);
                    break;
                case "8" : findViewById(R.id.btn_seat8).setVisibility(View.INVISIBLE);
                    break;
                case "9": findViewById(R.id.btn_seat9).setVisibility(View.INVISIBLE);
                    break;
                case "10" : findViewById(R.id.btn_seat10).setVisibility(View.INVISIBLE);
                    break;
            }
        }
    }
}