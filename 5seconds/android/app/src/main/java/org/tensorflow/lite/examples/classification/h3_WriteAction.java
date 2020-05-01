package org.tensorflow.lite.examples.classification;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.ArrayList;
import java.util.List;

public class h3_WriteAction extends AppCompatActivity {

    final int datsbaseSize = 63;
    int backMusic, sfx;
    int[] answerList = new int[datsbaseSize]; //초기값 0, 배열 크기 60 + 1(newStage 때문) + 1(backMusic) + 1 (sfx)

    Button help, back, check, eraser, ok, imageOk, exitButton, shadowBtn;
    TextView conv, find, name;
    RelativeLayout sayingLayout;
    ImageView floatingCharacter;

    int count = 0;
    int currentpoistion = 0;
    int wrongCount = 0;
    String level = "";
    String question = "";
    String answer = "";
    boolean isFirst;
    boolean isShadow = true;
    ArrayList<Integer> imageList;

    String[] nameList1 = new String[]{"chair", "clock", "eraser",
            "mouse", "pen", "shoes",
            "pillow", "tissue", "vase", "wallet"};

    String[] nameList2 = new String[]{"bowl", "coffeepot", "cup",
            "frying pan", "ladle", "plate",
            "refrigerator", "spatula", "toaster", "wok"};

    String[] nameList3 = new String[]{"banana", "broccoli", "crab",
            "cucumber", "lemon", "orange",
            "pineapple", "pizza", "cart", "strawberry"};

    String[] nameList4 = new String[]{"balloon", "bench", "bus",
            "butterfly", "cat", "dog",
            "dragonfly", "street sign", "swing", "traffic light"};

    String[] nameList5 = new String[]{"bakery", "barber shop", "book shop",
            "butcher shop", "church", "cinema",
            "greenhouse", "grocery store", "shoe shop", "toy shop"};

    String[] nameList6 = new String[]{"bear", "camel", "elephant"
            , "goose", "hippopotamus", "lion"
            , "monkey", "penguin", "tiger", "zebra"};

    String[] koreanList1 = new String[]{"의자", "시계", "지우개",
            "마우스", "펜", "신발",
            "배게", "휴지", "꽃병", "지갑"};

    String[] koreanList2 = new String[]{"보울", "커피포트", "컵",
            "프라이팬", "국자", "접시",
            "냉장고", "뒤집개", "토스트기", "웍"};

    String[] koreanList3 = new String[]{"바나나", "브로콜리", "게",
            "오이", "레몬", "오렌지",
            "파인애플", "피자", "카트", "딸기"};

    String[] koreanList4 = new String[]{"풍선", "벤치", "버스",
            "나비", "고양이", "강아지",
            "잠자리", "표지판", "그네", "신호등"};

    String[] koreanList5 = new String[]{"빵집", "미용실", "서점",
            "정육점", "교회", "영화관",
            "비닐하우스", "마트", "신발가게", "장난감가게"};

    String[] koreanList6 = new String[]{"곰", "낙타", "코끼리"
            , "거위", "하마", "사자"
            , "원숭이", "펭귄", "호랑이", "얼룩말"};

    DBhelper dbHelper;
    CustomView mview;
    CustomView mview2;

    MyView myView;
    ImageView image, newimage;
    LinearLayout pictureLayout;

    //Allocate a new Bitmap at 400 x 400 px
    Bitmap bitmap = Bitmap.createBitmap(2000, 400, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);

    private View 	decorView;
    private int	uiOption;

    DataSet d;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        // super.onWindowFocusChanged(hasFocus);

