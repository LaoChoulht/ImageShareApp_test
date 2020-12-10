package com.guet.laochou.testapp.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.os.Message;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.guet.laochou.testapp.activities.R;
import com.guet.laochou.testapp.adapters.MainRecyclerViewAdapter;
import com.guet.laochou.testapp.models.LoginResult;
import com.guet.laochou.testapp.models.MainListResult;
import com.guet.laochou.testapp.models.MyImage;
import com.guet.laochou.testapp.models.Picture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private ListView listView;
    private MyImage image,image2;
    private ArrayList<MyImage> itemList;
    private ArrayList<MyImage> itemList2;
    private MainRecyclerViewAdapter mAdapter;

    private BitmapFactory bitmapFactory;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private StaggeredGridLayoutManager layoutManager;

    private OkHttpClient client;
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private String remoteGetMainListURL;
    private Runnable runnable;
    private String spFileName, userTokenKey;
    private SharedPreferences spFile;
    private Gson gson;
    private String sss;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_likes, container, false);
        recyclerView = root.findViewById(R.id.liked_RecyclerView);
        swipeRefreshLayout = root.findViewById(R.id.main_swipeRefreshLayout);
        recyclerView.setHasFixedSize(true);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        itemList = new ArrayList<>();
//        returnList = new ArrayList<Picture>();
        gson = new Gson();
        sss = new String();

        remoteGetMainListURL = getActivity().getResources().getString(R.string.remoteServerID) +
                getActivity().getResources().getString(R.string.getMainListAPI);
        spFileName = getResources().getString(R.string.login_sp_file_name);
        userTokenKey = getResources().getString(R.string.login_userToken);
        spFile = getActivity().getSharedPreferences(spFileName, Context.MODE_PRIVATE);

        runnable = new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                Bundle data = new Bundle();
                try {
                    sss = remoteGetMainList(4);
                    Log.d("TESTTAG", "main run: " + sss);
                    MainListResult result = gson.fromJson(sss, MainListResult.class);
//                    List<Picture> pictures = gson.fromJson(result.getData(),)
////                    List<Picture> pictures = new ArrayList<Picture>();
////                    result.getPictures();
//                    Log.d("TESTTAG", result.getData() == null?"main run list null":"main run list");
//                    Log.d("TESTTAG", result.getData().get(0).getPicture());
                    for(Picture p : result.getData()){
                        MyImage i = new MyImage();
                        i.setImageID(p.getPictureName());
//                        byte[] bitmapArr = Base64.decode(p.getPicture(),);
//                        i.setOriginal(BitmapFactory.decodeByteArray(bitmapArr,0,bitmapArr.length));
                        addOneItem(i);
                    }
                    mAdapter.notifyItemRangeChanged(0,itemList.size());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                for (int i = 0; i < 5; i++) {
                    addOneItem(image);
                }
//                new Thread(runnable).start();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        mAdapter = new MainRecyclerViewAdapter(getContext(), R.layout.liked_list_item, itemList);
        recyclerView.setAdapter(mAdapter);
        DefaultItemAnimator animator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(animator);
        return root;
    }

    public boolean initData() {
        image2 = new MyImage();
        image2.setOriginal(bitmapFactory.decodeResource(getResources(), R.drawable.wallpaper_60223263));
        image2.setImageID("testID");
        image2.setLikes("testLikes");
        for (int i = 0; i < 5; i++) {
            itemList.add(image2);
        }
        return true;
    }

    public void addOneItem(MyImage data) {
        itemList.add(0, data);
        mAdapter.notifyItemInserted(0);
        recyclerView.scrollToPosition(0);
    }

    public void addItems(ArrayList<MyImage> data) {
        itemList.addAll(0, data);
        mAdapter.notifyItemRangeChanged(0, itemList.size());
        recyclerView.scrollToPosition(0);
    }

    public String remoteGetMainList(int num) throws IOException {
        String result = new String();
        result = post(remoteGetMainListURL + num);
        return result;
    }

    String post(String url) throws IOException {
        client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}