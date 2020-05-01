package org.tensorflow.lite.examples.classification;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StoryActivity extends AppCompatActivity {

    RelativeLayout sayingLayout;
    TextView conv, name;
    ImageButton quitbtn;
    ImageView leftcharacter, rightcharacter, floatingCharacter;
    Handler mHandler;
    int sayingLength = 0;
    int i = 0;
    String sayingSub = "";
    String username = "d";
    String level = "";
    String[] sayingList;
    boolean isFin = false;

    //    String[] sayingList1 = new String[] {"안녕!", "내 이름은 앨리스야!",
//            "나는 저 바다 밑에서 왔어", "내 모험을 도와주지 않을래?", "앗 나두나두!", "나도 같이 갈래!"};
    String[] t_sayingList = new String[]{"아 오늘 할 일도 다 했다!!",
            "계속 농사를 짓다보면 언젠간 하늘을 날 수 있을까?",
            "농사도 좋지만 승무원이 돼서 하늘을 날고 싶은데..", "깜짝이야!!언제부터 있었어?", "넌 이름이 뭐야?",
            "name(이)구나! 저기 날 도와주지 않을래?", "승무원이 되려면 영어를 공부해야하거든! \n혼자 공부하기 외로웠는데 같이 공부하자",
            "그럼 공부 방법을 알려줄게!"};
    String[] sayingList1 = new String[]{"들어와 우리집이야! 여기 들어온 친구는 네가 처음이야",
            "그래도 우리집에 있을 건 다 있다고!", "그럼 내가 소개시켜줄게"};//스토리마다 여기에 기입
    String[] sayingList2 = new String[]{"우리집에 온 손님이니까 내가 맛있는거 해줄게!", "너 (먹이) 좋아해?",
            "뭐 이걸 싫어한다고? 이게 얼마나 맛있는데!", "흠..알았어. 입맛은 다 다른거니까..",
            "조금만 기대려. 내가 맛있는 음식 만들어줄게!"};
    String[] sayingList3 = new String[]{"우리 오늘 저녁은 파티하자! 나 친구랑 파티 해보고싶었어.",
            "파티에는 맛있는 음식이 빠질 수 없지", "우리 얼른 마트가서 맛있는 음식 많이 사오자!"};
    String[] sayingList4 = new String[]{"아 친구랑 놀러나오니까 너무 좋다!", "어 저거 혹시..쳇바퀴!!!",
            "우리 저 공원 가자!! 얼른 따라와!"};
    String[] sayingList5 = new String[]{"와 도시로 나오니까 정말 좋다.", "여긴 건물이 정말 많구나.",
            "저기 어두운 곳은 뭐 하는 곳이야? 저기로 가보자!"};
    String[] sayingList6 = new String[]{"나 친구랑 꼭 가보고 싶었던 곳이 있는데..같이 가줄래?",
            "여기 가면 공부 진행이 더 잘 될거야!"};

    private View decorView;
    private int uiOption;

    final String PREFERENCE = "isFirst";

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
        setContentView(R.layout.story_activity);

        //인플레이션으로 겹치는 레이아웃을 깐다
        LayoutInflater inflater = (LayoutInflater) getSystemService(

                Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout conversation = (LinearLayout) inflater.inflate(R.layout.conversation, null);
        LinearLayout.LayoutParams paramlinear = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addContentView(conversation, paramlinear);//이 부분이 레이아웃을 겹치는 부분

        decorView = getWindow().getDecorView();
        uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;


        sayingLayout = findViewById(R.id.sayingLayout);
        conv = findViewById(R.id.conv);
        name = findViewById(R.id.name);
        quitbtn = findViewById(R.id.quitbtn);
        leftcharacter = findViewById(R.id.leftcharacter);
        rightcharacter = findViewById(R.id.rightcharacter);

        Intent h2intent = getIntent();
        if (h2intent.getStringExtra("tToS") != null) {
            level = h2intent.getStringExtra("tToS");
        }

        // 200420 출력할 문장들
        if (level.equals("st1") == true) sayingList = sayingList1;
        else if (level.equals("st2") == true) sayingList = sayingList2;
        else if (level.equals("st3") == true) sayingList = sayingList3;
        else if (level.equals("st4") == true) sayingList = sayingList4;
        else if (level.equals("st5") == true) sayingList = sayingList5;
        else if (level.equals("b") == true) sayingList = sayingList6;
        else sayingList = t_sayingList;

        SharedPreferences pref = getSharedPreferences(PREFERENCE, Activity.MODE_PRIVATE);
        boolean isFirst = pref.getBoolean(PREFERENCE, true);

        if(isFirst) {
            init();
            quitbtn.setVisibility(View.INVISIBLE);
        }

        if (checkStory(level) == true) {
            Intent intent = new Intent(this, h3_ImageViewMain.class);
            intent.putExtra("sToI", level);
            startActivity(intent);
            finish();
        }

        sayingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i == 5 && isFirst) saveName();
                else if (i < sayingLength) {
                    startSaying(sayingList, i);
                    i++;
                } else {
                    //대화 종료
                    Log.d("lengdd", "대화 종료");
                    endActivity();
                }

            }
        });

        quitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endActivity();
            }
        });

        mHandler = new Handler();

        Runnable mTask = new Runnable() {
            @Override
            public void run() {

                Log.d("lengdd", " " + sayingLength);

                sayingLayout.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);
                sayingLength = sayingList.length;
                startSaying(sayingList, i);

                name.setText("케빈");
                leftcharacter.setVisibility(View.VISIBLE);

                i++;
            }
        };
        mHandler.postDelayed(mTask, 1500);

    }

    private void endActivity() {
        // 200404 튜토리얼 화면 가는 부분
        SharedPreferences pref = getSharedPreferences(PREFERENCE, Activity.MODE_PRIVATE);
        boolean isFirst = pref.getBoolean(PREFERENCE, true);

        Intent intent = new Intent(StoryActivity.this, h3_ImageViewMain.class);
        if(!isFirst){
            intent.putExtra("sToI", level);

            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(level, true);
            editor.commit();
        }
        startActivity(intent);
        finish();
    }

    void startSaying(String[] sayinglist, int saying) {
        //기본적인 코딩
        Log.d("lengdd", sayinglist[saying]);

        conv.setText(sayinglist[saying]);
        //더 어려운 코딩

        for (int j = 1; j <= sayinglist[saying].length(); j++) {

            //conv.setText(sayinglist[saying]);
            sayingSub = sayinglist[saying].substring(0, j);
            mHandler = new Handler();

            Runnable mTask = new Runnable() {
                @Override
                public void run() {

                    Log.d("lengdd", sayingSub);
                }
            };
            mHandler.postDelayed(mTask, 1500);
        }

    }

    public void saveName() {
        sayingLayout.setClickable(false);
        LayoutInflater inflater = (LayoutInflater) getSystemService(

                Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout inputName = (LinearLayout) inflater.inflate(R.layout.username, null);
        LinearLayout.LayoutParams paramlinear = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addContentView(inputName, paramlinear);//이 부분이 레이아웃을 겹치는 부분

        EditText editText = (EditText) findViewById(R.id.username);
        Button save = findViewById(R.id.save_name);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strSaveData = editText.getText().toString();

                if (strSaveData.equals("") == true) {
                    Toast.makeText(getApplicationContext(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences pref = getSharedPreferences("isFirst", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("UserName", strSaveData);
                    editor.commit();

                    username = pref.getString("UserName", strSaveData);
                    ((ViewGroup) inputName.getParent()).removeView(inputName);

                    sayingLayout.setClickable(true);

//                sayingList1[i].replace("name",username);
                    t_sayingList[i] = username + "(이)구나! 저기 날 도와주지 않을래?";
                    Log.d("asdfasdfasdf", "" + t_sayingList[i]);
                    startSaying(t_sayingList, i);
                    i++;

                    Log.d("asdfasdfasdf", "name = " + username);
                    Log.d("asdfasdfasdf", "data = " + strSaveData);

                    quitbtn.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void init(){
        SharedPreferences pref = getSharedPreferences("isFirst", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("st1", false);
        editor.putBoolean("st2", false);
        editor.putBoolean("st3", false);
        editor.putBoolean("st4", false);
        editor.putBoolean("st5", false);
        editor.putBoolean("b", false);
        editor.commit();
    }
    public boolean checkStory(String level) {
        SharedPreferences pref = getSharedPreferences("isFirst", Activity.MODE_PRIVATE);
        isFin = pref.getBoolean(level, false);

        return isFin;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //음악 재생은 이 안의 구문을 다 갖다 쓰면 됨
        DBhelper dbHelper = new DBhelper(getApplicationContext(), "CLEARLIST.db", null, 1);
        if (dbHelper.getStageResult()[61] == 1) {
            Intent intent = new Intent(getApplicationContext(), MusicService.class);
            intent.putExtra("index", 2);//몇번째 노래를 재생할 것인지 MusicService에 전달
            startService(intent);
        }
        //음악 재생은 이 안의 구문을 다 갖다 쓰면 됨
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopService(new Intent(StoryActivity.this, MusicService.class));
    }
}