        if( hasFocus ) {
            decorView.setSystemUiVisibility( uiOption );
        }
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h3_action);

        help = findViewById(R.id.help);
        back = findViewById(R.id.endingOfGame);
        check = findViewById(R.id.check);
        eraser = findViewById(R.id.eraser);

        image = (ImageView) findViewById(R.id.captureResult);
        myView = (MyView) findViewById(R.id.myView);

        decorView = getWindow().getDecorView();
        uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        LayoutInflater inflater = (LayoutInflater)getSystemService(

                Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout camera_picure = (LinearLayout)inflater.inflate(R.layout.camera_picture, null);//처음에 찾아야될 물체의 사진을 보여주는 부분
        LinearLayout.LayoutParams paramlinear1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addContentView(camera_picure, paramlinear1);

        newimage = findViewById(R.id.newimage);
        pictureLayout =  findViewById(R.id.startLayout);
        imageOk =  findViewById(R.id.startBtn);
        find =  findViewById(R.id.find);
        exitButton = (Button) findViewById(R.id.set);
        shadowBtn = (Button) findViewById(R.id.shadowBtn);

        dbHelper = new DBhelper(getApplicationContext(), "CLEARLIST.db", null, 1);
        answerList = dbHelper.getStageResult();

        /* 음악 환경 설정 부분 0 : 꺼짐, 1 : 켜짐 ///////////////////////////////////////////////// */
        backMusic = answerList[61];
        sfx = answerList[62];

        imageList = new ArrayList<Integer>();
        d = new DataSet(h3_WriteAction.this, imageList, exitButton);
        d.setExit();

        imageOk.setText("녜에엥");//textList도 만들어서 넣기

    }

    public void onResume() {
        super.onResume();

        //음악 재생은 이 안의 구문을 다 갖다 쓰면 됨
        DBhelper dbHelper = new DBhelper(getApplicationContext(), "CLEARLIST.db", null, 1);
        if(dbHelper.getStageResult()[61] == 1){
            Intent intent=  new Intent(getApplicationContext(), MusicService.class);
            intent.putExtra("index",4);//몇번째 노래를 재생할 것인지 MusicService에 전달
            startService(intent);
        }
        //음악 재생은 이 안의 구문을 다 갖다 쓰면 됨

        Intent i = getIntent();
        currentpoistion = i.getIntExtra("cToW1", 0);
        level = i.getStringExtra("cToW2");


       /* if (level.equals("st1") == true) {
            suggetion.setText(nameList[currentpoistion]);
        }*/

        FrameLayout fl = (FrameLayout) findViewById(R.id.suggetion);
        FrameLayout fl_2 = (FrameLayout) findViewById(R.id.drawingArea);
        FrameLayout fl_3 = (FrameLayout) findViewById(R.id.shadowView);

        ViewGroup.LayoutParams params = fl_2.getLayoutParams();

        params.height = 400;
        params.width = 2200;

        fl_2.setLayoutParams(params);

        // 200404 튜토리얼 대화창
        // 200412 sayingLayout 등 추가
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout t_linear = (LinearLayout) inflater.inflate(R.layout.conversation, null);
        LinearLayout.LayoutParams t_paramlinear = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        SharedPreferences pref = getSharedPreferences("isFirst", Activity.MODE_PRIVATE);
        isFirst = pref.getBoolean("isFirst", true);
        if (isFirst) {
            level = "t";
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

            //인플레이터 뒷부분 다 클릭 안되게 하는 부분
//            back.setClickable(false);
//            back.setVisibility(View.GONE);
            name.setText("앨리스");
            conv.setText("이제 글씨를 써보자!");

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (count == 0) {
                        conv.setText("다 쓰고 확인을 누르면 돼");
                    } else {
                        ((ViewGroup) t_linear.getParent()).removeView(t_linear);
                        t_linear.setBackgroundColor(Color.parseColor("#00ff0000"));
                    }
                    count++;
                }
            });
        }

        d.initializeData(level);
        newimage.setImageResource(imageList.get(currentpoistion));

        // 200412 mview = new CustomView 구문 추가
        if (level.equals("t") == true) {
            mview = new CustomView(fl.getContext(), "sock", false);
            mview2 = new CustomView(fl.getContext(), "sock", true);
            question = "sock";
            find.setText("양말");
        }else if(level.equals("st1") == true){
            mview = new CustomView(fl.getContext(), nameList1[currentpoistion], false);
            mview2 = new CustomView(fl.getContext(), nameList1[currentpoistion], true);
            question = nameList1[currentpoistion];
            find.setText(koreanList1[currentpoistion]);
        } else if (level.equals("st2") == true) {
            mview = new CustomView(fl.getContext(), nameList2[currentpoistion], false);
            mview2 = new CustomView(fl.getContext(), nameList2[currentpoistion], true);
            question = nameList2[currentpoistion];
            find.setText(koreanList2[currentpoistion]);
        } else if (level.equals("st3") == true) {
            mview = new CustomView(fl.getContext(), nameList3[currentpoistion], false);
            mview2 = new CustomView(fl.getContext(), nameList3[currentpoistion], true);
            question = nameList3[currentpoistion];
            find.setText(koreanList3[currentpoistion]);
        } else if (level.equals("st4") == true) {
            mview = new CustomView(fl.getContext(), nameList4[currentpoistion], false);
            mview2 = new CustomView(fl.getContext(), nameList4[currentpoistion], true);
            question = nameList4[currentpoistion];
            find.setText(koreanList4[currentpoistion]);
        } else if (level.equals("st5") == true) {
            mview = new CustomView(fl.getContext(), nameList5[currentpoistion], false);
            mview2 = new CustomView(fl.getContext(), nameList5[currentpoistion], true);
            question = nameList5[currentpoistion];
            find.setText(koreanList5[currentpoistion]);
        } else if (level.equals("b") == true) {
            mview = new CustomView(fl.getContext(), nameList6[currentpoistion], false);
            mview2 = new CustomView(fl.getContext(), nameList6[currentpoistion], true);
            question = nameList6[currentpoistion];
            find.setText(koreanList6[currentpoistion]);
        }

        fl.addView(mview);
        fl_3.addView(mview2);
