package com.example.rhythmnoodle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartNoodleActivity extends AppCompatActivity {


    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_noodle);

        fa = this;
    }

    public void onClick(View view){

        switch (view.getId()){

            case R.id.makeNoodle:
                Intent intent1 = new Intent(this,ChefSelect.class);
                startActivity(intent1);
                break;

            case R.id.noodleLibrary:
                Intent intent2 = new Intent(this,NoodleLibraryActivity.class);
                startActivity(intent2);
                break;
        }
    }
}