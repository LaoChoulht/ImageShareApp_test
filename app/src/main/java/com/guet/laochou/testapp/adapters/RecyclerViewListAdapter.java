package com.guet.laochou.testapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.guet.laochou.testapp.activities.R;
import com.guet.laochou.testapp.ImageDetailActivity;
import com.guet.laochou.testapp.models.MyImage;

import java.util.ArrayList;

public class RecyclerViewListAdapter extends RecyclerView.Adapter<RecyclerViewListAdapter.LikedListViewHolder> {

    private Context mContext;
    private int mResourceID;
    private ArrayList<MyImage> mData;
    private LayoutInflater mInflater;
    private boolean isScrolling;

    public class LikedListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView iv_thumbnail;
        public TextView tv_imageID;
        public ImageView iv_likeBtn;
        public TextView tv_likeNum;
        public ImageView iv_downloadBtn;
        private boolean likedState = false;
        private View mView;
        private ArrayList<MyImage> mData;

        public LikedListViewHolder(View itemView, ArrayList<MyImage> data) {
            super(itemView);
            this.mView = itemView;
            this.mData = data;
            setOnClickListeners();
        }

        private void setOnClickListeners() {
            this.iv_thumbnail = mView.findViewById(R.id.main_list_card1_iv_thumbnail);
            this.tv_imageID = mView.findViewById(R.id.main_list_card1_tv_imageID);
            this.tv_likeNum = mView.findViewById(R.id.main_list_card1_tv_likeNum);
            this.iv_likeBtn = mView.findViewById(R.id.main_list_card1_iv_likeBtn);
            this.iv_downloadBtn = mView.findViewById(R.id.main_list_card1_iv_download);

            this.iv_thumbnail.setOnClickListener(this);
            this.iv_likeBtn.setOnClickListener(this);
            this.iv_downloadBtn.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Intent intent;
            MyImage image;
            switch (view.getId()) {
                case R.id.main_list_card1_iv_thumbnail:
                    intent = new Intent(mContext, ImageDetailActivity.class);
                    view.getContext().startActivity(intent);
                    break;
                case R.id.main_list_card1_iv_likeBtn:
                    Log.d("TESTTAG", "LikedListViewHolder onclick getTag: " + itemView.getTag());
                    image = mData.get((int)itemView.getTag());
                    image.setLiked(!image.isLiked());
                    this.likedState = image.isLiked();
                    this.setLikeBtnState(this.likedState);
                    break;
                case R.id.main_list_card1_iv_download:
                    Log.d("TESTTAG", "LikedListViewHolder getTag: " + itemView.getTag());
                    break;
            }
        }

        private void setLikeBtnState(boolean state) {
            if (state) {
                this.iv_likeBtn.setImageResource(R.drawable.ic_baseline_thumb_up_24);
            } else {
                this.iv_likeBtn.setImageResource(R.drawable.ic_baseline_thumb_up_gray_24);
            }
        }
    }

    public RecyclerViewListAdapter(Context context, int listItemRID, ArrayList<MyImage> data) {
        this.mContext = context;
        this.mResourceID = listItemRID;
        this.mData = data;
        this.mInflater = LayoutInflater.from(context);
    }

    public void setScrolling(boolean scrolling) {
        isScrolling = scrolling;
    }

    @Override
    public LikedListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(mResourceID, parent, false);
        LikedListViewHolder holder = new LikedListViewHolder(view, mData);
        Log.d("TESTTAG", "create gettag: "+holder.itemView.getTag());
        return holder;
    }

    @Override
    public void onBindViewHolder(LikedListViewHolder holder, int position) {
        holder.iv_thumbnail.setImageBitmap(mData.get(position).getOriginal());
        holder.tv_imageID.setText(mData.get(position).getImageID());
        holder.tv_likeNum.setText(mData.get(position).getLikes());
        Log.d("TESTTAG", "position: "+position);
        holder.setLikeBtnState(mData.get(position).isLiked());
//        Log.d("TESTTAG", "onBindViewHolder: setTag" + position);
        holder.mView.setTag(position);

//        if (!mData.isEmpty() && !isScrolling) {
//            // 这里可以用Glide等网络图片加载库
//
//        } else {
//
//        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
