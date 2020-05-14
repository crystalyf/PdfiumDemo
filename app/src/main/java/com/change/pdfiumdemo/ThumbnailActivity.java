package com.change.pdfiumdemo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.change.pdfiumdemo.preview.FileUtils;
import com.change.pdfiumdemo.preview.VerticalGridAdapter;
import com.change.pdfiumdemo.preview.PreviewUtils;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;

import java.io.File;

public class ThumbnailActivity extends AppCompatActivity implements VerticalGridAdapter.GridEvent {

    RecyclerView rv_trumbnail;
    Button btn_back;
    PdfiumCore pdfiumCore;
    PdfDocument pdfDocument;
    String assetsFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumbnail);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        btn_back = findViewById(R.id.btn_back);
        rv_trumbnail = findViewById(R.id.rv_trumbnail);
        setEvent();
        loadData();
    }

    /**
     * 设置事件
     */
    private void setEvent() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回收内存
                recycleMemory();
                finish();
            }
        });
    }

    /**
     * 加载数据
     */
    private void loadData() {
        //加载pdf文件
        loadPdfFile();
        //获得pdf总页数
        int totalCount = pdfiumCore.getPageCount(pdfDocument);
        //绑定列表数据
        VerticalGridAdapter adapter = new VerticalGridAdapter(this, pdfiumCore, pdfDocument, assetsFileName, totalCount);
        adapter.setGridEvent(this);
        rv_trumbnail.setLayoutManager(new GridLayoutManager(this, 3));
        rv_trumbnail.setAdapter(adapter);
    }

    /**
     * 加载pdf文件
     */
    private void loadPdfFile() {
        Intent intent = getIntent();
        if (intent != null) {
            assetsFileName = "swift.pdf";
            loadAssetsPdfFile(assetsFileName);

        }
    }

    /**
     * 加载assets中的pdf文件
     */
    void loadAssetsPdfFile(String assetsFileName) {
        try {
            File f = FileUtils.fileFromAsset(this, assetsFileName);
            ParcelFileDescriptor pfd = ParcelFileDescriptor.open(f, ParcelFileDescriptor.MODE_READ_ONLY);
            pdfiumCore = new PdfiumCore(this);
            pdfDocument = pdfiumCore.newDocument(pfd);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 基于uri加载pdf文件
     */
    void loadUriPdfFile(Uri uri) {
        try {
            ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(uri, "r");
            pdfiumCore = new PdfiumCore(this);
            pdfDocument = pdfiumCore.newDocument(pfd);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 点击缩略图，带回Pdf页码到前一个页面
     *
     * @param position 页码
     */
    @Override
    public void onGridItemClick(int position) {
        //回收内存
        recycleMemory();
        //返回前一个页码
        Intent intent = new Intent();
        intent.putExtra("pageNum", position);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    /**
     * 回收内存
     */
    private void recycleMemory() {
        //关闭pdf对象
        if (pdfiumCore != null && pdfDocument != null) {
            pdfiumCore.closeDocument(pdfDocument);
            pdfiumCore = null;
        }
        //清空图片缓存，释放内存空间
        PreviewUtils.getInstance().getImageCache().clearCache();
    }
}
