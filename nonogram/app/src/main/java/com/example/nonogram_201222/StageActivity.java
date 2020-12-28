package com.example.nonogram_201222;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.HashMap;

public class StageActivity extends AppCompatActivity {

    ImageButton intentBtn;
    LinearLayout stageGrid;
    private HashMap<Integer, String> items = new HashMap<Integer, String>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);

        intentBtn = findViewById(R.id.intentBtn);
        stageGrid = findViewById(R.id.stageGrid);

        listView = findViewById(R.id.stageArea);

        for(int index = 0;index<5;index++){
            items.put(index, "Stage " + (index+1) + ". 학교");
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