package com.technight.musixmatch.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.technight.musixmatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAccount extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.nameEdit) EditText nameEdit;
    @BindView(R.id.emailEdit) EditText emailEdit;
    @BindView(R.id.passwordEdit) EditText passwordEdit;
    @BindView(R.id.confirmPasswordEdit) EditText confirmPasswordEdit;
    @BindView(R.id.createUserButton) Button createUserButton;
    @BindView(R.id.loginTextView) TextView loginTextView;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);

        createUserButton.setOnClickListener(this);
        loginTextView.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        if (view == loginTextView) {
            Intent intent = new Intent(CreateAccount.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        if (view == createUserButton) {
            createUser();
        }
    }

    private void createUser() {
        final String userName = nameEdit.getText().toString().trim();
        final String userEmail = emailEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();
        String confirmPassword = confirmPasswordEdit.getText().toString().trim();

        firebaseAuth.createUserWithEmailAndPassword(userEmail, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(CreateAccount.this, "Account Created", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CreateAccount.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}