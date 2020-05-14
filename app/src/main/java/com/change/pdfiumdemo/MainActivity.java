package com.change.pdfiumdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.change.pdfiumdemo.Linkage.ListBaseViewActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_vertical_thumbnail,btn_viewpager_thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        btn_vertical_thumbnail = findViewById(R.id.btn_vertical_thumbnail);
        btn_viewpager_thumbnail = findViewById(R.id.btn_viewpager_thumbnail);
        btn_vertical_thumbnail.setOnClickListener(this);
        btn_viewpager_thumbnail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.btn_vertical_thumbnail:
              Intent intent1 = new Intent(this,ThumbnailActivity.class);
              startActivity(intent1);
              break;
          case R.id.btn_viewpager_thumbnail:
              Intent intent2 = new Intent(this, ListBaseViewActivity.class);
              startActivity(intent2);
              break;
          default:
              break;
      }
    }
}
