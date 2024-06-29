package com.example.food_project.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_project.Adapter.WishListAdapter;
import com.example.food_project.Helper.ManagmentWishList;
import com.example.food_project.R;
import com.example.food_project.databinding.ActivityWishlistBinding;

public class WishListActivity extends AppCompatActivity {
    private ActivityWishlistBinding binding;
    private WishListAdapter adapter;
    private ManagmentWishList managmentWishList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWishlistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        managmentWishList = new ManagmentWishList(this);
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setVariable();
        initList();
    }

    private void setVariable() {

    }

    private void initList() {
        updateList();
    }

    private void updateList() {
        if (managmentWishList.getWishList().isEmpty()) {
            binding.emptyList.setVisibility(View.VISIBLE);
            binding.ScrollViewWishList.setVisibility(View.GONE);
        } else {
            binding.emptyList.setVisibility(View.GONE);
            binding.ScrollViewWishList.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.wishlistView.setLayoutManager(linearLayoutManager);
        adapter = new WishListAdapter(managmentWishList.getWishList(), this);
        binding.wishlistView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
