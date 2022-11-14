package com.example.metflix;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();
    ArrayAdapter<String> aa;
    DB helper;
    SQLiteDatabase mdb;
    Cursor cursor;

    Intent intent;
    static int Handerturnchange = 1;
    SliderAdapter sa;
    static String user_id;
    ListView lv;
    String arr[];

    ImageButton btn_Login, btn_Ticket, btn_Mypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageslider);

        viewPager2 = findViewById(R.id.viewPageImageSlider);


        // Here, i'm preparing list of images from drawable,
        // You can get it from API as well.
        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.picture));
        sliderItems.add(new SliderItem(R.drawable.picture2));
        sliderItems.add(new SliderItem(R.drawable.picture3));
        sliderItems.add(new SliderItem(R.drawable.picture4));
        sliderItems.add(new SliderItem(R.drawable.picture5));
        sliderItems.add(new SliderItem(R.drawable.picture6));
        sliderItems.add(new SliderItem(R.drawable.picture7));
        sliderItems.add(new SliderItem(R.drawable.picture8));
        sliderItems.add(new SliderItem(R.drawable.picture9));
        sliderItems.add(new SliderItem(R.drawable.picture10));

        sa = new SliderAdapter(sliderItems, viewPager2);
        viewPager2.setAdapter(sa);

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);


        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {

                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }

        });

        viewPager2.setPageTransformer(compositePageTransformer);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);

            }
        });

        btn_Login = findViewById(R.id.btn_login);
        btn_Ticket = findViewById(R.id.btn_ticket);
        btn_Mypage = findViewById(R.id.btn_mypage);

        lv = findViewById(R.id.lv_mv);

        ImageButton Left_btn = findViewById(R.id.left_btn);
        ImageButton Right_btn = findViewById(R.id.right_btn);

        Left_btn.setOnClickListener(movieslider);
        Right_btn.setOnClickListener(movieslider);
        btn_Login.setOnClickListener(menuclick);
        btn_Ticket.setOnClickListener(menuclick);
        btn_Mypage.setOnClickListener(menuclick);

        helper = new DB(this, "mydb.db", null, 1);
        mdb = helper.getWritableDatabase();
        cursor = mdb.rawQuery("SELECT * FROM movie", null);

        DataBaseController dbc = new DataBaseController(cursor, mdb);
        arr = dbc.movie_rank();
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
        lv.setAdapter(aa);
        String str = arr[1].toString();
        btn_Login.setVisibility(btn_Login.VISIBLE);
        btn_Mypage.setVisibility(btn_Mypage.INVISIBLE);
        btn_Ticket.setVisibility(btn_Ticket.INVISIBLE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            user_id = data.getStringExtra("userid");
            TextView tv = findViewById(R.id.tv_logininfo);
            tv.setText("Hello! " + user_id);

            btn_Login.setVisibility(btn_Login.GONE);
            btn_Mypage.setVisibility(btn_Mypage.VISIBLE);
            btn_Ticket.setVisibility(btn_Mypage.VISIBLE);
            btn_Mypage.setOnClickListener(menuclick);
            btn_Mypage.setVisibility(btn_Mypage.VISIBLE);

            //rank ListView 갱신
            DataBaseController dbc = new DataBaseController(cursor, mdb);
            arr = dbc.movie_rank();
            aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
            lv.setAdapter(aa);
        }
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            int[] sa1 = sa.getCheck();
            if (Handerturnchange == 1 && sa1[viewPager2.getCurrentItem()] == 1) {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                if (viewPager2.getCurrentItem() == 9) {
                    Handerturnchange = 0;
                }

            } else if (Handerturnchange == 0 && sa1[viewPager2.getCurrentItem()] == 1) {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                if (viewPager2.getCurrentItem() == 0) {
                    Handerturnchange = 1;
                }
            } else {
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
            }
        }
    };


    View.OnClickListener movieslider = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (view.getId() == R.id.right_btn) {

                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);

                if (viewPager2.getCurrentItem() == 9) {
                    Handerturnchange = 0;
                }
            } else {

                viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                if (viewPager2.getCurrentItem() == 0) {
                    Handerturnchange = 1;
                }
            }
        }
    };


    View.OnClickListener menuclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {

                case R.id.btn_login:
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, 1);
                    break;

                case R.id.btn_ticket:
                    Intent intent = new Intent(MainActivity.this, BookActivity.class);
                    intent.putExtra("userid", user_id);
                    startActivityForResult(intent, 1);
//                    startActivity(intent);
                    break;
                case R.id.btn_mypage:

                    intent = new Intent(MainActivity.this, MyActivity.class);
                    intent.putExtra("userid", user_id);
                    startActivity(intent);
                    break;
            }
        }
    };
}