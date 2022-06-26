package com.khiempt.trinhdocpdf.ui.data;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.khiempt.trinhdocpdf.R;

import java.util.List;


public class PDFAdapter extends RecyclerView.Adapter<PDFAdapter.ViewHolder> {

    Context context;
    List<PDFMode> mList;
    int mType;
    int mPosition;

    public PDFAdapter(Context context, List<PDFMode> mList, int type, int mPosition) {
        this.context = context;
        this.mList = mList;
        this.mType = type;
        this.mPosition = mPosition;
        Log.d("WWWWWW",new Gson().toJson(mList));
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PDFAdapter.ViewHolder holder, int position) {

        holder.textView.setText(mList.get(position).getmTenFile());
        holder.imageView.setImageResource(mList.get(position).getmLogo());
        holder.img_thongtin.setImageResource(R.drawable.ic_ellipsis_vertical);
        holder.img_dautrang.setImageResource(R.drawable.ic_outline_turned_in_not_24);
        String suffix = "";
        int size = mList.get(position).getSize();
        if (size >= 1024) {
            suffix = "MB";
            size /= 1024;

        } else {
            suffix = " KB";
        }
        holder.size.setText(size + " " + suffix);
        Log.d("ASDASDD", mType + " " + mList.get(position).getDuoiFile());
        if (mPosition == 0) {
            holder.cardview.setVisibility(View.VISIBLE);
            if (size < 1)
                holder.cardview.setVisibility(View.GONE);
            else holder.cardview.setVisibility(View.VISIBLE);
        } else if (mList.get(position).getDuoiFile().equals("pdf") && mPosition == 1) {
            holder.cardview.setVisibility(View.VISIBLE);
            if (size < 1)
                holder.cardview.setVisibility(View.GONE);
            else holder.cardview.setVisibility(View.VISIBLE);
        } else if ( mList.get(position).getDuoiFile().equals("docx") && mPosition == 2) {
            holder.cardview.setVisibility(View.VISIBLE);
            if (size < 1)
                holder.cardview.setVisibility(View.GONE);
            else holder.cardview.setVisibility(View.VISIBLE);
        } else if ( mList.get(position).getDuoiFile().equals("xlsx") && mPosition == 3) {
            holder.cardview.setVisibility(View.VISIBLE);
            if (size < 1)
                holder.cardview.setVisibility(View.GONE);
            else holder.cardview.setVisibility(View.VISIBLE);
        } else if ( mList.get(position).getDuoiFile().equals("pptx") && mPosition == 4) {
            holder.cardview.setVisibility(View.VISIBLE);
            if (size < 1)
                holder.cardview.setVisibility(View.GONE);
            else holder.cardview.setVisibility(View.VISIBLE);
        } else holder.cardview.setVisibility(View.GONE);
        if (mList.get(position).isDauTrang()) {
            holder.img_dautrang.setImageResource(R.drawable.ic_outline_turned_in_24_y);
        } else holder.img_dautrang.setImageResource(R.drawable.ic_outline_turned_in_not_24);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        TextView size;
        CardView cardview;
        public ImageView imageView, img_dautrang, img_thongtin;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.file_name_text_view);
            size = itemView.findViewById(R.id.size);
            imageView = itemView.findViewById(R.id.icon_view);
            img_dautrang = itemView.findViewById(R.id.img_dautrang);
            img_thongtin = itemView.findViewById(R.id.img_thongtin);
            cardview = itemView.findViewById(R.id.cardview);
        }
    }
}
