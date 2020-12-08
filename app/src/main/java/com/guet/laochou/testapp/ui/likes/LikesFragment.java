package com.guet.laochou.testapp.ui.likes;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.guet.laochou.testapp.activities.R;
import com.guet.laochou.testapp.models.MyImage;
import com.guet.laochou.testapp.adapters.LikedListAdapter;

import java.util.ArrayList;

public class LikesFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        likesViewModel =
//                ViewModelProviders.of(this).get(LikesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_likes, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.liked_RecyclerView);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<MyImage> list = new ArrayList<>();
        MyImage image = new MyImage();
        image.setImageID("testID");
        image.setLikes("testLikes");
        image.setOriginal(BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper_1825343));
        for (int i = 0; i < 20; i++)
            list.add(image);

        LikedListAdapter adapter = new LikedListAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);

        return root;
    }
}