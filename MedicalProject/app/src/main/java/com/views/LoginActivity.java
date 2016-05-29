package com.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.medicalproject.R;

/**
 * Created by mingk on 2016/4/21.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;

    String[] userName = {"a@a.a"};
    String[] passWord = {"123456"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.Login_Toolbar);
        setSupportActionBar(toolbar);

        etUsername = (EditText) findViewById(R.id.Login_UserName);
        etPassword = (EditText) findViewById(R.id.Login_Password);
        btnLogin = (Button) findViewById(R.id.Button_Login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etUsername.getText().length() != 0 && etPassword.getText().length() != 0) {
                    //if (userName.equals(etUsername.getText().toString())) {
                    for (int i = 0; i < userName.length; i++) {
                        if (userName[i].equals(etUsername.getText().toString())) {
                            //Log.e("消息：", "找得到");
                            for (int j = 0; j < passWord.length; j++){
                                if (passWord[j].equals(etPassword.getText().toString())) {
                                    Intent intent = new Intent();
                                    intent.setClass(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getBaseContext(), "密码错误", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(getBaseContext(), "没有该用户", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else{
                    Toast.makeText(getBaseContext(), "用户名/密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
