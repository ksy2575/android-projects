package com.example.nonogram_201222;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    ImageButton hintBtn, writeBtn, checkBtn;
    FrameLayout gameArea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

//        gameArea = setContentView(new GameView(this));

//        setContentView(new GameView(this));

        hintBtn = findViewById(R.id.hintBtn);
        writeBtn = findViewById(R.id.writeBtn);
        checkBtn = findViewById(R.id.checkBtn);

        hintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "hintBtn", Toast.LENGTH_LONG).show();
            }
        });
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "writeBtn", Toast.LENGTH_LONG).show();
            }
        });
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "checkBtn", Toast.LENGTH_LONG).show();
            }
        });
    }
}