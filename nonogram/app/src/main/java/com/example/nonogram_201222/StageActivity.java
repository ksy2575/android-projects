package com.example.nonogram_201222;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;

public class StageActivity extends AppCompatActivity {

    ImageButton intentBtn;
    LinearLayout stageGrid;
    private HashMap<Integer, String> items = new HashMap<Integer, String>();
    private ListView listView;

    int foldIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);

        intentBtn = findViewById(R.id.intentBtn);

        listView = findViewById(R.id.stageArea);

        for(int index = 0;index<5;index++){
            items.put(index, "Stage " + (index+1) + ". 학교");
        }
        System.out.println(items.values());

        final CustomAdapter adapter = new CustomAdapter(this, 0, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.d("asdf", i + " onClick");
                Toast.makeText(getApplicationContext(), "asdf"+i, Toast.LENGTH_LONG).show();
                //adapterView에 position 전달
//                adapter.folding(i);

//                if(foldIndex != -1){
//                    stageGrid = (LinearLayout)listView.getChildAt(foldIndex).findViewById(R.id.stageGrid);
//                    stageGrid.setVisibility(View.GONE);
//                }
//                stageGrid = (LinearLayout)listView.getChildAt(i).findViewById(R.id.stageGrid);
//                stageGrid.setVisibility(View.VISIBLE);
//                foldIndex = i;
//                if(stageGrid.getVisibility() == View.GONE){
//                    stageGrid.setVisibility(View.VISIBLE);
//                }else{
//                    stageGrid.setVisibility(View.GONE);
//                }

            }
        });

        intentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StageActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
    }
}