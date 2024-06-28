package com.example.food_project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.food_project.R;

public class PaymentNotification extends AppCompatActivity {

    TextView txtNotification;
    AppCompatButton btnBackList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_notification);
        BindView();
        setVariable();

    }

    private void BindView() {
        txtNotification = findViewById(R.id.txtViewNotify);
        btnBackList = findViewById(R.id.btnBackList);

    }
    private void setVariable() {
        Intent intent = getIntent();
        txtNotification.setText(intent.getStringExtra("result"));
        btnBackList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }


}