package com.guet.laochou.testapp.ui.likes;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.guet.laochou.testapp.activities.R;
import com.guet.laochou.testapp.models.LikeListResult;
import com.guet.laochou.testapp.models.MyImage;
import com.guet.laochou.testapp.adapters.MainRecyclerViewAdapter;
import com.guet.laochou.testapp.models.Picture;
import com.guet.laochou.testapp.models.UserToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LikesFragment extends Fragment {

    private ArrayList<MyImage> itemList, resultList;
    private MainRecyclerViewAdapter mAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private OkHttpClient client;
    private final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private String remoteUploadURL;
    private String pictureID;
    private UserToken userToken;
    private String remoteLikelistResult;
    private MyImage image;
    private Runnable runnable;
    private Handler handler;
    private String spFileName, userTokenKey;
    private SharedPreferences spFile;
    private Gson gson;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_likes, container, false);
        recyclerView = root.findViewById(R.id.liked_RecyclerView);
        swipeRefreshLayout = root.findViewById(R.id.main_swipeRefreshLayout);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        Log.d("TESTTAG", "onCreateView init: ");
        initData();

        gson = new Gson();
        userToken = new UserToken();
        itemList = new ArrayList<>();
        resultList = new ArrayList<>();
        remoteUploadURL = getResources().getString(R.string.remoteServerID) +
                getResources().getString(R.string.likeListAPI);
        spFileName = getResources().getString(R.string.login_userToken_sp_file_name);
        userTokenKey = getResources().getString(R.string.login_userToken);
        spFile = getActivity().getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        userToken.setToken(spFile.getString(userTokenKey, "null"));


        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();

                remoteLikelistResult = data.getString("result");
                LikeListResult result = gson.fromJson(remoteLikelistResult, LikeListResult.class);
                if (result.getStatus() == 1) {
                    resultList.removeAll(resultList);
                    for (Picture p : result.getData()) {
                        image = new MyImage();
                        Log.d("TESTTAG", "handleMessage: " + result.getData().size());
                        image.setOriginal(stringToBitmap(p.getPicture()));
                        image.setImageID(String.valueOf(p.getPictureID()));
                        image.setLikes(String.valueOf(p.getLikes()));
                        image.setLiked(true);
                        resultList.add(0, image);
                    }
                    Log.d("TESTTAG", "handleMessage item: " + itemList.size());
                    addItems(resultList);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        };
        runnable = new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                Bundle data = new Bundle();
                String remoteGetLikedList = null;
                try {
                    remoteGetLikedList = remoteGetLikedList();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (remoteGetLikedList != null)
                    data.putSerializable("result", remoteGetLikedList);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(runnable).start();
            }
        });

        mAdapter = new MainRecyclerViewAdapter(getContext(), R.layout.liked_list_item, itemList);
        recyclerView.setAdapter(mAdapter);

        return root;
    }

    public void initData() {
        new Thread(runnable).start();
    }

    public void addItems(ArrayList<MyImage> data) {
        mAdapter.notifyItemRangeRemoved(0,itemList.size());
        itemList.removeAll(itemList);
        itemList.addAll(0, data);
        mAdapter.notifyItemRangeChanged(0, itemList.size());
        recyclerView.scrollToPosition(0);
    }

    public void deleteAllItem(ArrayList<MyImage> data) {
        data.removeAll(data);
        mAdapter.notifyItemRangeChanged(0, data.size());
    }

    public String remoteGetLikedList() throws IOException {
        String likeResultString = post(remoteUploadURL, gson.toJson(userToken));
        return likeResultString;
    }

    String post(String url, String json) throws IOException {
        client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        Log.d("TESTTAG", "likelist post: " + json);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public Bitmap stringToBitmap(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}