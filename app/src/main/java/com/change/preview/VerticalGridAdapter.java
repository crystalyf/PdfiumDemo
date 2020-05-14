package com.change.preview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.change.pdfiumdemo.R;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;

/**
 * grid列表适配器
 * 作者：齐行超
 * 日期：2019.08.08
 */
public class VerticalGridAdapter extends RecyclerView.Adapter<VerticalGridAdapter.GridViewHolder> {

    Context context;
    PdfiumCore pdfiumCore;
    PdfDocument pdfDocument;
    String pdfName;
    int totalPageNum;


    public VerticalGridAdapter(Context context, PdfiumCore pdfiumCore, PdfDocument pdfDocument, String pdfName, int totalPageNum) {
        this.context = context;
        this.pdfiumCore = pdfiumCore;
        this.pdfDocument = pdfDocument;
        this.pdfName = pdfName;
        this.totalPageNum = totalPageNum;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item_vertical, null);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {
        //设置PDF图片
        final int pageNum = position;
        PreviewUtils.getInstance().loadBitmapFromPdf(context, holder.iv_page, pdfiumCore, pdfDocument, pdfName, pageNum);
        //设置PDF页码
        holder.tv_pagenum.setText(String.valueOf(position));
        //设置Grid事件
        holder.iv_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delegate != null) {
                    delegate.onGridItemClick(pageNum);
                }
            }
        });
        return;
    }

    @Override
    public void onViewDetachedFromWindow(GridViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        try {
            //item不可见时，取消任务
            if (holder.iv_page != null) {
                PreviewUtils.getInstance().cancelLoadBitmapFromPdf(holder.iv_page.getTag().toString());
            }
            //item不可见时，释放bitmap  (注意：本Demo使用了LruCache缓存来管理图片，所以不用写)
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return totalPageNum;
    }

    static class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_page;
        TextView tv_pagenum;

        GridViewHolder(View itemView) {
            super(itemView);
            iv_page = itemView.findViewById(R.id.iv_page);
            tv_pagenum = itemView.findViewById(R.id.tv_pagenum);
        }
    }

    /**
     * 接口：Grid事件
     */
    public interface GridEvent {
        /**
         * 当选择了某Grid项
         *
         * @param position tree节点数据
         */
        void onGridItemClick(int position);
    }

    /**
     * 设置Grid事件
     *
     * @param event Grid事件对象
     */
    public void setGridEvent(GridEvent event) {
        this.delegate = event;
    }

    //Grid事件委托
    private GridEvent delegate;
}