//        fl_3.setVisibility(View.INVISIBLE);

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                View dlgView = View.inflate(h3_WriteAction.this, R.layout.camera_picture, null);
//                AlertDialog.Builder dlg = new AlertDialog.Builder(h3_WriteAction.this);
//                ImageView pic = (ImageView) dlgView.findViewById(R.id.newimage);
//                dlg.setTitle("사진");
//
//                if (level.equals("st1") == true) {
//                    pic.setImageResource(imageList.get(currentpoistion));
//                } else if (level.equals("st2") == true) {
//                    pic.setImageResource(imageList.get(currentpoistion));
//                } else if (level.equals("st3") == true) {
//                    pic.setImageResource(imageList.get(currentpoistion));
//                } else if (level.equals("st4") == true) {
//                    pic.setImageResource(imageList.get(currentpoistion));
//                } else if (level.equals("st5") == true) {
//                    pic.setImageResource(imageList.get(currentpoistion));
//                } else if (level.equals("b") == true) {
//                    pic.setImageResource(imageList.get(currentpoistion));
//                }
//                // 200404 튜토리얼 추가
//                else if (level.equals("t") == true) {
//                    pic.setImageResource(imageList.get(currentpoistion));
//                }
//                dlg.setView(dlgView);
//                dlg.setNegativeButton("취소", null);
//                dlg.show();
                pictureLayout.setVisibility(View.VISIBLE);
            }
        });

        imageOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureLayout.setVisibility(View.GONE);
            }
        });

        //image.setImageResource();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.imageList.clear();
                Intent intent = new Intent(h3_WriteAction.this, h3_ImageViewMain.class);//이미지 뷰로 가는 부분
                intent.putExtra("wToI1", level);
                intent.putExtra("wToI4", currentpoistion);

                startActivity(intent);
                finish();
            }
        });

        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //myView.isDraw = false;
                myView.path.reset();
                myView.invalidate();
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myView.draw(canvas);
                image.setImageBitmap(bitmap);
                canvas.drawPath(myView.path, myView.paint);

                //canvas.drawCircle(900,200,90, myView.paint);

                myView.invalidate();

                runTextRecognition(bitmap);



            }
        });
        shadowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isShadow){
                    isShadow = false;
                    fl_3.setVisibility(View.INVISIBLE);
                    shadowBtn.setBackgroundResource(R.drawable.lightbulboff);
                }else{
                    isShadow = true;
                    fl_3.setVisibility(View.VISIBLE);
                    shadowBtn.setBackgroundResource(R.drawable.lightbulb);
                }
            }
        });
    }

    private void runTextRecognition(Bitmap bitmap) {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionTextRecognizer recognizer = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();

        recognizer.processImage(image)
                .addOnSuccessListener(
                        new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText texts) {
                                processTextRecognitionResult(texts);
                                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), texts.getText(), Toast.LENGTH_SHORT).show();

                                answer = texts.getText();

                                checkAnswer(answer);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                            }
                        });
    }

    private void checkAnswer(String answer) {

        answer = answer.toLowerCase();
        if (answer.equals(question)) {

            Toast.makeText(getApplicationContext(), "정답!", Toast.LENGTH_SHORT).show();

            if (level.equals("st1")) {
                String tmp = "1" + currentpoistion;
                int newtmp = Integer.parseInt(tmp);
                Log.d("iioi", newtmp + "");
                dbHelper.update(newtmp, 1);
            } else if (level.equals("st2")) {
                String tmp = "2" + currentpoistion;
                int newtmp = Integer.parseInt(tmp);
                Log.d("iioi", newtmp + "");
                dbHelper.update(newtmp, 1);
            } else if (level.equals("st3")) {
                String tmp = "3" + currentpoistion;
                int newtmp = Integer.parseInt(tmp);
                Log.d("iioi", newtmp + "");
                dbHelper.update(newtmp, 1);
            } else if (level.equals("st4")) {
                String tmp = "4" + currentpoistion;
                int newtmp = Integer.parseInt(tmp);
                Log.d("iioi", newtmp + "");
                dbHelper.update(newtmp, 1);
            } else if (level.equals("st5")) {
                String tmp = "5" + currentpoistion;
                int newtmp = Integer.parseInt(tmp);
                Log.d("iioi", newtmp + "");
                dbHelper.update(newtmp, 1);
            } else if (level.equals("b")) {
                String tmp = "6" + currentpoistion;
                int newtmp = Integer.parseInt(tmp);
                Log.d("iioi", newtmp + "");
                dbHelper.update(newtmp, 1);
            }

            d.imageList.clear();

            Intent intent2 = new Intent(h3_WriteAction.this, h3_ImageViewMain.class);//이미지 뷰로 가는 부분
            intent2.putExtra("wToI2", currentpoistion);
            intent2.putExtra("wToI3", level);


            myView.path2.addCircle(900, 200, 130, Path.Direction.CW);
            canvas.drawPath(myView.path2, myView.paint2);
            myView.invalidate();

            bitmap.eraseColor(Color.WHITE);


            // 2초간 멈추게 하고싶다면
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    //myView.startAnimation(  AnimationUtils.loadAnimation( h3_WriteAction.this,  R.anim.fadein ) );
                    //맞았다면
                    if(isFirst){
                        d.imageList.clear();
                        Intent t_intent = new Intent(h3_WriteAction.this, h2_TreeActivityMain.class);//이미지 뷰로 가는 부분
                        startActivity(t_intent);
                        finish();
                    }else {
                        startActivity(intent2);
                        finish();
                    }
                }
            }, 2000);  // 2000은 2초를 의미합니다.


        } else {
            Toast.makeText(getApplicationContext(), "다시 해볼까요?", Toast.LENGTH_SHORT).show();
            wrongCount++;
            if(wrongCount == 3){
                Toast.makeText(getApplicationContext(), "가이드를 따라 천천히 써봐!", Toast.LENGTH_SHORT).show();
            }else if(wrongCount == 10000){
                wrongCount = 10;
            }
        }

    }

    private void processTextRecognitionResult(FirebaseVisionText texts) {
        List<FirebaseVisionText.TextBlock> blocks = texts.getTextBlocks();
        if (blocks.size() == 0) {
            Toast.makeText(getApplicationContext(), "no text", Toast.LENGTH_SHORT).show();
            return;
        }
//        mGraphicOverlay.clear();
        for (int i = 0; i < blocks.size(); i++) {
            List<FirebaseVisionText.Line> lines = blocks.get(i).getLines();
            for (int j = 0; j < lines.size(); j++) {
                List<FirebaseVisionText.Element> elements = lines.get(j).getElements();
                for (int k = 0; k < elements.size(); k++) {
                    //Graphic textGraphic = new TextGraphic(mGraphicOverlay, elements.get(k));
                    //imageList.add(textGraphic);
                }
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        stopService(new Intent(h3_WriteAction.this, MusicService.class));
    }
//    private int exifOrientationToDegrees(int exifOrientation) {//가로화면에서 찍었을 시 찍힌 사진을 원래대로 돌려주는 함수
//        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
//            return 90;
//        }else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180){
//            return 180;
//        }else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270){
//            return 270;
//        }
//        return 0;
//    }

//    private Bitmap rotate(Bitmap bitmap, float degree) {//가로화면에서 찍었을 시 찍힌 사진을 원래대로 돌려주는 함수
//        Matrix matrix = new Matrix();
//        matrix.postRotate(degree);
//        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//    }
}
