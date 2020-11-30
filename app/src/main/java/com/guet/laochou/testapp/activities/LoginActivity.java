package com.guet.laochou.testapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUserName = findViewById(R.id.login_et_userName);
        editPsw = findViewById(R.id.login_et_psw);
        pswVisibleBtn = findViewById(R.id.login_iv_pswSwitch);
        recordPswCheck = findViewById(R.id.login_ctv_recordPsw);
        loginBtn = findViewById(R.id.login_btn_login);

        pswVisibleBtn.setOnClickListener(this);
        recordPswCheck.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn_login:
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.login_iv_pswSwitch:
                pswVisible = !pswVisible;
                if (pswVisible) {
                    pswVisibleBtn.setImageResource(R.drawable.ic_baseline_visibility_24);
                    editPsw.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    pswVisibleBtn.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    editPsw.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                }
                editPsw.setSelection(editPsw.length());
                break;
            case R.id.login_ctv_recordPsw:

                break;
        }
    }
}
