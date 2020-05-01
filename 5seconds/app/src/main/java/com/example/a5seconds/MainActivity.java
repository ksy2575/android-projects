package com.example.a5seconds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button start;
    Button setting;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.Button_Start);
        setting = findViewById(R.id.Button_Setting);





    }


    @Override
    protected  void onResume(){
        super.onResume();

        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.fiveseconds);
        mediaPlayer.setLooping(true);
        mediaPlayer.start(); // prepare(); 나 create() 를 호출할 필요 없음

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, GameActivity.class);

                startActivity(intent);

            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "setting", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected  void onStop(){
        super.onStop();

        mediaPlayer.release();
        mediaPlayer = null;

    }

}
