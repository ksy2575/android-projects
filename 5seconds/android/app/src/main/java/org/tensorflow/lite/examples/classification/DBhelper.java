package org.tensorflow.lite.examples.classification;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBhelper extends SQLiteOpenHelper {
    final int datsbaseSize = 63;
    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 CLEARLIST, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
        db.execSQL("CREATE TABLE CLEARLIST (_id INTEGER, checking INTEGER);");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert() {

        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가

        db.execSQL("INSERT INTO CLEARLIST VALUES(10, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(11, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(12, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(13, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(14, 0);");

        db.execSQL("INSERT INTO CLEARLIST VALUES(15, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(16, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(17, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(18, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(19, 0);");

        db.execSQL("INSERT INTO CLEARLIST VALUES(20, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(21, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(22, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(23, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(24, 0);");

        db.execSQL("INSERT INTO CLEARLIST VALUES(25, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(26, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(27, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(28, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(29, 0);");

        db.execSQL("INSERT INTO CLEARLIST VALUES(30, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(31, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(32, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(33, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(34, 0);");

        db.execSQL("INSERT INTO CLEARLIST VALUES(35, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(36, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(37, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(38, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(39, 0);");

        db.execSQL("INSERT INTO CLEARLIST VALUES(40, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(41, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(42, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(43, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(44, 0);");

        db.execSQL("INSERT INTO CLEARLIST VALUES(45, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(46, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(47, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(48, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(49, 0);");

        db.execSQL("INSERT INTO CLEARLIST VALUES(50, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(51, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(52, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(53, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(54, 0);");

        db.execSQL("INSERT INTO CLEARLIST VALUES(55, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(56, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(57, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(58, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(59, 0);");

        db.execSQL("INSERT INTO CLEARLIST VALUES(60, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(61, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(62, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(63, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(64, 0);");

        db.execSQL("INSERT INTO CLEARLIST VALUES(65, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(66, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(67, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(68, 0);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(69, 0);");

        db.execSQL("INSERT INTO CLEARLIST VALUES(70, 1);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(71, 1);");
        db.execSQL("INSERT INTO CLEARLIST VALUES(72, 1);");


        db.close();
    }

    public void update(int id, int checking) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE CLEARLIST SET checking=" + checking + " WHERE _id ='" + id + "';");
        db.close();
    }

    /*
    public void delete(String item) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM CLEARLIST WHERE item='" + item + "';");
        db.close();
    }
     */

    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM CLEARLIST", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0)
                    + " 성공여부 : "
                    + cursor.getString(1)
                    + "\n";
        }

        db.close();
        return result;
    }

    public int[] getStageResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        int[] result = new int[datsbaseSize];

        Cursor cursor = db.rawQuery("SELECT * FROM CLEARLIST", null);
        int i = 0;
        while (cursor.moveToNext()) {
            result[i] = cursor.getInt(1);
            i++;
        }
        db.close();
        return result;
    }

    public void allClear() {

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM CLEARLIST");

        db.close();
    }
}




