/*
 * Copyright 2019 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tensorflow.lite.examples.classification;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.Image.Plane;
import android.media.ImageReader;
import android.media.ImageReader.OnImageAvailableListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Trace;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.tensorflow.lite.examples.classification.env.ImageUtils;
import org.tensorflow.lite.examples.classification.env.Logger;
import org.tensorflow.lite.examples.classification.tflite.Classifier.Device;
import org.tensorflow.lite.examples.classification.tflite.Classifier.Model;
import org.tensorflow.lite.examples.classification.tflite.Classifier.Recognition;

public abstract class CameraActivity extends AppCompatActivity
        implements OnImageAvailableListener,
        Camera.PreviewCallback,
        View.OnClickListener,
        AdapterView.OnItemSelectedListener {

  final int datsbaseSize = 63;
  final String sock = "sock";
  int backMusic, sfx2;
  int[] answerList = new int[datsbaseSize]; //초기값 0, 배열 크기 60 + 1(newStage 때문) + 1(backMusic) + 1 (sfx)
  DBhelper dbHelper;


  private static final Logger LOGGER = new Logger();

  private static final int PERMISSIONS_REQUEST = 1;

  private static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
  protected int previewWidth = 0;
  protected int previewHeight = 0;
  private Handler handler;
  private HandlerThread handlerThread;
  private boolean useCamera2API;
  private boolean isProcessingFrame = false;
  private byte[][] yuvBytes = new byte[3][];
  private int[] rgbBytes = null;
  private int yRowStride;
  private Runnable postInferenceCallback;
  private Runnable imageConverter;

  private Model model = Model.QUANTIZED;
  private Device device = Device.CPU;
  private int numThreads = 2;

  int currentpoistion = 0; //현재 이미지 위치
  String level; //Stage level
  int count = 0;

  DataSet d;

  String[][] nameList1 = new String[][] {{"barber chair"}, {"wall clock", "analog clock"},
          {"rubber eraser"},{"mouse"}, {"ballpoint", "fountain pen"},
          {"pillow"}, {"running shoes", "sandal"},
          {"toilet tissue"}, {"vase"}, {"wallet"}};

  String[][] nameList2 = new String[][] {{"mixing bowl", "soup bowl"}, {"coffeepot", "water jug", "espresso maker"},
          {"cup", "coffee mug"}, {"frying pan"}, {"ladle"},
          {"plate", "petri dish"}, {"refrigerator"},
          {"spatula"}, {"toaster"}, {"wok"}};

  String[][] nameList3=new String[][] {{"banana"}, {"broccoli"},
          {"king crab", "American lobster", "Dungeness crab", "rock crab"},{"cucumber"}, {"lemon"},
          {"orange"}, {"pineapple"},
          {"pizza"}, {"shopping cart"}, {"strawberry"}};

  String[][] nameList4 = new String[][] {{"balloon"}, {"park bench"},
          {"trolleybus", "school bus", "minibus"}, {"admiral", "ringlet", "monarch", "cabbage butterfly", "sulphur butterfly", "lycaenid"}, {"egyptian cat", "tabby", "tiger cat", "Persian cat", "Siamese cat"},
          {"Chihuahua", "Japanese spaniel", "Maltese dog", "Pekinese", "Shih-Tzu", "Blenheim spaniel", "papillon", "toy terrier", "Rhodesian ridgeback", "Afghan hound", "basset", "beagle", "bloodhound",
                  "bluetick", "black-and-tan coonhound", "Walker hound", "English foxhound", "redbone", "borzoi", "Irish wolfhound",
                  "Italian greyhound", "whippet", "Ibizan hound", "Norwegian elkhound", "otterhound", "Saluki", "Scottish deerhound", "Weimaraner", "Staffordshire bullterrier", "American Staffordshire terrier",
                  "Bedlington terrier", "Border terrier", "Kerry blue terrier", "Irish terrier", "Norfolk terrier", "Norwich terrier", "Yorkshire terrier", "wire-haired fox terrier", "Lakeland terrier", "Sealyham terrier", "Airedale", "cairn", "Australian terrier", "Dandie Dinmont", "Boston bull", "miniature schnauzer", "giant schnauzer", "standard schnauzer", "Scotch terrier",
                  "Tibetan terrier", "silky terrier", "soft-coated wheaten terrier", "West Highland white terrier", "Lhasa", "flat-coated retriever", "curly-coated retriever", "golden retriever", "Labrador retriever", "Chesapeake Bay retriever", "German short-haired pointer", "vizsla", "English setter", "Irish setter", "Gordon setter", "Brittany spaniel", "clumber", "English springer", "Welsh springer spaniel",
                  "cocker spaniel", "Sussex spaniel", "Irish water spaniel", "kuvasz", "schipperke", "groenendael", "malinois", "briard", "kelpie", "komondor", "Old English sheepdog", "Shetland sheepdog", "collie", "Border collie", "Bouvier des Flandres", "Rottweiler", "German shepherd", "Doberman", "miniature pinscher",
                  "Greater Swiss Mountain dog", "Bernese mountain dog", "Appenzeller", "EntleBucher", "boxer", "bull mastiff", "Tibetan mastiff", "French bulldog", "Great Dane", "Saint Bernard", "Eskimo dog", "malamute", "Siberian husky", "dalmatian", "affenpinscher", "basenji", "pug", "Leonberg", "Newfoundland", "Great Pyrenees", "Samoyed", "Pomeranian",
                  "chow", "keeshond", "Brabancon griffon", "Pembroke", "Cardigan", "toy poodle", "miniature poodle", "standard poodle", "Mexican hairless", "African hunting dog"}, {"lacewing", "dragonfly", "damselfly"},
          {"street sign"}, {"swing"}, {"traffic light"}};

  String[][] nameList5=new String[][] {{"barery", "pretzel", "bagel", "French loaf", "tray"}, {"barber chair", "barbershop"},
          {"bookcase", "bookshop", "library"},{"butcher shop", "rotisserie"}, {"stretcher", "mousetrap", "birdhouse", "space shuttle", "missile"},
          {"home theater", "cinema", "theater curtain", "stage", "projector", "screen"}, {"greenhouse", "picket fence", "window screen"},
          {"grocery store", "confectionery"}, {"shoe shop"}, {"toyshop"}};

  String[][] nameList6 = new String[][] {{"brown bear"},{"Arabian camel"},{"African elephant"}
          ,{"goose"},{"hippopotamus"},{"lion"}
          ,{"orangutan","howler monkey", "squirrel monkey"},{"king penguin"},{"tiger"},{"zebra"}};

  private ArrayList<Integer> imageList;

  //20.03.26 수용 추가
  //대사 : ~~~를 찾아보자!, 아직이야?, 이건 아닌거 같아, 다른 걸 찾아볼까?, 이건 ~~~같아

  LinearLayout startLayout, quitLayout;
  ImageView character, newimage;
  ImageButton quitbtn;
  Button startBtn, quitYes, quitNo;
  TextView saying;
  String ask = "";
  String s = "";
  Boolean isQuit = false;
  Boolean isStart = false;
  Boolean isAnswer = false;
  Boolean isCorrect = false;
  Handler mHandler;
  Saying1 mSaying1;//시작 전 할 말
  Saying2 mSaying2;//찾으면서 할 말

  int i, j, k, correct, correctionConfirmor = 0;


  //20.03.26 수용 추가
  private View decorView;
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
  protected void onCreate(final Bundle savedInstanceState) {
    LOGGER.d("onCreate " + this);
    super.onCreate(null);

    dbHelper = new DBhelper(getApplicationContext(), "CLEARLIST.db", null, 1);
    answerList = dbHelper.getStageResult();
    /* 음악 환경 설정 부분 0 : 꺼짐, 1 : 켜짐 ///////////////////////////////////////////////// */
    backMusic = answerList[61];
    sfx2 = answerList[62];

    imageList = new ArrayList<Integer>();

    decorView = getWindow().getDecorView();
    uiOption = getWindow().getDecorView().getSystemUiVisibility();
    if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
      uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
      uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
    if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
      uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    setContentView(R.layout.activity_camera);

    //인플레이션으로 겹치는 레이아웃을 깐다
    LayoutInflater inflater = (LayoutInflater)getSystemService(

            Context.LAYOUT_INFLATER_SERVICE);

    LinearLayout camera_picure = (LinearLayout)inflater.inflate(R.layout.camera_picture, null);//처음에 찾아야될 물체의 사진을 보여주는 부분
    LinearLayout.LayoutParams paramlinear1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    addContentView(camera_picure, paramlinear1);

    LinearLayout camera_quit = (LinearLayout)inflater.inflate(R.layout.camera_quit, null);
    LinearLayout.LayoutParams paramlinear3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    addContentView(camera_quit, paramlinear3);//이 부분이 레이아웃을 겹치는 부분


    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);

    if (hasPermission()) {
      setFragment();
    }

    model = Model.valueOf("Quantized".toUpperCase());
    device = Device.valueOf("CPU");
    numThreads = 2;

    /////////////////////////////////////////////////////////수용-다이얼로그 부분 **인텐트 받는 부분 중복되게 가져옴
    character = findViewById(R.id.character);
    saying = findViewById(R.id.saying);
    startBtn = findViewById(R.id.startBtn);
    //playbtn.setVisibility(View.GONE);
    quitbtn = findViewById(R.id.quitbtn);
    startLayout = findViewById(R.id.startLayout);
    quitLayout = findViewById(R.id.quitLayout);
    newimage = findViewById(R.id.newimage);
    quitYes = findViewById(R.id.quitYes);
    quitNo = findViewById(R.id.quitNo);
    quitLayout.setVisibility(View.GONE);
    quitbtn.setClickable(false);

    isAnswer = false;
