package com.technight.musixmatch.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.technight.musixmatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = LoginActivity.class.getSimpleName();
    @BindView(R.id.createAccountTextView) TextView createAccountTextView;
    @BindView(R.id.emailEdit) TextView emailEdit;
    @BindView(R.id.passwordEdit) TextView passwordEdit;
    @BindView(R.id.loginButton) Button loginButton;
    @BindView(R.id.loadingTextView) TextView loadingTextView;
    @BindView(R.id.logLock) ImageView logLock;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        createAccountTextView.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        Animation animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.rotation);
        logLock.startAnimation(animation);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if (firebaseUser != null) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == createAccountTextView) {
            Intent intent = new Intent(LoginActivity.this, CreateAccount.class);
            startActivity(intent);
            finish();
        }

        if (view == loginButton) {
            logInWithPassWord();
            showLoadingState();
        }
    }

    private void logInWithPassWord() {
        String email = emailEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();

        if (email.equals("")){
            emailEdit.setError("Please Enter Your Email");
            return;
        }
        if (password.equals("")) {
            passwordEdit.setError("Password Cannot be blank");
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideLoadingState();
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void showLoadingState() {
        loadingTextView.setVisibility(View.VISIBLE);
        passwordEdit.setVisibility(View.GONE);
        emailEdit.setVisibility(View.GONE);
        createAccountTextView.setVisibility(View.GONE);
        loginButton.setVisibility(View.GONE);
    }

    private void hideLoadingState() {
        loadingTextView.setVisibility(View.GONE);
        passwordEdit.setVisibility(View.VISIBLE);
        emailEdit.setVisibility(View.VISIBLE);
        createAccountTextView.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.VISIBLE);
    }
}