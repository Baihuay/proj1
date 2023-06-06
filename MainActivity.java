package com.example.proj4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.proj4.fgm.fgm1;
import com.example.proj4.fgm.fgm2;
import com.example.proj4.fgm.fgm3;
import com.example.proj4.net.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView titleTextView;
    private TextView date_textview;
    private TextView like_textview;
    private TextView read_textview;
    private TextView contentTextView;
    private ImageView coverImageView;
    private LinearLayout linearLayout;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clickOnce();

        initView();
        initEvent();
    }
    private void initEvent() {
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            String context = OkHttpUtils.getInstance().doGet("http://124.93.196.45:10001/prod-api/press/press/list");
//                            NewsItem newsItem = parseJson(context);
                            List<NewsItem> newsItemList = parseJson(context);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (newsItemList != null && !newsItemList.isEmpty()) {
                                        RecyclerView recyclerView = findViewById(R.id.recycler_view);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                        recyclerView.setAdapter(new NewsAdapter(newsItemList));
                                    } else {
                                        titleTextView.setText("Error parsing JSON.");
                                        date_textview.setText("Error parsing JSON.");
                                        contentTextView.setText("Error parsing JSON.");
                                        like_textview.setText("Error parsing JSON.");
                                        read_textview.setText("Error parsing JSON.");
                                    }

                                }
                            });
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }.start();
            }
        });
    }

    private void initView() {
        linearLayout = findViewById(R.id.tv1);
//        button = findViewById(R.id.button_get);
        titleTextView = findViewById(R.id.titleTextView);
        date_textview = findViewById(R.id.date_textview);
        like_textview = findViewById(R.id.like_textview);
        read_textview = findViewById(R.id.read_textview);
        contentTextView = findViewById(R.id.contentTextView);
        coverImageView = findViewById(R.id.coverImageView);
    }

    private void clickOnce() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        fgm1 f1 = new fgm1();
        ft.replace(R.id.lv, f1);
        ft.commit();
    }

    public void abc(View view) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        fgm1 f1 = new fgm1();
        fgm2 f2 = new fgm2();
        fgm3 f3 = new fgm3();

        int viewId = view.getId();
        if (viewId == R.id.tv1) {
            ft.replace(R.id.lv, f1);
        } else if (viewId == R.id.tv2) {
            ft.replace(R.id.lv, f2);
        } else if (viewId == R.id.tv3) {
            ft.replace(R.id.lv, f3);
        }

        ft.commit();
    }

    private List<NewsItem> parseJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray rows = jsonObject.getJSONArray("rows");
//            JSONObject data = jsonObject.getJSONArray("rows").getJSONObject(0);

            List<NewsItem> newsItemList = new ArrayList<>();

            for (int i = 0; i < rows.length(); i++) {
                JSONObject data = rows.getJSONObject(i);

                NewsItem newsItem = new NewsItem();
                newsItem.setTitle(data.getString("title"));
                newsItem.setSubTitle(data.getString("subTitle"));
                newsItem.setContent(data.getString("content"));
                newsItem.setStatus(data.getString("status"));
                newsItem.setPublishDate(data.getString("publishDate"));
                newsItem.setTags(data.getString("tags"));
                newsItem.setCommentNum(data.getString("commentNum"));
                newsItem.setLikeNum(data.getString("likeNum"));
                newsItem.setReadNum(data.getString("readNum"));
                newsItem.setCover(data.getString("cover"));

                newsItemList.add(newsItem);
            }

            return newsItemList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}