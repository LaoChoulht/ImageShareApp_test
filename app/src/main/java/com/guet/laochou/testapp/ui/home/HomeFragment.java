package com.guet.laochou.testapp.ui.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.guet.laochou.testapp.R;
import com.guet.laochou.testapp.models.MainListViewHolder;
import com.guet.laochou.testapp.models.MyImage;
import com.guet.laochou.testapp.utils.MainListAdapter;
import com.guet.laochou.testapp.utils.UMainListAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ListView listView,listView2;
    private MyImage test1, test2;
    private ArrayList<MyImage> itemList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        listView = root.findViewById(R.id.main_lv_imageList);

        itemList = new ArrayList<>();
        test1 = new MyImage();

        test1.setImageID("testID");
        test1.setLikes("testLikes");
        test1.setOriginal(BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper_1825343));
        for (int i = 0; i < 20; i++)
            itemList.add(test1);

        UMainListAdapter adapter = new UMainListAdapter(getContext(),itemList);
        listView.setAdapter(adapter);
//        list_L.setAdapter(adapter);
//        list_R.setAdapter(adapter);
        return root;
    }


    public boolean initData() {
        String[] thumbnail, imageID, likeNum;
//        thumbnail = root.getResources().getStringArray(R.array.images);
//        imageID = root.getResources().getStringArray(R.array.imageIDs);
//        likeNum = root.getResources().getStringArray(R.array.imageLikes);
        return true;
    }
}