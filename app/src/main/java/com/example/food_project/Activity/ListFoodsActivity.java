package com.example.food_project.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.food_project.Adapter.CategoryAdapter;
import com.example.food_project.Adapter.FoodListAdapter;
import com.example.food_project.Domain.Category;
import com.example.food_project.Domain.Foods;
import com.example.food_project.R;
import com.example.food_project.databinding.ActivityListFoodsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListFoodsActivity extends BaseActivity {

//    Sử dụng View Binding để truy cập các thành phần giao diện người dùng trong layout activity_list_foods.xml
    ActivityListFoodsBinding binding;
//    Biến adapterListFood đại diện cho adapter của RecyclerView.
    private Adapter apdapterListFood;
//    Các biến categoryId, categoryName, searchText, isSearch được sử dụng để lưu trữ dữ liệu nhận từ Intent
    private int categoryId;
    private String categoryName;
    private String searchText;
    private boolean isSearch;

    @Override
//    Phương thức này được gọi khi Activity được tạo.
    protected void onCreate(Bundle savedInstanceState) {
//        Gọi phương thức onCreate của lớp cha để đảm bảo các hành vi mặc định được thực hiện.
        super.onCreate(savedInstanceState);
//        Sử dụng View Binding để khởi tạo binding.
        binding = ActivityListFoodsBinding.inflate(getLayoutInflater());
//        Đặt nội dung giao diện người dùng bằng gốc của binding.
        setContentView(binding.getRoot());

        getIntentExtra();
        initList();
        setVariable();


    }

    private void setVariable() {
    }

//    private void initList() {
//        DatabaseReference myRef = database.getReference("Foods");
//        binding.progressBar.setVisibility(View.VISIBLE);
//        ArrayList<Foods> list = new ArrayList<>();
//
//        Query query;
//        if(isSearch){
//            query = myRef.orderByChild("Title").startAt(searchText).endAt(searchText + '\uf8ff');
//        }
//        else {
//            query = myRef.orderByChild("CategoryId").equalTo(categoryId);
//
//            query.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (snapshot.exists()) {
//                        for (DataSnapshot issue : snapshot.getChildren()) {
//                            list.add(issue.getValue(Foods.class));
//                        }
//                        if (list.size() > 0) {
//                            binding.foodListView.setLayoutManager(new GridLayoutManager(ListFoodsActivity.this, 2));
//                            apdapterListFood = new FoodListAdapter(list);
//                            binding.foodListView.setAdapter(apdapterListFood);
//                        }
//                        binding.progressBar.setVisibility(View.GONE);
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
//    }

    private void initList() {
//        Tham chiếu đến "Foods" trong cơ sở dữ liệu Firebase.
        DatabaseReference myRef = database.getReference("Foods");
//        Hiển thị ProgressBar trong khi tải dữ liệu
        binding.progressBar.setVisibility(View.VISIBLE);
//        Tạo một danh sách trống để lưu trữ các đối tượng Foods.
        ArrayList<Foods> list = new ArrayList<>();
//      Khai báo biến query để thực hiện truy vấn
        Query query;
        if (isSearch) {
            query = myRef.orderByChild("Title").startAt(searchText).endAt(searchText + '\uf8ff');
        } else {
            query = myRef.orderByChild("CategoryId").equalTo(categoryId);
        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
//             Phương thức này được gọi khi có dữ liệu từ truy vấn.
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Kiểm tra nếu có dữ liệu
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        list.add(issue.getValue(Foods.class));
                    }
                    if (list.size() > 0) {
                        binding.foodListView.setLayoutManager(new GridLayoutManager(ListFoodsActivity.this, 2));
                        apdapterListFood = new FoodListAdapter(list);
                        binding.foodListView.setAdapter(apdapterListFood);
                    }
                    binding.progressBar.setVisibility(View.GONE);
                } else {
                    // Xử lý khi không tìm thấy kết quả
                    Log.d("ListFoodsActivity", "No results found");
                    binding.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ListFoodsActivity", "Query cancelled or failed: " + error.getMessage());
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }



    private void getIntentExtra() {
        categoryId = getIntent().getIntExtra("CategoryId", 0);
        categoryName = getIntent().getStringExtra("CategoryName");
        searchText = getIntent().getStringExtra("text");
        isSearch = getIntent().getBooleanExtra("isSearch", false);

        binding.titleTxt.setText(categoryName);
        binding.backBtn.setOnClickListener(v -> finish());
    }
}