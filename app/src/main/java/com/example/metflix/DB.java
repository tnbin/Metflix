package com.example.metflix;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {

    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE movie (_id INTEGER PRIMARY KEY AUTOINCREMENT, m_img INTEGER, m_name TEXT, m_ticket TEXT);");
        sqLiteDatabase.execSQL("INSERT INTO movie VALUES (null, " +R.drawable.picture +", '지금만나러갑니다', '1#3#');");
        sqLiteDatabase.execSQL("INSERT INTO movie VALUES (null, " +R.drawable.picture2 +", '비상선언', '2#');");
        sqLiteDatabase.execSQL("INSERT INTO movie VALUES (null, " +R.drawable.picture3 +", '한산', '3#2#5#');");
        sqLiteDatabase.execSQL("INSERT INTO movie VALUES (null, " +R.drawable.picture4 +", '마녀2', '7#');");
        sqLiteDatabase.execSQL("INSERT INTO movie VALUES (null, " +R.drawable.picture5 +", '인질', '3#');");
        sqLiteDatabase.execSQL("INSERT INTO movie VALUES (null, " +R.drawable.picture6 +", '낙원의밤', null);");
        sqLiteDatabase.execSQL("INSERT INTO movie VALUES (null, " +R.drawable.picture7 +", '범죄도시2', null);");
        sqLiteDatabase.execSQL("INSERT INTO movie VALUES (null, " +R.drawable.picture8 +", '헤어질결심', null);");
        sqLiteDatabase.execSQL("INSERT INTO movie VALUES (null, " +R.drawable.picture9 +", '탑건', null);");
        sqLiteDatabase.execSQL("INSERT INTO movie VALUES (null, " +R.drawable.picture10 +", '마이네임', null);");

        sqLiteDatabase.execSQL("CREATE TABLE user (_id INTEGER PRIMARY KEY AUTOINCREMENT, u_name TEXT, u_pw TEXT, m_id INTEGER, u_ticket TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS movie");
        onCreate(sqLiteDatabase);
    }
}
