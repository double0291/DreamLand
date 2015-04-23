package com.dreamland.ui;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dreamland.R;
import com.dreamland.base.BaseActivity;
import com.dreamland.util.Constants;
import com.dreamland.util.Logger;

public class LoginActivity extends BaseActivity implements View.OnClickListener,
        Response.Listener<String>, Response.ErrorListener {
    EditText mPhoneEditText, mPasswordEditText;
    TextView mRegisterNowTextView;
    Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        initView();
        sendRequest();
    }

    private void initView() {
        mPhoneEditText = (EditText) findViewById(R.id.phone);
        mPasswordEditText = (EditText) findViewById(R.id.password);
        mRegisterNowTextView = (TextView) findViewById(R.id.register_now);
        mLoginButton = (Button) findViewById(R.id.login);
        mLoginButton.setOnClickListener(this);
    }

    private void sendRequest() {
        StringRequest request = new StringRequest(Constants.HttpCmd.TEST, "http://www.baidu.com",
                this, this);
        app.mRequestQueue.add(request);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                startActivity(MainActivity.class);
                break;
        }
    }

    @Override
    public void onErrorResponse(Constants.HttpCmd cmd, VolleyError error) {
        Logger.d(error.toString(), false);
    }

    @Override
    public void onResponse(Constants.HttpCmd cmd, String response) {
        Logger.d(response, false);
    }
}
