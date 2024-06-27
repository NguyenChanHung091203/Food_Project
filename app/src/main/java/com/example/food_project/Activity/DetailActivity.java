package com.example.food_project.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.food_project.Domain.Foods;
import com.example.food_project.Helper.ManagmentCart;
import com.example.food_project.R;
import com.example.food_project.databinding.ActivityDetailBinding;

//Khai báo lớp DetailActivity mở rộng từ BaseActivity.
public class DetailActivity extends BaseActivity {
//    Khai báo biến binding cho view binding
    ActivityDetailBinding binding;
//    Khai báo biến object kiểu Foods để lưu thông tin chi tiết món ăn
    private Foods object;
    private int num = 1;
    private ManagmentCart managmentCart;

//    Ghi chú rằng phương thức này ghi đè một phương thức trong lớp cha
    @Override
//    Phương thức onCreate, được gọi khi activity được tạo lần đầu.
    protected void onCreate(Bundle savedInstanceState) {
//        Gọi phương thức onCreate của lớp cha
        super.onCreate(savedInstanceState);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        Đặt màu thanh trạng thái thành màu đen.
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        getIntentExtra();
        setVariable();
    }

    private void setVariable() {
        managmentCart = new ManagmentCart(this);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//         Tải hình ảnh vào ImageView sử dụng Glide.
        Glide.with(DetailActivity.this)
                .load(object.getImagePath())
                .into(binding.pic);

        binding.priceTxt.setText("$" + object.getPrice());
        binding.titleTxt.setText(object.getTitle());
        binding.descriptionTxt.setText(object.getDescription());
        binding.rateTxt.setText(object.getStar() + " Rating");
        binding.ratingBar.setRating((float) object.getStar());
        binding.totalTxt.setText((num * object.getPrice() + "$"));

        binding.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = num + 1;
                binding.numTxt.setText(num + " ");
                binding.totalTxt.setText("$" + (num * object.getPrice()));
            }
        });

        binding.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num > 1) {
                    num = num - 1;
                    binding.numTxt.setText(num + "");
                    binding.totalTxt.setText("$" + (num * object.getPrice()));
                }
            }
        });

        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setNumberInCart(num);
                managmentCart.insertFood(object);
            }
        });
    }

//    Điều này có nghĩa là Foods đã được truyền qua Intent với key là "object", và bạn đang lấy đối tượng đó ra để sử dụng trong DetailActivity
    private void getIntentExtra() {
        object = (Foods) getIntent().getSerializableExtra("object");
    }
}
