package com.example.metflix;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Array;

public class DataBaseController {
    Cursor mCursor;
    SQLiteDatabase mdb;

    DataBaseController(Cursor mCorsor, SQLiteDatabase mdb){
        this.mCursor = mCorsor;
        this.mdb = mdb;
    }

    //id 일치 여부. 일치 = true, 불일치 = false
    boolean ck_uname(String name){
        boolean result = false;

        String sql = "select u_name from user";
        mCursor = mdb.rawQuery(sql, null);
        for (int i = 0; i < mCursor.getCount(); i++) {
            mCursor.moveToNext();
            String id = mCursor.getString(0);
            if (id.equals(name)) {
                result = true;
                break;
            }
        }
        return result;
    }

    //비밀번호 일치 여부. 일치 = true, 불일치 = false
    boolean ck_pw(String password){
        boolean result = false;

        String sql = "SELECT u_pw FROM user";
        mCursor = mdb.rawQuery(sql, null);
        for (int i = 0; i < mCursor.getCount(); i++) {
            mCursor.moveToNext();
            String pw = mCursor.getString(0);
            if (pw.equals(password)) {
                result = true;
                break;
            }
        }
        return result;
    }


    //회원가입 시 비밀번호 일치 여부 확인. 일치 = true, 불일치 = false
    boolean ck_re_pw(String pw, String re_pw){
        boolean result = false;
        if(pw.equals(re_pw)){
            result = true;
        }else{
            result = false;
        }
        return result;
    }

    //로그인 성공 여부
    int ck_login(String name, String pw){
        int result = 0;
        boolean ck_name = ck_uname(name);
        boolean ck_pw = ck_pw(pw);
        if(ck_name == true){
            if(ck_pw == true) result = 1; //로그인 성공
            else result = 2; //비밀번호 불일치
        }
        else result = 3; //존재하지 않는 회원정보
        return result;
    }

    //회원가입시 데이터베이스에 입력한 id와 비밀번호 값 저장.
    public void insertData(String id, String pw) {
        mdb.execSQL("INSERT INTO user VALUES (null, '" + id + "', '" + pw + "', null, null);");
    }

    //회원가입(수정 코드)
    int joinConfirm(String id, String pw, String pwok){
        int result = 0;

        if(id.length() >= 5 && pw.length() >= 5){
            if(ck_uname(id)==false){
                if(ck_re_pw(pw, pwok)){
                    insertData(id, pw);
                    result = 4; //회원가입 성공
                }else { result = 3; } //제확인 비밀번호 불일치
            }else { result = 2; } //이미 존재하는 아이디
        } else{ result = 1; } //5글자 이상 조건 불충족

        return result;
    }



    //예매 여부
    boolean ck_ticket(String uname){
        boolean result = false;
        String sql = "SELECT m_id FROM user WHERE u_name= '"+uname+"';";
        mCursor = mdb.rawQuery(sql, null);
        mCursor.moveToFirst();
        if(!mCursor.isNull(0)) {
            result = true;
        }
        return result;
    }

    //영화 예매 정보 문자열 반환 함수
    String movie_seats(String movie_name){
        String result = "";
        String sql = "SELECT m_ticket FROM movie WHERE m_name= '"+movie_name+ "';";
        mCursor = mdb.rawQuery(sql, null);
        Log.i("leehj", String.valueOf(mCursor));
        mCursor.moveToFirst();
        result = mCursor.getString(0);
        Log.i("leehj", result);
        return result;
    }

    //영화 에매 수
    int ticket_count(String movie_name){
        int result = 0;
        String str = "";
        String sql = "SELECT m_ticket FROM movie WHERE m_name= '"+movie_name+ "';";
        mCursor = mdb.rawQuery(sql, null);
        mCursor.moveToFirst();
        str = mCursor.getString(0);
        String[] arr = str.split("#");
        result = arr.length;
        return result;
    }

    //영화 예매 정보 (사용자 이름, 영화 이미지 파일 주소, 영화 제목, 사용자가 예매한 좌석)
    String[] myTicketData(String user_name){
        String sql1 = "SELECT m_id, u_ticket FROM user WHERE u_name ='"+user_name+"';";
        mCursor = mdb.rawQuery(sql1, null);
        mCursor.moveToFirst();
        int movie_id = mCursor.getInt(0);
        String u_ticket = mCursor.getString(1);

        String sql2 = "SELECT m_img, m_name FROM movie WHERE _id ='"+movie_id+"';";
        mCursor = mdb.rawQuery(sql2, null);
        mCursor.moveToFirst();
        String movie_img = Integer.toString(mCursor.getInt(0));
        String movie_name = mCursor.getString(1);

        String[] result = {user_name, movie_img, movie_name, u_ticket};
        return result;
    }
    //예매율이 높은 상위 5개 영화의 영화 제목을 String 배열로 반환하는 함수
    String[] movie_rank(){
        //select m_name from movie ORDER BY length(m_ticket) DESC;
        String[] movie_list = new String[5];
        String sql = "SELECT m_name FROM movie ORDER BY length(m_ticket) DESC";
        mCursor = mdb.rawQuery(sql, null);
        mCursor.moveToFirst();
        for(int i =0;i<5;i++){
            String m_name = mCursor.getString(0);
            mCursor.moveToNext();
            movie_list[i] = (i+1)+" . "+m_name;
        }
        return movie_list;
    }

    //BookActivity에서 data update에 사용될 함수
    //1. 추가로 예매한 영화 좌석을 기존의 좌석에 추가
    //2. 현재 사용자가 예매한 좌석을 해당 사용자의 예매 좌석에 영화 id와 예매 좌석 추가
    void update_data(String user_name, String movie_ticket, int movie_id){
        String sql_select = "SELECT m_ticket FROM movie WHERE _id="+movie_id+";";
        mCursor = mdb.rawQuery(sql_select, null);
        mCursor.moveToFirst();
        String str = mCursor.getString(0);
        Log.i("leehj", "m_ticket select 성공: "+str);
        String sql_movie;
        if(str == null){
            sql_movie = "UPDATE movie SET m_ticket = '" + movie_ticket + "' WHERE _id = " + movie_id + ";";
        }else { sql_movie = "UPDATE movie SET m_ticket = '" +str+ movie_ticket + "' WHERE _id = " + movie_id + ";"; }

        mdb.execSQL(sql_movie);
        Log.i("leehj", "movie update 성공: "+sql_movie);
        String sql_user = "UPDATE user SET m_id ="+movie_id+", u_ticket='"+movie_ticket+"' WHERE u_name='"+user_name+"';";
        mdb.execSQL(sql_user);
        Log.i("leehj", "user update 성공: "+sql_user);
    }


}