//    View dlgView = View.inflate(CameraActivity.this, R.layout.camera_picture, null);
//    AlertDialog.Builder dlg = new AlertDialog.Builder(CameraActivity.this);
//    ImageView pic = (ImageView) dlgView.findViewById(R.id.newimage);
//    dlg.setTitle("사진");

    d = new DataSet(imageList);

    Intent intent = getIntent();

    currentpoistion = intent.getIntExtra("iToC1",0);
    level = intent.getStringExtra("iToC2");

    d.initializeData(level);

//    if(level.equals("t") == true) {
//      newimage.setImageResource(t_imageList.get(currentpoistion));
//    }else {
//      newimage.setImageResource(imageList1.get(currentpoistion));
//    }

    if (level.equals("t") == true) {
      newimage.setImageResource(imageList.get(currentpoistion));
    }else if(level.equals("st1") == true){
      newimage.setImageResource(imageList.get(currentpoistion));
    } else if (level.equals("st2") == true) {
      newimage.setImageResource(imageList.get(currentpoistion));
    } else if (level.equals("st3") == true) {
      newimage.setImageResource(imageList.get(currentpoistion));
    } else if (level.equals("st4") == true) {
      newimage.setImageResource(imageList.get(currentpoistion));
    } else if (level.equals("st5") == true) {
      newimage.setImageResource(imageList.get(currentpoistion));
    } else if (level.equals("b") == true) {
      newimage.setImageResource(imageList.get(currentpoistion));
    }



    mSaying1 = new Saying1();
    mSaying1.setDaemon(true);
    mSaying1.start();

    startBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        startLayout.setVisibility(View.GONE);
        mSaying2 = new Saying2();
        mSaying2.setDaemon(true);
        mSaying2.start();
        quitbtn.setClickable(true);//뒤로가기 버튼 활성화
      }
    });

    quitbtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        quitLayout.setVisibility(View.VISIBLE);
        quitbtn.setClickable(false);
        startBtn.setClickable(false);
      }
    });
    quitYes.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        quitLayout.setVisibility(View.GONE);
        quitbtn.setClickable(true);
        isQuit = true;
        startBtn.setClickable(true);
        finish();
      }
    });
    quitNo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        quitLayout.setVisibility(View.GONE);
        quitbtn.setClickable(true);
        startBtn.setClickable(true);
      }
    });
  }

  protected int[] getRgbBytes() {
    imageConverter.run();
    return rgbBytes;
  }

  protected int getLuminanceStride() {
    return yRowStride;
  }

  protected byte[] getLuminance() {
    return yuvBytes[0];
  }

  /** Callback for android.hardware.Camera API */
  @Override
  public void onPreviewFrame(final byte[] bytes, final Camera camera) {
    if (isProcessingFrame) {
      LOGGER.w("Dropping frame!");
      return;
    }

    try {
      // Initialize the storage bitmaps once when the resolution is known.
      if (rgbBytes == null) {
        Camera.Size previewSize = camera.getParameters().getPreviewSize();
        previewHeight = previewSize.height;
        previewWidth = previewSize.width;
        rgbBytes = new int[previewWidth * previewHeight];
        onPreviewSizeChosen(new Size(previewSize.width, previewSize.height), 90);
      }
    } catch (final Exception e) {
      LOGGER.e(e, "Exception!");
      return;
    }

    isProcessingFrame = true;
    yuvBytes[0] = bytes;
    yRowStride = previewWidth;

    imageConverter =
            new Runnable() {
              @Override
              public void run() {
                ImageUtils.convertYUV420SPToARGB8888(bytes, previewWidth, previewHeight, rgbBytes);
              }
            };

    postInferenceCallback =
            new Runnable() {
              @Override
              public void run() {
                camera.addCallbackBuffer(bytes);
                isProcessingFrame = false;
              }
            };
    processImage();
  }

  /** Callback for Camera2 API */
  @Override
  public void onImageAvailable(final ImageReader reader) {
    // We need wait until we have some size from onPreviewSizeChosen
    if (previewWidth == 0 || previewHeight == 0) {
      return;
    }
    if (rgbBytes == null) {
      rgbBytes = new int[previewWidth * previewHeight];
    }
    try {
      final Image image = reader.acquireLatestImage();

      if (image == null) {
        return;
      }

      if (isProcessingFrame) {
        image.close();
        return;
      }
      isProcessingFrame = true;
      Trace.beginSection("imageAvailable");
      final Plane[] planes = image.getPlanes();
      fillBytes(planes, yuvBytes);
      yRowStride = planes[0].getRowStride();
      final int uvRowStride = planes[1].getRowStride();
      final int uvPixelStride = planes[1].getPixelStride();

      imageConverter =
              new Runnable() {
                @Override
                public void run() {
                  ImageUtils.convertYUV420ToARGB8888(
                          yuvBytes[0],
                          yuvBytes[1],
                          yuvBytes[2],
                          previewWidth,
                          previewHeight,
                          yRowStride,
                          uvRowStride,
                          uvPixelStride,
                          rgbBytes);
                }
              };

      postInferenceCallback =
              new Runnable() {
                @Override
                public void run() {
                  image.close();
                  isProcessingFrame = false;
                }
              };

      processImage();
    } catch (final Exception e) {
      LOGGER.e(e, "Exception!");
      Trace.endSection();
      return;
    }
    Trace.endSection();
  }

  @Override
  public synchronized void onStart() {
    LOGGER.d("onStart " + this);
    super.onStart();
  }

  @Override
  public synchronized void onResume() {
    LOGGER.d("onResume " + this);
    super.onResume();

    //h3_ImageViewMain으로 부터 온 인텐트
    Intent intent = getIntent();
    currentpoistion = intent.getIntExtra("iToC1",0);
    level = intent.getStringExtra("iToC2");

    handlerThread = new HandlerThread("inference");
    handlerThread.start();
    handler = new Handler(handlerThread.getLooper());
  }

  @Override
  public synchronized void onPause() {
    LOGGER.d("onPause " + this);

    handlerThread.quitSafely();
    try {
      handlerThread.join();
      handlerThread = null;
      handler = null;
    } catch (final InterruptedException e) {
      LOGGER.e(e, "Exception!");
    }
    super.onPause();
  }

  @Override
  public synchronized void onStop() {
    LOGGER.d("onStop " + this);

    isQuit = true;

    super.onStop();
  }

  @Override
  public synchronized void onDestroy() {
    LOGGER.d("onDestroy " + this);
    super.onDestroy();
  }

  protected synchronized void runInBackground(final Runnable r) {
    if (handler != null) {
      handler.post(r);
    }
  }

  @Override
  public void onRequestPermissionsResult(
          final int requestCode, final String[] permissions, final int[] grantResults) {
    if (requestCode == PERMISSIONS_REQUEST) {
      if (allPermissionsGranted(grantResults)) {
        setFragment();
      } else {
        requestPermission();
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

  private boolean hasPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      return checkSelfPermission(PERMISSION_CAMERA) == PackageManager.PERMISSION_GRANTED;
    } else {
      return true;
    }
  }

  private void requestPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (shouldShowRequestPermissionRationale(PERMISSION_CAMERA)) {
        Toast.makeText(
                CameraActivity.this,
                "Camera permission is required for this demo",
                Toast.LENGTH_LONG)
                .show();
      }
      requestPermissions(new String[] {PERMISSION_CAMERA}, PERMISSIONS_REQUEST);
    }
  }

  // Returns true if the device supports the required hardware level, or better.
  private boolean isHardwareLevelSupported(
          CameraCharacteristics characteristics, int requiredLevel) {
    int deviceLevel = characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
    if (deviceLevel == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY) {
      return requiredLevel == deviceLevel;
    }
    // deviceLevel is not LEGACY, can use numerical sort
    return requiredLevel <= deviceLevel;
  }

  private String chooseCamera() {
    final CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
    try {
      for (final String cameraId : manager.getCameraIdList()) {
        final CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);

        // We don't use a front facing camera in this sample.
        final Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
        if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT) {
          continue;
        }

        final StreamConfigurationMap map =
                characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

        if (map == null) {
          continue;
        }

        // Fallback to camera1 API for internal cameras that don't have full support.
        // This should help with legacy situations where using the camera2 API causes
        // distorted or otherwise broken previews.
        useCamera2API =
                (facing == CameraCharacteristics.LENS_FACING_EXTERNAL)
                        || isHardwareLevelSupported(
                        characteristics, CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_FULL);
        LOGGER.i("Camera API lv2?: %s", useCamera2API);
        return cameraId;
      }
    } catch (CameraAccessException e) {
      LOGGER.e(e, "Not allowed to access camera");
    }

    return null;
  }

  protected void setFragment() {
    String cameraId = chooseCamera();

    Fragment fragment;
    if (useCamera2API) {
      CameraConnectionFragment camera2Fragment =
              CameraConnectionFragment.newInstance(
                      new CameraConnectionFragment.ConnectionCallback() {
                        @Override
                        public void onPreviewSizeChosen(final Size size, final int rotation) {
                          previewHeight = size.getHeight();
                          previewWidth = size.getWidth();
                          CameraActivity.this.onPreviewSizeChosen(size, rotation);
                        }
                      },
                      this,
                      getLayoutId(),
                      getDesiredPreviewFrameSize());

      camera2Fragment.setCamera(cameraId);
      fragment = camera2Fragment;
    } else {//0427 수용  카메라 방향 돌리고 싶은데 모르겠다. 내 폰은 카메라2가 아니라 이쪽으로 넘어오는데 그것 때문인지
//      LOGGER.i("Camera API lv2?asdfasf");
//      camera.setDisplayOrientation(90);
//      lp.height = previewSurfaceHeight;
//      lp.width = (int) (previewSurfaceHeight / aspect);
      fragment =
              new LegacyCameraConnectionFragment(this, getLayoutId(), getDesiredPreviewFrameSize());
    }

    getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
  }

  protected void fillBytes(final Plane[] planes, final byte[][] yuvBytes) {
    // Because of the variable row stride it's not possible to know in
    // advance the actual necessary dimensions of the yuv planes.
    for (int i = 0; i < planes.length; ++i) {
      final ByteBuffer buffer = planes[i].getBuffer();
      if (yuvBytes[i] == null) {
        LOGGER.d("Initializing buffer %d at size %d", i, buffer.capacity());
        yuvBytes[i] = new byte[buffer.capacity()];
      }
      buffer.get(yuvBytes[i]);
    }
  }

  protected void readyForNextImage() {
    if (postInferenceCallback != null) {
      postInferenceCallback.run();
    }
  }

  protected int getScreenOrientation() {
    switch (getWindowManager().getDefaultDisplay().getRotation()) {
      case Surface.ROTATION_270:
        return 270;
      case Surface.ROTATION_180:
        return 180;
      case Surface.ROTATION_90:
        return 90;
      default:
        return 0;
    }
  }

  @UiThread
  protected void showResultsInBottomSheet(List<Recognition> results) {
    if (results != null && results.size() >= 3) {
      Recognition recognition = results.get(0);
      if (recognition != null) {
        if (recognition.getTitle() != null) Log.d("TrueResult","1번 이름 : " + recognition.getTitle());
        /*if (recognition.getConfidence() != null)
          Log.d("TrueResult","1번 확률 : " + String.format("%.2f", (100 * recognition.getConfidence())) + "%");*/
      }

      Recognition recognition1 = results.get(1);
      if (recognition1 != null) {
        if (recognition1.getTitle() != null) Log.d("TrueResult","2번 이름 : " + recognition1.getTitle());
        /*if (recognition1.getConfidence() != null)
          Log.d("TrueResult","2번 확률 : " + String.format("%.2f", (100 * recognition1.getConfidence())) + "%");*/
      }

      Recognition recognition2 = results.get(2);
      if (recognition2 != null) {
        if (recognition2.getTitle() != null) Log.d("TrueResult","3번 이름 : " + recognition2.getTitle());
        /*if (recognition2.getConfidence() != null)
          Log.d("TrueResult","3번 확률 : " + String.format("%.2f", (100 * recognition2.getConfidence())) + "%");*/
      }

//      if(level.equals("st1") == true) {
//        int length = nameList1[currentpoistion].length;
//        for (int i=0;i<length;i++) {
//          if (recognition.getTitle().equals(nameList1[currentpoistion][i])){
//            count += 1;
//          }
//        }
//      }
//      else if(level.equals("st2") == true) {
//        int length = nameList2[currentpoistion].length;
//        for (int i=0;i<length;i++) {
//          if (recognition.getTitle().equals(nameList2[currentpoistion][i])){
//            count += 1;
//          }
//        }
//      }
//      else if(level.equals("st3") == true) {
//        int length = nameList3[currentpoistion].length;
//        for (int i=0;i<length;i++) {
//          if (recognition.getTitle().equals(nameList3[currentpoistion][i])){
//            count += 1;
//          }
//        }
//      }
//      else if(level.equals("st4") == true) {
//        int length = nameList4[currentpoistion].length;
//        for (int i=0;i<length;i++) {
//          if (recognition.getTitle().equals(nameList4[currentpoistion][i])){
//            count += 1;
//          }
//        }
//      }
//      else if(level.equals("st5") == true) {
//        int length = nameList5[currentpoistion].length;
//        for (int i=0;i<length;i++) {
//          if (recognition.getTitle().equals(nameList5[currentpoistion][i])){
//            count += 1;
//          }
//        }
//      }
//      else if(level.equals("b") == true) {
//        int length = nameList6[currentpoistion].length;
//        for (int i=0;i<length;i++) {
//          if (recognition.getTitle().equals(nameList6[currentpoistion][i])){
//            count += 1;
//          }
//        }
//      }

      //수용-3초마다 출력하는 부분
      s = recognition.getTitle();
      if (count == 3){
        count = 0;
        d.imageList.clear();
        Intent go = new Intent(CameraActivity.this, h3_WriteAction.class);
        go.putExtra("cToW1",currentpoistion);
        go.putExtra("cToW2",level);
        startActivity(go);
        finish();
      }
    }
  }

  public class Saying1 extends Thread{//시작 전
    @Override
    public void run(){

      while (i<4 && !isQuit){
        (CameraActivity.this).runOnUiThread(new Runnable() {
          @Override
          public void run() {
            if(i == 0) {
              saying.setText("좋아!");
            }else if(i ==1){
              saying.setText("같이 찾아보자!");
            }else if(i ==2){
              saying.setText("어디에 있을까?");
            }else if(i ==3){
              saying.setVisibility(View.INVISIBLE);
              startLayout.setVisibility(View.VISIBLE);
              isStart = true;
            }
            i++;
          }
        });
        try {
          Thread.sleep(2500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

    }

  }
  public class Saying2 extends Thread{//시작 후

    @Override
    public void run(){
      while (j < 5 && !isAnswer && !isQuit){
        (CameraActivity.this).runOnUiThread(new Runnable() {
          @Override
          public void run() {


            if(!isInterrupted())Log.d("jdada", correct+"번");

            if(!isStart&&level.equals("st1") == true) {
              int length = nameList1[currentpoistion].length;
              for (int i=0;i<length;i++) {
                if (s.equals(nameList1[currentpoistion][i])){
                  if(correct==0){
                    saying.setVisibility(View.VISIBLE);
                    ask = s + "?";
                    saying.setText(ask);
                  }else if(correct==1){
                    //ask = s + "?";
                    saying.setText("어 맞는거 같은데?");
                  }else if(correct==2){
                    ask = s + " 정답!";
                    saying.setText(ask);
                    isAnswer = true;
                    //넘어가기
                    mHandler = new Handler();

                    Runnable mTask = new Runnable() {
                      @Override
                      public void run() {
                        correct = 0;
                        Intent go = new Intent(CameraActivity.this, h3_WriteAction.class);
                        go.putExtra("cToW1",currentpoistion);
                        go.putExtra("cToW2",level);
                        startActivity(go);
                        mSaying2.interrupt();
                        finish();
                      }
                    };
                    mHandler.postDelayed(mTask,1500);
                  }
                  correct++;
                  isCorrect = true;

                }
              }
            }
            // 200404 튜토리얼일 때
            else if(!isStart&&level.equals("t") == true) {
              int length = sock.length();
              for (int i=0;i<length;i++) {
                if (s.equals(sock)){
                  if(correct==0){
                    saying.setVisibility(View.VISIBLE);
                    ask = s + "?";
                    saying.setText(ask);
                  }else if(correct==1){
                    //ask = s + "?";
                    saying.setText("어 맞는거 같은데?");
                  }else if(correct==2){
                    ask = s + " 정답!";
                    saying.setText(ask);
                    isAnswer = true;
                    //넘어가기
                    mHandler = new Handler();

                    Runnable mTask = new Runnable() {
                      @Override
                      public void run() {
                        correct = 0;
                        Intent go = new Intent(CameraActivity.this, h3_WriteAction.class);
                        go.putExtra("cToW1",currentpoistion);
                        go.putExtra("cToW2",level);
                        startActivity(go);
                        mSaying2.interrupt();
                        finish();
                      }
                    };
                    mHandler.postDelayed(mTask,1500);
                  }
                  correct++;
                  isCorrect = true;

                }
              }
            }
            if(correct!=0 && correct==correctionConfirmor){
              correct = 0;
              correctionConfirmor = 0;
              isCorrect = false;
            }
            if(isCorrect){
              correctionConfirmor = correct;
              Log.d("jdada",correct + " sdf");

            }
            if(correct == 0){
              if(j == 0) {
                if(isStart) {
                  saying.setVisibility(View.VISIBLE);
                  saying.setText("보물찾기 시작!");
                  isStart = false;
                }else{
                  ask = s + "?";
                  saying.setText(ask);
                }
              }else if(j ==1){
                saying.setText("음...");
              }else if(j ==2){
                ask = s + "?";
                saying.setText(ask);
              }else if(j ==3){
                if(k==3){
                  saying.setText("너무 어려운가?");
                  k=0;
                }else if(k%2==0) {
                  saying.setText("으음...");
                } else{
                  saying.setText("어...");
                }
              }
            }
            j++;
          }
        });
        if(j == 4) {
          j = 0;
          k++;
        }
        try {
          Thread.sleep(1500);//말하기, 인식 속도
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  protected void showFrameInfo(String frameInfo) {
  }

  protected void showCropInfo(String cropInfo) {
  }

  protected void showCameraResolution(String cameraInfo) {
  }

  protected void showRotationInfo(String rotation) {

  }

  protected void showInference(String inferenceTime) {
  }

  protected Model getModel() {
    return model;
  }

  private void setModel(Model model) {
    if (this.model != model) {
      LOGGER.d("Updating  model: " + model);
      this.model = model;
      onInferenceConfigurationChanged();
    }
  }

  protected Device getDevice() {
    return device;
  }

  private void setDevice(Device device) {
    if (this.device != device) {
      LOGGER.d("Updating  device: " + device);
      this.device = device;
      final boolean threadsEnabled = device == Device.CPU;
      onInferenceConfigurationChanged();
    }
  }

  protected int getNumThreads() {
    return numThreads;
  }

  protected abstract void processImage();

  protected abstract void onPreviewSizeChosen(final Size size, final int rotation);

  protected abstract int getLayoutId();

  protected abstract Size getDesiredPreviewFrameSize();

  protected abstract void onInferenceConfigurationChanged();

  @Override
  public void onClick(View v) {
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {
    // Do nothing.
  }
}
