package com.change.pdfiumdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_vertical_thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        btn_vertical_thumbnail = findViewById(R.id.btn_vertical_thumbnail);
        btn_vertical_thumbnail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.btn_vertical_thumbnail:
              Intent intent = new Intent(this,ThumbnailActivity.class);
              startActivity(intent);
              break;
          default:
              break;
      }
    }
}
