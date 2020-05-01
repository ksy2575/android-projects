package org.tensorflow.lite.examples.classification;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class h1_StartMain extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST = 1;
    private static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;

    final int datsbaseSize = 63;
    int backMusic, sfx;
    int[] answerList = new int[datsbaseSize]; //초기값 0, 배열 크기 60 + 1(newStage 때문) + 1(backMusic) + 1 (sfx)

    Boolean isSetting = false;
    Boolean isMusicOn = true;
    LinearLayout settingLayout, clearLayout;
    Button start, settingBtn, clearData, clearYes, clearNo, sc, exitButton;
    CheckBox setMusic, setSfx;
    RadioButton bm1, bm2;
    RadioGroup bm;
    RadioButton se1, se2;
    RadioGroup se;

    DBhelper dbHelper;

    View decorView;
    int uiOption;

    final String PREFERENCE = "isFirst";

    class StartListenerClass implements View.OnClickListener {
        public void onClick(View view) {
//            mediaPlayer.release();
//            mediaPlayer = null;

            // 200404 튜토리얼 화면 가는 부분
            SharedPreferences pref = getSharedPreferences(PREFERENCE, Activity.MODE_PRIVATE);
            boolean isFirst = pref.getBoolean(PREFERENCE, true);
//            SharedPreferences.Editor editor = pref.edit();
//            editor.putBoolean("isFirst", true);
//            editor.commit();
            if (isFirst) { // 처음 접속했다면
                Intent intent = new Intent(h1_StartMain.this, StoryActivity.class); // 튜토리얼 화면으로
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(h1_StartMain.this, h2_TreeActivityMain.class);
                startActivity(intent);
                finish();
            }
        }
    }

    class SettingListenerClass implements View.OnClickListener {
        public void onClick(View view) {
            if (isSetting) {
                //화면 없애는 부분
                settingLayout.setVisibility(View.GONE);
                start.setClickable(true);
                isSetting = false;
            } else {
                isSetting = true;
                start.setClickable(false);
                settingLayout.setVisibility(View.VISIBLE);
            }
        }
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        // super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            decorView.setSystemUiVisibility(uiOption);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("masadf", "create ");
        setContentView(R.layout.h1_start_activity);

        dbHelper = new DBhelper(getApplicationContext(), "CLEARLIST.db", null, 1);
        answerList = dbHelper.getStageResult();
        /* 음악 환경 설정 부분 0 : 꺼짐, 1 : 켜짐 ///////////////////////////////////////////////// */
        backMusic = answerList[61];
        sfx = answerList[62];

        Log.d("masadf", " " + dbHelper.getStageResult()[61]);
        //인플레이션으로 겹치는 레이아웃을 깐다
        LayoutInflater inflater = (LayoutInflater) getSystemService(

                Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout setting = (LinearLayout) inflater.inflate(R.layout.h1_setting, null);
        LinearLayout.LayoutParams paramlinear = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addContentView(setting, paramlinear);//이 부분이 레이아웃을 겹치는 부분

        LinearLayout clear = (LinearLayout) inflater.inflate(R.layout.h1_clear_data, null);
        LinearLayout.LayoutParams paramlinear2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addContentView(clear, paramlinear2);//이 부분이 레이아웃을 겹치는 부분


        decorView = getWindow().getDecorView();
        uiOption = getWindow().getDecorView().getSystemUiVisibility();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        start = findViewById(R.id.Button_Start);
        StartListenerClass startbuttonListener = new StartListenerClass();
        start.setOnClickListener(startbuttonListener);

        settingBtn = findViewById(R.id.Button_Setting);
        SettingListenerClass setbuttonListener = new SettingListenerClass();
        settingBtn.setOnClickListener(setbuttonListener);
        settingLayout = findViewById(R.id.settingLayout);
        clearLayout = findViewById(R.id.clearLayout);

        //setMusic = findViewById(R.id.setMusic);
        //setSfx = findViewById(R.id.setSfx);

        clearData = findViewById(R.id.clearData);
        clearYes = findViewById(R.id.clearYes);
        clearNo = findViewById(R.id.clearNo);

        sc = findViewById(R.id.settingCheck);

        //라디오 그룹 설정
        bm = (RadioGroup) findViewById(R.id.backMusic);
        bm.setOnCheckedChangeListener(radioGroupButtonChangeListener1);

        //라디오 버튼 설정
        bm1 = (RadioButton) findViewById(R.id.bm1);
        bm2 = (RadioButton) findViewById(R.id.bm2);

        //라디오 그룹 설정
        se = (RadioGroup) findViewById(R.id.soundEffect);
        se.setOnCheckedChangeListener(radioGroupButtonChangeListener2);

        //라디오 버튼 설정
        se1 = (RadioButton) findViewById(R.id.se1);
        se2 = (RadioButton) findViewById(R.id.se2);

        exitButton = (Button) findViewById(R.id.Button_End);

/* if문 안으로 들어갈려면, 카메라 권한이 허용되지 않아야 함
            hasPermission()이 0 false 여야함
         */
        if (!hasPermission()) {
            requestPermission();
        }

//        if(backMusic == 1){//db상으로 음악이 켜져있다면
//            Log.d("masadf", " " + answerList[61]);
//            bm.check(R.id.bm1);
////            Intent intent=  new Intent(getApplicationContext(), MusicService.class);
////            intent.putExtra("index",1);//몇번째 노래를 재생할 것인지 MusicService에 전달
////            startService(intent);
////            dbHelper.update(71, 1);
////            Log.d("MusicService", "bm1");
//            isMusicOn = true;
//        }else{
//            Log.d("masadf", " " + answerList[61]);
//            bm.check(R.id.bm2);
//            isMusicOn = false;
//        }

        if(sfx == 1){//db상으로 효과음이 켜져있다면
            Log.d("masadf", " " + answerList[62]);
            se.check(R.id.se1);
            //효과음
        }else{
            Log.d("masadf", " " + answerList[62]);
            se.check(R.id.se2);
        }

        DataSet d = new DataSet(h1_StartMain.this,exitButton);
        d.setExit();

        //데이터 베이스 관련 버튼
        clearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearLayout.setVisibility(View.VISIBLE);

                bm1.setClickable(false);
                bm2.setClickable(false);
                se1.setClickable(false);
                se2.setClickable(false);

                settingBtn.setClickable(false);
            }
        });
        clearYes.setOnClickListener(new View.OnClickListener() {        //세이브 데이터 초기화 코딩하기
            @Override
            public void onClick(View view) {
                clearLayout.setVisibility(View.GONE);
                bm1.setClickable(true);
                bm2.setClickable(true);
                se1.setClickable(true);
                se2.setClickable(true);
                settingBtn.setClickable(true);

                dbHelper.allClear();
                dbHelper.insert();

            }
        });
        clearNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearLayout.setVisibility(View.GONE);
                bm1.setClickable(true);
                bm2.setClickable(true);
                se1.setClickable(true);
                se2.setClickable(true);
                settingBtn.setClickable(true);
            }
        });

        sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingLayout.setVisibility(View.GONE);
                start.setClickable(true);
                isSetting = false;
            }
        });
    }

    //배경음악 라디오 그룹 클릭 리스너
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener1 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            Log.d("MusicService", "11111");
            if (i == R.id.bm1&&!isMusicOn) {
                Intent intent=  new Intent(getApplicationContext(), MusicService.class);
                intent.putExtra("index",1);//몇번째 노래를 재생할 것인지 MusicService에 전달
                startService(intent);
                dbHelper.update(71, 1);
                backMusic = 1;
                Log.d("MusicService", "bm1");
                isMusicOn = true;
            } else if (i == R.id.bm2&&isMusicOn) {
                stopService(new Intent(h1_StartMain.this, MusicService.class));
                dbHelper.update(71, 0);
                backMusic = 0;
                Log.d("MusicService", "bm2");
                isMusicOn = false;
            }
        }
    };

    //효과음 라디오 그룹 클릭 리스너
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener2 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if (i == R.id.se1) {
                //dbHelper.update(72, 1);
            } else if (i == R.id.se2) {
                //dbHelper.update(72, 0);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("masadf", "resume ");
//        if(backMusic == 1&& !isMusicOn){//db상으로 음악이 켜져있다면
//            Log.d("masadf", " " + answerList[61]);
//            bm.check(R.id.bm1);
//            Intent intent=  new Intent(getApplicationContext(), MusicService.class);
//            intent.putExtra("index",1);//몇번째 노래를 재생할 것인지 MusicService에 전달
//            startService(intent);
//            dbHelper.update(71, 1);
//            Log.d("MusicService", "bm1");
//            isMusicOn = true;
//        }else{
//            Log.d("masadf", " " + answerList[61]);
//            bm.check(R.id.bm2);
//            isMusicOn = false;
//        }
        if(backMusic == 1){//db상으로 음악이 켜져있다면
            Log.d("masadf", " " + answerList[61]);
            bm.check(R.id.bm2);
            bm.check(R.id.bm1);
//            Intent intent=  new Intent(getApplicationContext(), MusicService.class);
//            intent.putExtra("index",1);//몇번째 노래를 재생할 것인지 MusicService에 전달
//            startService(intent);
//            dbHelper.update(71, 1);
//            Log.d("MusicService", "bm1");
            isMusicOn = true;
        }else{
            Log.d("masadf", " " + answerList[61]);
            bm.check(R.id.bm2);
            isMusicOn = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("masadf", "pause ");
        stopService(new Intent(h1_StartMain.this, MusicService.class));
        isMusicOn = false;
    }
    public boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(PERMISSION_CAMERA) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
        //android.permission.CAMERA

    }

    public void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            /*
            if (shouldShowRequestPermissionRationale(PERMISSION_CAMERA)) {

            }*/
            requestPermissions(new String[] {PERMISSION_CAMERA}, PERMISSIONS_REQUEST);
        }
    }
    @Override
    public void onRequestPermissionsResult(
            final int requestCode, final String[] permissions, final int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST) {
            if (allPermissionsGranted(grantResults)) {
                //setFragment();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(h1_StartMain.this, R.style.MyAlertDialogStyle);
                builder.setMessage("카메라 권한을 거부하시면 서비스를 이용할 수 없습니다.\n\n권한 설정 방법\n[설정 -> 애플리케이션 -> Kevin의 영어공부 -> 권한 -> 카메라 ON]");
                builder.setCancelable(false)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                finishAffinity();
                                System.runFinalization();
                                System.exit(0);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }
    private static boolean allPermissionsGranted(final int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
