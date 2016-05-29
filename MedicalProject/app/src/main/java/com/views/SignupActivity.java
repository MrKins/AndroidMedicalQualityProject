package com.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.medicalproject.R;

/**
 * Created by mingk on 2016/4/23.
 */
public class SignupActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnSignup;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = (EditText) findViewById(R.id.Signup_Username);
        etPassword = (EditText) findViewById(R.id.Signup_Password);
        btnSignup = (Button) findViewById(R.id.Signup_Button);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
