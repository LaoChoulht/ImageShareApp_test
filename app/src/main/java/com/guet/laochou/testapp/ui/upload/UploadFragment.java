package com.guet.laochou.testapp.ui.upload;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.guet.laochou.testapp.activities.R;
import com.guet.laochou.testapp.adapters.UploadRecyclerViewAdapter;
import com.guet.laochou.testapp.models.MyImage;
import com.guet.laochou.testapp.models.Picture;
import com.guet.laochou.testapp.models.PictureUpload;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadFragment extends Fragment {

    //权限
    private final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private final int IMAGE_REQUEST_CODE = 1;

    private FloatingActionButton floatButton_add, floatButton_upload;

    private List<Picture> uploadList;
    private List<MyImage> showList;
    private MyImage image;
    private Picture picture;
    private PictureUpload pictureUpload;
    private UploadRecyclerViewAdapter mAdapter;
    private ImageView thumbnail;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager layoutManager;

    private OkHttpClient client;
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private String remoteUploadURL;
    private Runnable runnable;
    private String spFileName, userTokenKey;
    private SharedPreferences spFile;
    private Gson gson;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_upload, container, false);
        pictureUpload = new PictureUpload();
        uploadList = new ArrayList<>();
        showList = new ArrayList<>();
        gson = new Gson();

        remoteUploadURL = getActivity().getResources().getString(R.string.remoteServerID) +
                getActivity().getResources().getString(R.string.upLoadAPI);
        spFileName = getResources().getString(R.string.login_userToken_sp_file_name);
        userTokenKey = getResources().getString(R.string.login_userToken);
        spFile = getActivity().getSharedPreferences(spFileName, Context.MODE_PRIVATE);

        recyclerView = root.findViewById(R.id.upload_recyclerView);
        floatButton_add = root.findViewById(R.id.upload_floatButton_add);
        floatButton_upload = root.findViewById(R.id.upload_floatButton_upload);

        floatButton_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image = new MyImage();
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            PERMISSIONS_STORAGE,
                            REQUEST_EXTERNAL_STORAGE);
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, IMAGE_REQUEST_CODE);
                }
            }
        });
        runnable = new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                Bundle data = new Bundle();
                String s = new String();
                try {
                    Log.d("TESTTAG", "showlist: " + showList.size());
                    s = remoteUpload(showList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("TESTTAG", "uploadrun: " + s);
            }
        };
        floatButton_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(runnable).start();
            }
        });

        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        DefaultItemAnimator animator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(animator);
        mAdapter = new UploadRecyclerViewAdapter(getContext(), R.layout.upload_list_item, showList);
        recyclerView.setAdapter(mAdapter);
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //获取照片
            case IMAGE_REQUEST_CODE:
                Uri selectImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContext().getContentResolver().query(
                        selectImage,
                        filePathColumn,
                        null,
                        null,
                        null
                );
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String path = cursor.getString(columnIndex);
                cursor.close();
                Bitmap bitmap = BitmapFactory.decodeFile(path);

                image.setOriginal(bitmap);
//                image.setImageID(get);
                addOneItem(image);
                //                Log.d("TESTTAG", "picture " + picture + " list" + uploadList);
                break;
            default:
                break;
        }
    }

    public void addOneItem(MyImage data) {
        showList.add(0, data);
        mAdapter.notifyItemRangeChanged(0, showList.size());
//        Log.d("TESTTAG", "addOneItem: ");
        recyclerView.scrollToPosition(0);
    }

    public String remoteUpload(List<MyImage> showList) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (MyImage i : showList) {
            i.getOriginal().compress(Bitmap.CompressFormat.JPEG, 50, baos);
            picture = new Picture();
            picture.setPicture(new String(baos.toByteArray(),"utf-8"));
            picture.setPictureName(i.getImageID());
            uploadList.add(0, picture);
        }
        pictureUpload.setToken(spFile.getString(userTokenKey,"null"));
        pictureUpload.setPictures(uploadList);

        String uploadResultString = post(remoteUploadURL, gson.toJson(pictureUpload));
        return uploadResultString;
    }

    String post(String url, String json) throws IOException {
        client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        Log.d("TESTTAG", "upload post: "+json);
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