package com.example.nonogram_201222;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
//                Toast.makeText(getApplicationContext(), "asdf"+i, Toast.LENGTH_LONG).show();

//                Cursor cursor = (Cursor)adapterView.getItemAtPosition(i);
//                Log.d("asdf", cursor.getString(0));

                Log.d("asdf", "asdf" + String.valueOf(adapterView.getItemAtPosition(i)));
            }
        });

        intentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StageActivity.this, GameActivity.class);
                intent.putExtra("i", 1);
                intent.putExtra("j", 1);
                startActivity(intent);
            }
        });
    }
    public static Intent getNameIntent(Context context, String firstName){
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra("i", firstName);
        return intent;
    }
    void goToGame(int i, int j){
        Log.d("asdf", "asdf");
        Intent intent = new Intent(StageActivity.this, GameActivity.class);
        intent.putExtra("i", i);
        intent.putExtra("j", j);
        startActivity(intent);
    }
}