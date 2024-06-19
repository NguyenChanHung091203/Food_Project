package com.example.food_project.Activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.food_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

// AppCompatActivity để đảm bảo ứng dụng của bạn có thể chạy mượt mà trên nhiều phiên bản hệ điều hành khác nhau
public class BaseActivity extends AppCompatActivity {

    //    mAuth để làm việc xác thực người dùng trên Firebase
    FirebaseAuth mAuth;
    //    database để làm việc với Firebase Realtime Database
    FirebaseDatabase database;
    //    TAG để in ra Logcat
    public String TAG = "Tam ne!";
    @Override
//    Phương thức onCreate được gọi khi Activity được tạo. Nó thường được sử dụng để thực hiện các thao tác khởi tạo
    protected void onCreate(Bundle savedInstanceState) {
//        Gọi phương thức onCreate của lớp cha (AppCompatActivity) để đảm bảo các hành vi mặc định được thực hiện.
        super.onCreate(savedInstanceState);
//        Khởi tạo đối tượng FirebaseDatabase để kết nối với cơ sở dữ liệu Firebase.
        database = FirebaseDatabase.getInstance();
//        Khởi tạo đối tượng FirebaseAuth để làm việc với xác thực người dùng Firebase.
        mAuth = FirebaseAuth.getInstance();
// Thiết lập màu của thanh trạng thái (status bar) thành màu trắng.
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));

    }
}