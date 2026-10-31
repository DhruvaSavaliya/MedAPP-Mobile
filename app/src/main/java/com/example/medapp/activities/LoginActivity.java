package com.example.medapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medapp.R;
import com.example.medapp.utils.FirebaseHelper;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private TextView txtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtSignup);

        btnLogin.setOnClickListener(v -> login());

        txtRegister.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    private void login() {

        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth auth = FirebaseHelper.getAuth();

        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {

                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

                    // 🔹 Admin Login
                    if (email.equals("admin@medapp.com")) {

                        Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                        intent.putExtra("role", "admin");

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                    // 🔹 Normal User Login
                    else {

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("role", "user");

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                    finish();
                })

                .addOnFailureListener(e ->
                        Toast.makeText(this, "Login Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
}