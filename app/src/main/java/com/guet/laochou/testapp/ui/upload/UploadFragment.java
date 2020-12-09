package com.guet.laochou.testapp.ui.upload;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.guet.laochou.testapp.activities.R;
import com.guet.laochou.testapp.adapters.RecyclerViewListAdapter;
import com.guet.laochou.testapp.models.MyImage;
import com.guet.laochou.testapp.models.Picture;
import com.guet.laochou.testapp.models.PictureUpload;

import java.util.ArrayList;

public class UploadFragment extends Fragment {

    //权限
    private final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private final int IMAGE_REQUEST_CODE = 1;

    private PictureUpload pictureUpload;
    private RecyclerViewListAdapter mAdapter;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager layoutManager;

    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_upload, container, false);
        recyclerView = root.findViewById(R.id.upload_recyclerView);
        layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        return root;
    }
}