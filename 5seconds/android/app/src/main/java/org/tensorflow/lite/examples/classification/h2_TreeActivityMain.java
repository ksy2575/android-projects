package org.tensorflow.lite.examples.classification;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class h2_TreeActivityMain extends AppCompatActivity {

    final int datsbaseSize = 63;
    int backMusic, sfx;

    ImageView Number1, Number2, Number3, Number4, Number5, bonus;
    Button set, back, ok;
    TextView conv, name;
    RelativeLayout sayingLayout;
    ImageView floatingCharacter;

    public final String PREFERENCE = "isFirst";
    DBhelper dbHelper;

    int[] answerList = new int[datsbaseSize]; //초기값 0, 배열 크기 60 + 1(newStage 때문) + 1(backMusic) + 1 (sfx)
    int[] countOfStage = new int[6]; //초기값 0, 배열 크기 6
    int[] standardOfStage = {2, 15, 24, 34, 44}; //2, 3, 4, 5, 엔딩 오픈 기준 개수
    int newStage = 0; //사용자가 참여할 수 있는 최고 단계

    private View 	decorView;
    private int	uiOption;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        // super.onWindowFocusChanged(hasFocus);

        if( hasFocus ) {
            decorView.setSystemUiVisibility( uiOption );
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h2_tree_activity);

        decorView = getWindow().getDecorView();
        uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        Number1 = findViewById(R.id.number1); // 레이아웃에 설정된 뷰 가져옴
        Number2 = findViewById(R.id.number2);
        Number3 = findViewById(R.id.number3);
        Number4 = findViewById(R.id.number4);
        Number5 = findViewById(R.id.number5);
        bonus = findViewById(R.id.bonus);

        set = findViewById(R.id.set);
        back = findViewById(R.id.back);

        DataSet d = new DataSet(h2_TreeActivityMain.this,set);
        d.setExit();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(h2_TreeActivityMain.this, h1_StartMain.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        dbHelper = new DBhelper(getApplicationContext(), "CLEARLIST.db", null, 1);

        //음악 재생은 이 안의 구문을 다 갖다 쓰면 됨
        DBhelper dbHelper = new DBhelper(getApplicationContext(), "CLEARLIST.db", null, 1);
        if (dbHelper.getStageResult()[61] == 1) {
            Intent intent = new Intent(getApplicationContext(), MusicService.class);
            intent.putExtra("index", 5);//몇번째 노래를 재생할 것인지 MusicService에 전달
            startService(intent);
        }
        //음악 재생은 이 안의 구문을 다 갖다 쓰면 됨

        //200404 튜토리얼 끝
        tutorial();

        answerList = dbHelper.getStageResult();
        int count = 0;
        Log.d("ananv", dbHelper.getResult());

        newStage = answerList[60];
        /* 음악 환경 설정 부분 0 : 꺼짐, 1 : 켜짐 ///////////////////////////////////////////////// */
        backMusic = answerList[61];
        sfx = answerList[62];

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 10; j++) {
                if (answerList[i * 10 + j] == 1) {
                    count++;
                }
            }
            countOfStage[i] = count;
            count = 0;
        }

        for (int k = 0; k < 6; k++) {
            Log.d("ooiel", k + "");
            Log.d("ooiel", countOfStage[k] + "");
        }

        //레벨 판단하는 부분

        //5단계 끝나고 엔딩하는 부분
        //엔딩 생기면 들어감

        //5단계 오픈, b 10까지
        if (countOfStage[3] >= 6) {
            if (countOfStage[0] + countOfStage[1] + countOfStage[2] + countOfStage[3] + countOfStage[5] >= standardOfStage[3]) {
                newStage = 5;
                dbHelper.update(70, newStage);
            }
        }
        //4단계 오픈, b 7까지
        else if (countOfStage[2] >= 6) {
            if (countOfStage[0] + countOfStage[1] + countOfStage[2] + countOfStage[5] >= standardOfStage[2]) {
                newStage = 4;
                dbHelper.update(70, newStage);
            }
        }
        //3단계 오픈, b 5까지
        else if (countOfStage[1] >= 6) {
            if (countOfStage[0] + countOfStage[1] + countOfStage[5] >= standardOfStage[1]) {
                newStage = 3;
                dbHelper.update(70, newStage);
            }
        }
        //2단계 오픈, b 2까지
        else if (countOfStage[0] >= 2) {
            if (countOfStage[0] + countOfStage[1] + countOfStage[5] >= standardOfStage[0]) {
                newStage = 2;
                dbHelper.update(70, newStage);
            }
        }
        //1단계 오픈
        else {
            Log.d("111w","22222222");
            newStage = 1;
            dbHelper.update(70, newStage);
        }
    }


    public void onClick(View view) {
        Intent intent = new Intent(this, StoryActivity.class);
//        Intent intent = new Intent(this, h3_ImageViewMain.class);
        int count = 0;
        switch (view.getId()) {   //switch문으로 어떤 버튼을 클릭했는지 판별한다
            case R.id.number1:
                if (newStage >= 1) {
                    //Number1.setImageResource(R.drawable.house);
                    intent.putExtra("tToS", "st1");
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.number2:
                if (newStage >= 2) {
                    //Number2.setImageResource(R.drawable.kitchen);
                    intent.putExtra("tToS", "st2");
                    startActivity(intent);
                    finish();
                } else Toast.makeText(getApplicationContext(), "접근권한없음", Toast.LENGTH_SHORT).show();

                break;
            case R.id.number3:
                if (newStage >= 3) {
                    //Number3.setImageResource(R.drawable.park);
                    intent.putExtra("tToS", "st3");
                    startActivity(intent);
                    finish();
                } else Toast.makeText(getApplicationContext(), "접근권한없음", Toast.LENGTH_SHORT).show();

                break;

            case R.id.number4:
                if (newStage >= 4) {
                    //Number4.setImageResource(R.drawable.park);
                    intent.putExtra("tToS", "st4");
                    startActivity(intent);
                    finish();
                } else Toast.makeText(getApplicationContext(), "접근권한없음", Toast.LENGTH_SHORT).show();

                break;

            case R.id.number5:
                if (newStage >= 5) {
                    //Number4.setImageResource(R.drawable.park);
                    intent.putExtra("tToS", "st5");
                    startActivity(intent);
                    finish();
                } else Toast.makeText(getApplicationContext(), "접근권한없음", Toast.LENGTH_SHORT).show();

                break;

            //보너스
            case R.id.bonus:
                if (newStage >= 2) {
                    //bonus.setImageResource(R.drawable.bonus);
                    intent.putExtra("tToS", "b");
                    startActivity(intent);
                    finish();
                } else Toast.makeText(getApplicationContext(), "접근권한없음", Toast.LENGTH_SHORT).show();

                break;
        }

    }

    public void tutorial() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout t_linear = (LinearLayout) inflater.inflate(R.layout.conversation, null);
        LinearLayout.LayoutParams t_paramlinear = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        SharedPreferences pref = getSharedPreferences("isFirst", Activity.MODE_PRIVATE);
        boolean isFirst = pref.getBoolean("isFirst", true);
        if (isFirst) { // 처음 접속했다면
            t_linear.setBackgroundColor(Color.parseColor("#99000000")); // 배경 어둡게
            addContentView(t_linear, t_paramlinear);//이 부분이 레이아웃을 겹치는 부분

            sayingLayout =  findViewById(R.id.sayingLayout);
            sayingLayout.setVisibility(View.VISIBLE);
            ok = findViewById(R.id.conv_ok);
            conv = findViewById(R.id.conv);
            name = findViewById(R.id.name);

            ok.setVisibility(View.VISIBLE);

            floatingCharacter = findViewById(R.id.floatingCharacter);
            floatingCharacter.setVisibility(View.VISIBLE);

            name.setText("앨리스");
            conv.setText("잘했어!\n자, 이제 진짜 시작해보자!");

            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isFirst", false);
            editor.commit();

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ViewGroup)t_linear.getParent()).removeView(t_linear);
                    t_linear.setBackgroundColor(Color.parseColor("#00ff0000"));
                }
            });
        }


    }
    @Override
    protected void onPause() {
        super.onPause();
        stopService(new Intent(h2_TreeActivityMain.this, MusicService.class));
    }
}
