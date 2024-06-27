package com.example.food_project.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
private double discount = 0.0;
private double tax;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        managmentCart = new ManagmentCart(this);
        setVariable();
        calculateCart();
        initList();
    }

    private void calculateCart() {
        double delivery = 10; // 10 Dollar delivery fee

        double totalFee = managmentCart.getTotalFee();
        double itemTotal = Math.round(totalFee * 100.0) / 100.0;
        double discountAmount = Math.round(totalFee * discount * 100.0) / 100.0;

        double total = Math.round((totalFee - discountAmount + delivery) * 100.0) / 100.0;

        binding.totalFeeTxt.setText("$" + itemTotal);
        binding.discountTxt.setText("$" + discountAmount);
        binding.deliveryTxt.setText("$" + delivery);
        binding.totalTxt.setText("$" + total);
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
                calculateCart();
            }
        });
        binding.cardView.setAdapter(adapter);
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.discountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String discountCode = binding.discountCodeTxt.getText().toString();
                applyDiscount(discountCode);
            }
        });
    }

    private void applyDiscount(String discountCode) {
        // Define your discount codes and corresponding discount rates
        switch (discountCode) {
            case "PhamDung":
                discount = 0.5; // 10% discount
                Toast.makeText(this, "Discount applied successfully: 50%", Toast.LENGTH_SHORT).show();
                break;
            case "DangDuong":
                discount = -0.9; // 20% discount
                Toast.makeText(this, "Discount applied successfully: -90%", Toast.LENGTH_SHORT).show();
                break;
            default:
                discount = 0.0;
                Toast.makeText(this, "Invalid discount code", Toast.LENGTH_SHORT).show();
                break;
        }
        calculateCart();
    }
}