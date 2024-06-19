package com.example.food_project.Activity;

import android.os.Bundle;
<<<<<<< HEAD
import android.util.Log;
=======
>>>>>>> c79ff618b9fd9fcf6360c523d4176a5b173f42d9
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
<<<<<<< HEAD
import androidx.recyclerview.widget.RecyclerView.Adapter;
=======
>>>>>>> c79ff618b9fd9fcf6360c523d4176a5b173f42d9

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

    ActivityListFoodsBinding binding;
<<<<<<< HEAD
    private Adapter apdapterListFood;
=======
    private RecyclerView.Adapter apdapterListFood;
>>>>>>> c79ff618b9fd9fcf6360c523d4176a5b173f42d9
    private int categoryId;
    private String categoryName;
    private String searchText;
    private boolean isSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListFoodsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentExtra();
        initList();
        setVariable();


    }

    private void setVariable() {
    }

<<<<<<< HEAD
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

=======
>>>>>>> c79ff618b9fd9fcf6360c523d4176a5b173f42d9
    private void initList() {
        DatabaseReference myRef = database.getReference("Foods");
        binding.progressBar.setVisibility(View.VISIBLE);
        ArrayList<Foods> list = new ArrayList<>();

        Query query;
<<<<<<< HEAD
        if (isSearch) {
            query = myRef.orderByChild("Title").startAt(searchText).endAt(searchText + '\uf8ff');
        } else {
            query = myRef.orderByChild("CategoryId").equalTo(categoryId);
        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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
=======
        if(isSearch){
            query = myRef.orderByChild("Title").startAt(searchText).endAt(searchText + '\uf8ff');
        }
        else {
            query = myRef.orderByChild("CategoryId").equalTo(categoryId);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
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
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }


    private void getIntentExtra() {
        categoryId = getIntent().getIntExtra("CategoryId", 0);
//        categoryId = 3;
>>>>>>> c79ff618b9fd9fcf6360c523d4176a5b173f42d9
        categoryName = getIntent().getStringExtra("CategoryName");
        searchText = getIntent().getStringExtra("text");
        isSearch = getIntent().getBooleanExtra("isSearch", false);

        binding.titleTxt.setText(categoryName);
        binding.backBtn.setOnClickListener(v -> finish());
    }
}