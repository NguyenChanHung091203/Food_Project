package com.example.food_project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.food_project.Domain.UserDetail;
import com.example.food_project.R;
import com.example.food_project.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        setupViews();
    }

    private void setupViews() {
        binding.loginBtn.setOnClickListener(v -> loginUser());
        binding.txtRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String email = binding.userEdt.getText().toString().trim();
        String password = binding.passEdt.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please fill in both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            fetchUserDetails(user.getUid());
                        } else {
                            Log.e(TAG, "User is null");
                            Toast.makeText(LoginActivity.this, "User authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e(TAG, "Sign-in failed", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchUserDetails(String userId) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Registered Users").child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserDetail userDetail = snapshot.getValue(UserDetail.class);
                    if (userDetail != null) {
                        String userName = userDetail.getNameEdt();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("userName", userName);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.e(TAG, "UserDetail is null");
                        Toast.makeText(LoginActivity.this, "User detail not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "User not found in database");
                    Toast.makeText(LoginActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Database error: " + error.getMessage());
                Toast.makeText(LoginActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
