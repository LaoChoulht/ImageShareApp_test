package com.guet.laochou.testapp.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.guet.laochou.testapp.R;
import com.guet.laochou.testapp.models.MainListViewHolder;
import com.guet.laochou.testapp.models.MyImage;

import java.util.List;

public class MainListAdapter extends ArrayAdapter<MyImage> {

    private List<MyImage> mImageData;
    private int resourceId;
    private Context mContext;

    public MainListAdapter(@NonNull Context context, int resource, @NonNull List<MyImage> objects) {
        super(context, resource, objects);
        this.mImageData = objects;
        this.resourceId = resource;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        MainListViewHolder vh = new MainListViewHolder();
        MyImage myImage = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.main_list_items, null);
            vh.iv_thumbnail = convertView.findViewById(R.id.main_list_card1_iv_thumbnail);
            vh.tv_imageID = convertView.findViewById(R.id.main_list_card1_tv_imageID);
            vh.tv_likeNum = convertView.findViewById(R.id.main_list_card1_tv_likeNum);
            convertView.setTag(vh);
            Log.d("TESTTAG", "getView: setTag");
        } else {

            vh = (MainListViewHolder) convertView.getTag();
        }
        if (myImage.getOriginal() != null) {
            vh.iv_thumbnail.setImageBitmap(myImage.getOriginal());
            vh.tv_imageID.setText(myImage.getImageID());
            vh.tv_likeNum.setText(myImage.getLikes());
            Log.d("TESTTAG", "getOriginal: not null");
        }

        return convertView;
    }
}
