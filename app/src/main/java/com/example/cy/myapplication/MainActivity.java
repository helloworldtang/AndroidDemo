package com.example.cy.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String 现在有 = "现在有";
    public static final String 元 = "元";
    public static final String USER = "user";
    public static final String PWD = "pwd";
    private EditText etInput;
    private TextView tvResult;
    private int total = 0;

    private EditText etUser;
    private EditText etPwd;
    private CheckBox cbRememberMe;

    private SharedPreferences sp;
    private Handler handler = new Handler();


    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etInput = (EditText) findViewById(R.id.tvInput);
        etInput.setSelection(etInput.getText().length());
        tvResult = (TextView) findViewById(R.id.tvResult);
        etUser = (EditText) findViewById(R.id.etUser);
        etPwd = (EditText) findViewById(R.id.etPwd);
        cbRememberMe = (CheckBox) findViewById(R.id.cbRememberMe);
        sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        etUser.setText(sp.getString(USER, ""));
        etPwd.setText(sp.getString(PWD, ""));
    }

    public void turnRedClickHandler(View view) {
        change2(Color.RED);
    }

    public void turnGreenClickHandler(View view) {
        change2(Color.GREEN);
    }

    public void turnBlueClickHandler(View view) {
        change2(Color.BLUE);
    }

    public void turnTransparencyClickHandler(View view) {
        changeBackgroundAlphaTo(0.0f);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("turnTransparency", "begin to change alpha to 0.5");
                changeBackgroundAlphaTo(0.5f);
                Log.i("turnTransparency", "end to change alpha to 0.5");
            }
        }, 10 * 1000);
    }

    private void changeBackgroundAlphaTo(float alphaValue) {
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.alpha = alphaValue;//０.０全透明．１.０不透明．
        getWindow().setAttributes(attributes);
    }


    private void change2(int color) {
        getWindow().setBackgroundDrawable(new ColorDrawable(color));
    }

    public void dialClickHandler(View view) {

        EditText editTextPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(this, "电话号码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        }


    }

    public void spendMoneyClickHandler(View view) {

        CharSequence cs = etInput.getText();
        if (cs != null) {
            int value = Integer.parseInt(cs.toString());
            total -= value;
            if (total < 0) {
                total = 0;
                Toast.makeText(this, R.string.tips_noting, Toast.LENGTH_SHORT).show();
            }
            tvResult.setText(现在有 + total + 元);
        }

    }

    public void earnMoneyClickHandler(View view) {
        CharSequence cs = etInput.getText();
        if (cs != null) {
            int value = Integer.parseInt(cs.toString());

            total += value;
            tvResult.setText(现在有 + total + 元);
        }
    }

    public void loginClickHandler(View view) {

        if (TextUtils.isEmpty(etUser.getText().toString().trim()) || TextUtils.isEmpty(etPwd.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences.Editor edit = sp.edit();
            if (cbRememberMe.isChecked()) {
                edit.putString(USER, etUser.getText().toString());
                edit.putString(PWD, etUser.getText().toString());
                edit.apply();
                Toast.makeText(getApplicationContext(), "Login info has recorded.", Toast.LENGTH_SHORT).show();
            } else {
                edit.clear();
                edit.apply();
                Toast.makeText(getApplicationContext(), "Nothing recorded.", Toast.LENGTH_SHORT).show();
            }


        }
    }
}
