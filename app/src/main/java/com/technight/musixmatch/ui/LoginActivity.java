package com.technight.musixmatch.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.technight.musixmatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.registerText) TextView registerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        registerText.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == registerText) {
            Intent intent = new Intent(LoginActivity.this, CreateAccount.class);
            startActivity(intent);
            finish();
        }
    }
}