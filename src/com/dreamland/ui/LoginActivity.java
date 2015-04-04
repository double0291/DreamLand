package com.dreamland.ui;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dreamland.R;
import com.dreamland.base.BaseActivity;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    EditText mPhoneEditText, mPasswordEditText;
    TextView mRegisterNowTextView;
    Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        mPhoneEditText = (EditText) findViewById(R.id.phone);
        mPasswordEditText = (EditText) findViewById(R.id.password);
        mRegisterNowTextView = (TextView) findViewById(R.id.register_now);
        mLoginButton = (Button) findViewById(R.id.login);
        mLoginButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                startActivity(MainActivity.class);
                break;
        }
    }

}
