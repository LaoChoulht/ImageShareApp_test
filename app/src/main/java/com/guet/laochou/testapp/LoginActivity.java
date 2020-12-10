package com.guet.laochou.testapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.guet.laochou.testapp.activities.R;
import com.guet.laochou.testapp.models.LoginResult;
import com.guet.laochou.testapp.models.UserLoginInfo;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText editPsw, editUserName;
    private ImageView pswVisibleBtn;
    private CheckedTextView recordPswCheck;
    private Button loginBtn;
    private boolean pswVisible = false;

    private String spFileName, accountKey, pswKey, rememberPswKey, userTokenKey;
    private Gson gson;
    private String loginResultString;
    private SharedPreferences spFile;
    String remoteLoginString;

    private static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    private OkHttpClient client;
    private static Handler handler;
    private Runnable runnable;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // find views
        editUserName = findViewById(R.id.login_et_userName);
        editPsw = findViewById(R.id.login_et_psw);
        pswVisibleBtn = findViewById(R.id.login_iv_pswVisibleSwitch);
        recordPswCheck = findViewById(R.id.login_ctv_recordPsw);
        loginBtn = findViewById(R.id.login_btn_login);

        remoteLoginString = getResources().getString(R.string.remoteServerID)+
                getResources().getString(R.string.loginAPI);
        spFileName = getResources().getString(R.string.login_sp_file_name);
        accountKey = getResources().getString(R.string.login_account_name);
        pswKey = getResources().getString(R.string.login_Psw);
        rememberPswKey = getResources().getString(R.string.login_remember_Psw);
        userTokenKey = getResources().getString(R.string.login_userToken);
        spFile = getSharedPreferences(spFileName, Context.MODE_PRIVATE);


        loadUserLoginInfo();
        setOnClickListeners();

        gson = new Gson();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                LoginResult result = gson.fromJson(data.getString("result"), LoginResult.class);
                if(result.getStatus() == 1){
                    spFileName = getResources().getString(R.string.login_userToken_sp_file_name);
                    spFile = getSharedPreferences(spFileName, Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = spFile.edit();
                    edit.putString(userTokenKey, result.getData());
                    edit.commit();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    onDestroy();
                }
                String testname = spFileName;
//                getResources().getString(R.string.login_userToken_sp_file_name);
                String testkey = userTokenKey;
//                        getResources().getString(R.string.login_userToken);
                SharedPreferences testspfile = spFile;
//                        getSharedPreferences(testname, Context.MODE_PRIVATE);
                Log.d("TESTTAG", "test user token "+testspfile.getString(testkey,"null"));
//                Log.d("TESTTAG", "run: " + spFile.getString(userTokenKey,"null"));
            }
        };

        runnable = new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                Bundle data = new Bundle();
                int state = remoteLogin();
                if (loginResultString != null)
                    data.putSerializable("result", loginResultString);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_ctv_recordPsw:
                recordPswCheck.toggle();
                break;
            case R.id.login_iv_pswVisibleSwitch:
                pswVisibility_rev();
                break;
            case R.id.login_btn_login:
                SharedPreferences spFile = getSharedPreferences(spFileName, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = spFile.edit();
                if (recordPswCheck.isChecked()) {
                    String account = editUserName.getText().toString();
                    String psw = editPsw.getText().toString();

                    editor.putString(accountKey, account);
                    editor.putString(pswKey, psw);
                    editor.putBoolean(rememberPswKey, true);
                } else {
                    editor.remove(accountKey);
                    editor.remove(pswKey);
                    editor.remove(rememberPswKey);
                }
                editor.apply();
                new Thread(runnable).start();
                break;
        }
    }

    private int remoteLogin() {
        UserLoginInfo info = new UserLoginInfo();
        info.setUserName(editUserName.getText().toString());
        info.setUserPsw(editPsw.getText().toString());
        try {
            loginResultString = post(remoteLoginString, gson.toJson(info));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (loginResultString != null)
            return 1;
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    String post(String url, String json) throws IOException {
        client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private void loadUserLoginInfo() {
        // load user login info from file
        String account = spFile.getString(accountKey, null);
        String psw = spFile.getString(pswKey, null);
        if (account != null && !TextUtils.isEmpty(account))
            editUserName.setText(account);
        if (psw != null && !TextUtils.isEmpty(psw))
            editPsw.setText(psw);
        recordPswCheck.setChecked(spFile.getBoolean(rememberPswKey, false));
    }

    private void pswVisibility_rev() {
        pswVisible = !pswVisible;
        if (pswVisible) {
            pswVisibleBtn.setImageResource(R.drawable.ic_baseline_visibility_24);
            editPsw.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            pswVisibleBtn.setImageResource(R.drawable.ic_baseline_visibility_off_24);
            editPsw.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
        }
        editPsw.setSelection(editPsw.length());
    }

    private void setOnClickListeners() {
        // set onClickListener
        pswVisibleBtn.setOnClickListener(this);
        recordPswCheck.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }
}
