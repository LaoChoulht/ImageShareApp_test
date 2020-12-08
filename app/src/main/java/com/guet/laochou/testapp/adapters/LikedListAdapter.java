package com.guet.laochou.testapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.guet.laochou.testapp.activities.R;
import com.guet.laochou.testapp.ImageDetailActivity;
import com.guet.laochou.testapp.models.MyImage;

import java.util.ArrayList;

public class LikedListAdapter extends RecyclerView.Adapter<LikedListAdapter.LikedListViewHolder> implements View.OnClickListener {

    private Context mContext;
    private int mResourceID;
    private ArrayList<MyImage> mData;
    private LayoutInflater mInflater;
//    private LikedListViewHolder viewHolder;

    public class LikedListViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_thumbnail;
        public TextView tv_imageID;
        public ImageView iv_likeBtn;
        public TextView tv_likeNum;
        public ImageView iv_downloadBtn;
        private boolean liked = false;

        public LikedListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public LikedListAdapter(Context context, ArrayList<MyImage> data) {
        this.mContext = context;
//        this.mResourceID = resourceID;
        this.mData = data;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public LikedListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.liked_list_item,null);
        LikedListViewHolder holder = new LikedListViewHolder(view);
        setOnClickListeners(view,holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LikedListViewHolder holder, int position) {
        holder.iv_thumbnail.setImageBitmap(mData.get(position).getOriginal());
        holder.tv_imageID.setText(mData.get(position).getImageID());
        holder.tv_likeNum.setText(mData.get(position).getLikes());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void setOnClickListeners(View view,LikedListViewHolder holder) {
        holder.iv_thumbnail = view.findViewById(R.id.main_list_card1_iv_thumbnail);
        holder.tv_imageID = view.findViewById(R.id.main_list_card1_tv_imageID);
        holder.tv_likeNum = view.findViewById(R.id.main_list_card1_tv_likeNum);
        holder.iv_likeBtn = view.findViewById(R.id.main_list_card1_iv_likeBtn);
        holder.iv_downloadBtn = view.findViewById(R.id.main_list_card1_iv_download);

        holder.iv_thumbnail.setOnClickListener(this);
        holder.iv_likeBtn.setOnClickListener(this);
        holder.iv_downloadBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.main_list_card1_iv_thumbnail:
                intent = new Intent(mContext, ImageDetailActivity.class);
                view.getContext().startActivity(intent);
                break;
            case R.id.main_list_card1_iv_likeBtn:
                Log.d("TESTTAG", "onClick: like");
                break;
            case R.id.main_list_card1_iv_download:
                Log.d("TESTTAG", "onClick: download");
                break;
        }
    }
}
