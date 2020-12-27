package com.example.nonogram_201222;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class StageActivity extends AppCompatActivity {

    ImageButton intentBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);

        intentBtn = findViewById(R.id.intentBtn);

        intentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StageActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
    }
}