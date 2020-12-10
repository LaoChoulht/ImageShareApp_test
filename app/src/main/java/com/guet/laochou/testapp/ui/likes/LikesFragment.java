package com.guet.laochou.testapp.ui.likes;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.guet.laochou.testapp.activities.R;
import com.guet.laochou.testapp.models.MyImage;
import com.guet.laochou.testapp.adapters.MainRecyclerViewAdapter;

import java.util.ArrayList;

public class LikesFragment extends Fragment {

    private ArrayList<MyImage> itemList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_likes, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.liked_RecyclerView);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        initData();

        MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter(getContext(), R.layout.liked_list_item, itemList);
        recyclerView.setAdapter(adapter);

        return root;
    }

    public boolean initData() {
        itemList = new ArrayList<>();
        MyImage image = new MyImage();
        MyImage image2 = new MyImage();
        image.setImageID("testID");
        image.setLikes("testLikes");
        image.setOriginal(BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper_1825343));
        image2.setImageID("testID2");
        image2.setLikes("testLikes2");
        image2.setOriginal(BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper_60223263));
        for (int i = 0; i < 10; i++) {
            itemList.add(image);
            itemList.add(image2);
        }
        return true;
    }
}