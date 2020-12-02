package com.guet.laochou.testapp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;

import com.guet.laochou.testapp.R;

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText editPsw, editUserName;
    private ImageView pswVisibleBtn;
    private CheckedTextView recordPswCheck;
    private Button loginBtn;
    private boolean pswVisible = false;

    private String spFileName, accountKey, pswKey, rememberPswKey;
    private SharedPreferences spFile;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // find views
        editUserName = findViewById(R.id.login_et_userName);
        editPsw = findViewById(R.id.login_et_psw);
        pswVisibleBtn = findViewById(R.id.login_iv_pswVisibleSwitch);
        recordPswCheck = findViewById(R.id.login_ctv_recordPsw);
        loginBtn = findViewById(R.id.login_btn_login);

        spFileName = getResources().getString(R.string.shared_preferences_file_name);
        accountKey = getResources().getString(R.string.login_account_name);
        pswKey = getResources().getString(R.string.login_Psw);
        rememberPswKey = getResources().getString(R.string.login_remember_Psw);
        spFile = getSharedPreferences(spFileName, Context.MODE_PRIVATE);

        loadUserLoginInfo();
        setOnClickListeners();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_ctv_recordPsw:
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
                recordPswCheck.toggle();
                break;
            case R.id.login_iv_pswVisibleSwitch:
                pswVisibility_rev();
                break;
            case R.id.login_btn_login:
                remoteLogin();
                break;
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

    private int remoteLogin() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        return 0;
    }
}
