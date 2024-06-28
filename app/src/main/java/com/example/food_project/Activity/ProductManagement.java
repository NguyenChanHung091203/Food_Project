package com.example.food_project.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.food_project.Domain.Products;
import com.example.food_project.databinding.ActivityProductManagementBinding;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProductManagement extends AppCompatActivity {

    private ActivityProductManagementBinding binding;
    private Uri imageUri;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductManagementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReference("products"); // Example path

        binding.btnUpImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        binding.btnUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProductData();
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            binding.imageViewPro.setImageURI(imageUri);
        }
    }

    private void uploadProductData() {
        String name = binding.edtName.getText().toString();
        String price = binding.edtprice.getText().toString();
        String type = binding.edtListPr.getText().toString();
        String description = binding.edtDetail.getText().toString();

        if (name.isEmpty() || price.isEmpty() || type.isEmpty() || description.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        Products product = new Products(name, price, type, description, imageUri.toString());

        String key = databaseReference.push().getKey();
        databaseReference.child(key).setValue(product)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(ProductManagement.this, "Product uploaded successfully!", Toast.LENGTH_SHORT).show();

                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ProductManagement.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

}
