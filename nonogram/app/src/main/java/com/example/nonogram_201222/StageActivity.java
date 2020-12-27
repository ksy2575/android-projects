package com.example.nonogram_201222;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.HashMap;

public class StageActivity extends AppCompatActivity {

    ImageButton intentBtn;

    private HashMap<Integer, String> items = new HashMap<Integer, String>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);

        intentBtn = findViewById(R.id.intentBtn);

        listView = findViewById(R.id.stageArea);

        for(int i = 0;i<5;i++){
            items.put(i, "item Number" + i);
        }
        System.out.println(items.values());

        CustomAdapter adapter = new CustomAdapter(this, 0, items);
        listView.setAdapter(adapter);

        intentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StageActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
    }
}