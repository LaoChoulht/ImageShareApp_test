package com.guet.laochou.testapp.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.guet.laochou.testapp.R;
import com.guet.laochou.testapp.activities.ImageDetailActivity;
import com.guet.laochou.testapp.models.MainListViewHolder;
import com.guet.laochou.testapp.models.MyImage;

import java.util.ArrayList;

public class UMainListAdapter extends BaseAdapter implements View.OnClickListener {

    private ArrayList<MyImage> data;
    private Context mContext;

    public UMainListAdapter(Context mContext, ArrayList<MyImage> data) {
        super();
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    // 要搞清楚position怎么用
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        MyImage myImage = (MyImage) getItem(position);
        MainListViewHolder holder1 = new MainListViewHolder();
        MainListViewHolder holder2 = new MainListViewHolder();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.main_list_items, null);
            myImage = (MyImage) getItem(position);
            // findViews and set Tag
            initViewHolderIDs(convertView, holder1, holder2);


            convertView.setTag(holder1);
            convertView.setTag(holder2);
        } else {
            holder1 = (MainListViewHolder) convertView.getTag();
            holder2 = (MainListViewHolder) convertView.getTag();
        }
        // set resource
        holder1.iv_thumbnail.setImageBitmap(myImage.getOriginal());
        holder1.tv_imageID.setText(myImage.getImageID());
        holder1.tv_likeNum.setText(myImage.getLikes());
        holder2.iv_thumbnail.setImageBitmap(myImage.getOriginal());
        holder2.tv_imageID.setText(myImage.getImageID());
        holder2.tv_likeNum.setText(myImage.getLikes());

        return convertView;
    }

    public void initViewHolderIDs(View convertView, MainListViewHolder holder1, MainListViewHolder holder2) {
        holder1.iv_thumbnail = convertView.findViewById(R.id.main_list_card1_iv_thumbnail);
        holder1.tv_imageID = convertView.findViewById(R.id.main_list_card1_tv_imageID);
        holder1.tv_likeNum = convertView.findViewById(R.id.main_list_card1_tv_likeNum);
        holder1.iv_likeBtn = convertView.findViewById(R.id.main_list_card1_iv_likeBtn);
        holder1.iv_downloadBtn = convertView.findViewById(R.id.main_list_card1_iv_download);
        holder2.iv_thumbnail = convertView.findViewById(R.id.main_list_card2_iv_thumbnail);
        holder2.tv_imageID = convertView.findViewById(R.id.main_list_card2_tv_imageID);
        holder2.tv_likeNum = convertView.findViewById(R.id.main_list_card2_tv_likeNum);
        holder2.iv_likeBtn = convertView.findViewById(R.id.main_list_card2_iv_likeBtn);
        holder2.iv_downloadBtn = convertView.findViewById(R.id.main_list_card2_iv_download);

        holder1.iv_thumbnail.setOnClickListener(this);
        holder1.iv_likeBtn.setOnClickListener(this);
        holder1.iv_downloadBtn.setOnClickListener(this);
        holder2.iv_thumbnail.setOnClickListener(this);
        holder2.iv_likeBtn.setOnClickListener(this);
        holder2.iv_downloadBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.main_list_card1_iv_thumbnail:
            case R.id.main_list_card2_iv_thumbnail:
                intent = new Intent(mContext, ImageDetailActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.main_list_card1_iv_likeBtn:
            case R.id.main_list_card2_iv_likeBtn:
                Log.d("TESTTAG", "onClick: like");
                break;
            case R.id.main_list_card1_iv_download:
            case R.id.main_list_card2_iv_download:
                Log.d("TESTTAG", "onClick: download");
                break;
        }
    }
}
