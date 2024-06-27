package com.example.food_project.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_project.Adapter.BestFoodsAdapter;
import com.example.food_project.Adapter.FoodListAdapter;
import com.example.food_project.Domain.Foods;
import com.example.food_project.databinding.ActivityListBestFoodsBinding;
import com.example.food_project.databinding.ActivityListFoodsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


//Hiển thị danh sách món ăn best theo hàng dọc khi ấn vào View ALl
public class ListBestFoodsActivity extends BaseActivity{
    //    Sử dụng View Binding để truy cập các thành phần giao diện người dùng trong layout activity_list_foods.xml
    ActivityListBestFoodsBinding binding;
    //    Biến adapterListFood đại diện cho adapter của RecyclerView.
    private RecyclerView.Adapter apdapterListBestFood;

    @Override
//    Phương thức này được gọi khi Activity được tạo.
    protected void onCreate(Bundle savedInstanceState) {
//        Gọi phương thức onCreate của lớp cha để đảm bảo các hành vi mặc định được thực hiện.
        super.onCreate(savedInstanceState);
//        Sử dụng View Binding để khởi tạo binding.
        binding = ActivityListBestFoodsBinding.inflate(getLayoutInflater());
//        Đặt nội dung giao diện người dùng bằng gốc của binding.
        setContentView(binding.getRoot());
        initListBestFoods();
        binding.backBtn.setOnClickListener(v -> finish());


    }


    private void initListBestFoods() {
//        Thiết lập tham chiếu tới node "Foods" trong Firebase Realtime Database.
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Foods");
        // Show progress bar
        binding.progressBar.setVisibility(View.VISIBLE);
        ArrayList<Foods> list = new ArrayList<>();
//        Tạo một truy vấn để tìm các món ăn có thuộc tính "BestFood" là true.
        Query query = myRef.orderByChild("BestFood").equalTo(true);
//        Thêm ValueEventListener để lắng nghe một lần các thay đổi dữ liệu từ Firebase.
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
//            Được gọi khi dữ liệu tại vị trí này thay đổi
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Kiểm tra xem dữ liệu có tồn tại không.
                if (snapshot.exists()) {
//                    Lặp qua từng DataSnapshot và thêm đối tượng Foods vào danh sách
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        list.add(issue.getValue(Foods.class));
                    }

                    if (list.size() > 0) {
//                      Đây là ID của một thành phần giao diện người dùng (RecyclerView) trong file layout XML của bạn.
                        binding.bestFoodListViewVertical.setLayoutManager(new GridLayoutManager(ListBestFoodsActivity.this, 2));
//                        Tạo adapter cho RecyclerView.
                        apdapterListBestFood = new FoodListAdapter(list);
//                        Đặt adapter cho RecyclerView.
                        binding.bestFoodListViewVertical.setAdapter(apdapterListBestFood);
                    }
                    binding.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.progressBar.setVisibility(View.GONE);
                // Optionally handle the error here
            }
        });
    }
}
