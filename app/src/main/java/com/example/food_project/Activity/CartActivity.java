package com.example.food_project.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_project.Adapter.CartAdapter;
import com.example.food_project.Helper.ChangeNumberItemsListener;
import com.example.food_project.Helper.ManagmentCart;
import com.example.food_project.R;
import com.example.food_project.databinding.ActivityCartBinding;

public class CartActivity extends BaseActivity {
private ActivityCartBinding binding;
private RecyclerView.Adapter adapter;
private ManagmentCart managmentCart;
private double tax;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        managmentCart = new ManagmentCart(this);
        setVariable();
        caculateCart();
        initList();
    }

    private void initList() {
        if(managmentCart.getListCart().isEmpty()){
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scrollviewCart.setVisibility(View.GONE);

        }else {
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scrollviewCart.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager LinearLayoutManager = new LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false);
        binding.cardView.setLayoutManager(LinearLayoutManager);
        adapter = new CartAdapter(managmentCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void change() {
                caculateCart();
            }
        });
        binding.cardView.setAdapter(adapter);
    }

    private void caculateCart() {
        double percentTax = 0.02; //percent 2% tax
        double delivery = 10; // 10 Dollar

        tax = Math.round(managmentCart.getTotalFee() * percentTax * 100.0) / 100.0;

        double total = Math.round((managmentCart.getTotalFee() + tax + delivery) * 100.0) / 100.0;

        double itemTotal = Math.round(managmentCart.getTotalFee() * 100.0) / 100.0;

        binding.totalFeeTxt.setText("$" + itemTotal);
        binding.taxTxt.setText("$" + tax);
        binding.deliveryTxt.setText("$" + delivery);
        binding.totalTxt.setText("$" + total);
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}