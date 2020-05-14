package com.change.pdfiumdemo.Linkage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by fenrir-xjc on 2020/05/14.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private ArrayList<ImageView> mList;
    private Context context;
    private ArrayList<String> picList;

    ViewPagerAdapter(Context context, ArrayList<ImageView> mList, ArrayList<String> bigList) {
        this.mList = mList;
        this.context = context;
        this.picList = bigList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = mList.get(position);
        container.removeView(imageView);
        container.addView(imageView);
        Glide.with(context).load(picList.get(position)).into(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
