package com.guet.laochou.testapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.guet.laochou.testapp.activities.R;
import com.guet.laochou.testapp.models.Like;
import com.guet.laochou.testapp.models.MyImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.LikedListViewHolder> {

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

        private OkHttpClient client;
        private final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        private String remoteUploadURL;
        private String pictureID;
        private Like like;
        private Runnable runnable;
        private String spFileName, userTokenKey;
        private SharedPreferences spFile;
        private Gson gson;

        public LikedListViewHolder(View itemView, ArrayList<MyImage> data) {
            super(itemView);
            this.mView = itemView;
            this.mData = data;
            setOnClickListeners();
            like = new Like();
            gson = new Gson();

            remoteUploadURL = mContext.getResources().getString(R.string.remoteServerID) +
                    mContext.getResources().getString(R.string.likeAPI);
            spFileName = mContext.getResources().getString(R.string.login_userToken_sp_file_name);
            userTokenKey = mContext.getResources().getString(R.string.login_userToken);
            spFile = mContext.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
            runnable = new Runnable() {
                @Override
                public void run() {
                    Message msg = new Message();
                    Bundle data = new Bundle();
                    String s = new String();
                    try {
                        if (mView != null)
//                        Log.d("TESTTAG", "like ID: " + mData.get((int)mView.getTag()).getImageID());
                        {
                            boolean liked = mData.get((int) mView.getTag()).isLiked();
                            if (liked == true) {
                                s = remoteLike(Integer.parseInt(mData.get((int) mView.getTag()).getImageID()));
                            } else if (liked == false) {
                                s = remoteDislike(Integer.parseInt(mData.get((int) mView.getTag()).getImageID()));
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("TESTTAG", "likeBtn result: " + s);
                }
            };
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
//                    intent = new Intent(mContext, .class);
//                    view.getContext().startActivity(intent);
                    break;
                case R.id.main_list_card1_iv_likeBtn:
//                    Log.d("TESTTAG", "LikedListViewHolder onclick getTag: " + itemView.getTag());
                    image = mData.get((int) itemView.getTag());
                    image.setLiked(!image.isLiked());
                    image.setLikes(image.getLikes()+1);
                    this.likedState = image.isLiked();
                    this.setLikeBtnState(this.likedState);
                    new Thread(runnable).start();
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

        private String remoteLike(int pictureID) throws IOException {
            like.setPictureID(pictureID);
            like.setToken(spFile.getString(userTokenKey, "null"));
            Log.d("TESTTAG", "like: ");
            String likeResultString = post(remoteUploadURL, gson.toJson(like));
            return likeResultString;
        }

        private String remoteDislike(int pictureID) throws IOException {
            like.setPictureID(pictureID);
            like.setToken(spFile.getString(userTokenKey, "null"));
            remoteUploadURL = mContext.getResources().getString(R.string.remoteServerID) +
                    mContext.getResources().getString(R.string.dislikeAPI);
            Log.d("TESTTAG", "dislike: "+remoteUploadURL);
            String likeResultString = post(remoteUploadURL, gson.toJson(like));
            return likeResultString;
        }

        String post(String url, String json) throws IOException {
            client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();
            Log.d("TESTTAG", "like post: " + json);
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
        }
    }

    public MainRecyclerViewAdapter(Context context, int listItemRID, ArrayList<MyImage> data) {
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
        Log.d("TESTTAG", "create gettag: " + holder.itemView.getTag());
        return holder;
    }

    @Override
    public void onBindViewHolder(LikedListViewHolder holder, int position) {
        holder.iv_thumbnail.setImageBitmap(mData.get(position).getOriginal());
        holder.tv_imageID.setText(mData.get(position).getImageID());
        holder.tv_likeNum.setText(mData.get(position).getLikes());
//            Log.d("TESTTAG", "position: "+position);
        holder.setLikeBtnState(mData.get(position).isLiked());
        holder.mView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

}
