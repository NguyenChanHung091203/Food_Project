package com.example.food_project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.food_project.R;
import com.example.food_project.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

//Khai báo lớp LoginActivity kế thừa từ BaseActivity.
// Điều này có nghĩa là LoginActivity sẽ thừa hưởng tất cả các biến và phương thức từ BaseActivity.
public class LoginActivity extends BaseActivity {
//    sử dụng View Binding để truy cập các thành phần giao diện người dùng trong layout activity_login.xml
    ActivityLoginBinding binding;
    Button login_btn;

    @Override
//     Phương thức onCreate được gọi khi Activity được tạo. Nó thường được sử dụng để thực hiện các thao tác khởi tạo.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        login_btn = findViewById(R.id.loginBtn);
        setContentView(binding.getRoot());
        setVariable();
    }

    private void setVariable() {
        binding.loginBtn.setOnClickListener(v -> {
            String email = binding.userEdt.getText().toString();
            String password = binding.passEdt.getText().toString();
            if(!email.isEmpty() && !password.isEmpty()){
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, task -> {
                    if(task.isSuccessful()){
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                Toast.makeText(LoginActivity.this, "Please fill username and password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}