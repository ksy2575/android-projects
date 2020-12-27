package com.example.nonogram_201222;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class PaintActivity extends AppCompatActivity {

    ImageButton penBtn, eraserBtn, colorBtn, viewBtn, menuBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);

        penBtn = findViewById(R.id.penBtn);
        eraserBtn = findViewById(R.id.eraserBtn);
        colorBtn = findViewById(R.id.colorBtn);
        viewBtn = findViewById(R.id.viewBtn);
        menuBtn = findViewById(R.id.menuBtn);

        penBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "penBtn", Toast.LENGTH_LONG).show();
            }
        });
        eraserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "eraserBtn", Toast.LENGTH_LONG).show();
            }
        });
        colorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "colorBtn", Toast.LENGTH_LONG).show();
            }
        });
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "viewBtn", Toast.LENGTH_LONG).show();
            }
        });
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "menuBtn", Toast.LENGTH_LONG).show();
            }
        });
    }
}