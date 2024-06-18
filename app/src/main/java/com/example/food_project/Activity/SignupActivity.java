package com.example.food_project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.food_project.Domain.UserDetail;
import com.example.food_project.R;
import com.example.food_project.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    private EditText edtName,edtEmail,edtPass;
    private TextView txtLogin;
    private static final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        edtName = findViewById(R.id.nameEdt);
        edtEmail = findViewById(R.id.userEdt);
        edtPass = findViewById(R.id.passEdt);
        txtLogin = findViewById(R.id.txtLogin);

//        setVariable();
        Button btnRegister = findViewById(R.id.signupBtn);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
    private void Register() {
        String name,email,pass;
        name = edtName.getText().toString();
        email = edtEmail.getText().toString();
        pass = edtPass.getText().toString();
        if(TextUtils.isEmpty(name)){
            Toast.makeText(SignupActivity.this,"Please fill your name",Toast.LENGTH_SHORT).show();
            edtName.setError("Name is required");
            edtName.requestFocus();
        }
        if(TextUtils.isEmpty(email)){
            Toast.makeText(SignupActivity.this,"Please fill your email",Toast.LENGTH_SHORT).show();
            edtName.setError("Email is required");
            edtName.requestFocus();
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(SignupActivity.this,"Please fill your pass",Toast.LENGTH_SHORT).show();
            edtName.setError("Password is required");
            edtName.requestFocus();
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
                    UserDetail UD = new UserDetail(name,email,pass);
                    DatabaseReference drf = FirebaseDatabase.getInstance().getReference("Registered Users");
                    drf.child(user.getUid()).setValue(UD).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignupActivity.this,"Register Successful",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                }else {
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        edtPass.setError("Your Password is to weak");
                        edtPass.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        edtEmail.setError("Your Email is valid or already in use");
                        edtEmail.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(SignupActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }
}

//    private void setVariable() {
//        binding.signupBtn.setOnClickListener(v -> {
//            String name = binding.nameEdt.getText().toString();
//            String email = binding.userEdt.getText().toString();
//            String password = binding.passEdt.getText().toString();
//
//            if(password.length() < 6){
//                Toast.makeText(SignupActivity.this, "Your password must be 6 character", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            FirebaseAuth auth = FirebaseAuth.getInstance();
//            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignupActivity.this, task -> {
//                if(task.isSuccessful()){
//                    Log.i(TAG, "onComplete");
//                    FirebaseUser user = auth.getCurrentUser();
//                    UserDetail UD = new UserDetail(name);
//                    DatabaseReference drf = FirebaseDatabase.getInstance().getReference("Registered Users");
//                    drf.child(user.getUid()).setValue(UD).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if(task.isSuccessful()){
//                                Toast.makeText(SignupActivity.this,"Register Successful",Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    });
//                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
//                }else{
//                    Log.i(TAG, "Failure: ", task.getException());
//                    Toast.makeText(SignupActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
//                }
//            });
//        });