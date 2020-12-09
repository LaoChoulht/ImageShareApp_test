package com.guet.laochou.testapp.ui.home;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.guet.laochou.testapp.activities.R;
import com.guet.laochou.testapp.adapters.RecyclerViewListAdapter;
import com.guet.laochou.testapp.models.MyImage;

import java.util.ArrayList;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

public class HomeFragment extends Fragment {

    private ListView listView;
    private MyImage image, image2;
    private ArrayList<MyImage> itemList;
    private ArrayList<MyImage> itemList2;
    private RecyclerViewListAdapter mAdapter;

    private BitmapFactory bitmapFactory;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private StaggeredGridLayoutManager layoutManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_likes, container, false);
        recyclerView = root.findViewById(R.id.liked_RecyclerView);
        swipeRefreshLayout = root.findViewById(R.id.main_swipeRefreshLayout);
        recyclerView.setHasFixedSize(true);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        itemList = new ArrayList<>();
        itemList2 = new ArrayList<>();
        image = new MyImage();
        image.setImageID("test");
        image.setLikes("Likes");
        image.setOriginal(BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper_1825343));
        image2 = new MyImage();
        image2.setOriginal(bitmapFactory.decodeResource(getResources(), R.drawable.wallpaper_60223263));
        image2.setImageID("testID");
        image2.setLikes("testLikes");
        for (int i = 0; i < 3; i++) {
            itemList2.add(image);
            itemList2.add(image2);
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                for (int i = 0; i < 5; i++) {
//                    addOneItem(image);
//                }
                addItems(itemList2);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                if (newState == SCROLL_STATE_IDLE) { // 滚动静止时才加载图片资源，极大提升流畅度
//                    mAdapter.setScrolling(false);
//                    mAdapter.notifyDataSetChanged(); // notify调用后onBindViewHolder会响应调用
//                } else
//                    mAdapter.setScrolling(true);
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//        });

        mAdapter = new RecyclerViewListAdapter(getContext(), R.layout.liked_list_item, itemList);
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
}