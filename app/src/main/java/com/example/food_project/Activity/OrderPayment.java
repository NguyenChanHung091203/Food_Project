package com.example.food_project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.food_project.Api.CreateOrder;
import com.example.food_project.Domain.Order;
import com.example.food_project.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;


public class OrderPayment extends AppCompatActivity {
    TextView txtTotal;

    TextInputEditText edtPhoneNumber, edtDiaChi, edtName;
    ImageView  btnBackList;
    AppCompatButton btnDatHang,btnThanhToan;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ordersRef = database.getReference("orders");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_payment);
        txtTotal = findViewById(R.id.txtTotal);
        btnDatHang = findViewById(R.id.btnDatHang);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtName =findViewById(R.id.edtName);
        btnBackList = findViewById(R.id.btnBackList);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ZaloPaySDK.init(2553, Environment.SANDBOX);

        Intent intent = getIntent();
        Double total = intent.getDoubleExtra("total", (double) 0);
        String totalString = String.format("%.0f", total);
        txtTotal.setText(totalString);

        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = edtPhoneNumber.getText().toString();
                String diaChi = edtDiaChi.getText().toString();
                String name = edtName.getText().toString();

                if (phoneNumber.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (diaChi.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập địa chỉ!",  Toast.LENGTH_SHORT).show();
                    return;
                }


                Double total = intent.getDoubleExtra("total", (double) 0);
                String orderId = ordersRef.push().getKey();
                Order order = new Order(orderId, name, total, phoneNumber, diaChi);

                ordersRef.child(orderId).setValue(order);

                Intent intent1 = new Intent(OrderPayment.this, PaymentNotification.class);
                intent1.putExtra("result", "Đặt hàng thành công");
                startActivity(intent1);
                finish();
            }
        });


        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = edtPhoneNumber.getText().toString();
                String diachi = edtDiaChi.getText().toString();
                String name = edtName.getText().toString();

                // Validate user input
                if (phoneNumber.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (diachi.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập địa chỉ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Double total = intent.getDoubleExtra("total", (double) 0);
                String orderId = ordersRef.push().getKey(); // Generate unique ID
                Order order = new Order(orderId, name, total, phoneNumber, diachi);

                CreateOrder orderApi = new CreateOrder();
                try {
                    JSONObject data = orderApi.createOrder(totalString);
                    String code = data.getString("return_code");
                    if (code.equals("1")) {
                        String token = data.getString("zp_trans_token");
                        ZaloPaySDK.getInstance().payOrder(OrderPayment.this, token, "demozpdk://app", new PayOrderListener() {
                            @Override
                            public void onPaymentSucceeded(String s, String s1, String s2) {
                                // Save order to Firebase on successful payment
                                ordersRef.child(orderId).setValue(order);
                                Intent intent1 = new Intent(OrderPayment.this, PaymentNotification.class);
                                intent1.putExtra("result", "Thanh toán thành công");
                                startActivity(intent1);
                            }

                            @Override
                            public void onPaymentCanceled(String s, String s1) {
                                Intent intent2 = new Intent(OrderPayment.this, PaymentNotification.class);
                                intent2.putExtra("result", "Hủy thanh toán");
                                startActivity(intent2);
                            }

                            @Override
                            public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                                Log.e("ZaloPay", "Payment error: " + zaloPayError.getClass());
                                Intent intent3 = new Intent(OrderPayment.this, PaymentNotification.class);
                                intent3.putExtra("result", "Lỗi thanh toán");
                                startActivity(intent3);
                            }
                        });
                    } else {
                        Log.e("CreateOrder", "Error creating order: " + data.getString("return_message"));
                        Toast.makeText(getApplicationContext(), "Tạo đơn hàng thất bại!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("ZaloPay", "Error: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Lỗi thanh toán!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnBackList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}