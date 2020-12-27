package com.example.nonogram_201222;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AlbumActivity extends AppCompatActivity {


    ImageButton newpaintBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        newpaintBtn = findViewById(R.id.newpaintBtn);

        newpaintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlbumActivity.this, PaintActivity.class);
                startActivity(intent);
            }
        });
    }
}