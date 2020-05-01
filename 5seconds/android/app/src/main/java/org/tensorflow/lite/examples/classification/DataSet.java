package org.tensorflow.lite.examples.classification;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
public class DataSet extends AppCompatActivity {

    ArrayList<Integer> imageList;
    Button exit;
    Context c;

    public DataSet(Context c, Button exit) {
        this.exit = exit;
        this.c = c;
    }

    public DataSet(ArrayList<Integer> imageList) {
        this.imageList = imageList;
    }

    public DataSet(Context c, ArrayList<Integer> imageList, Button exit) {
        this.imageList = imageList;
        this.exit = exit;
        this.c = c;
    }

    public void setExit() {

        //닫기 버튼
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(c, R.style.MyAlertDialogStyle);
                builder.setMessage("Are you sure you want to exit?");
                builder.setTitle("Notification")
                        .setCancelable(false)
                        .setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                finishAffinity();
                                System.runFinalization();
                                System.exit(0);
                                Log.d("eere","안됨??");
                            }
                        })
                        .setNegativeButton("STAY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void initializeData(String level) {
        if (level.equals("st1")) {
            imageList.add(R.drawable.st1_chair); //barber chair
            imageList.add(R.drawable.st1_clock); //wall clock, analog clock -> clock
            imageList.add(R.drawable.st1_eraser); //rubber eraser
            imageList.add(R.drawable.st1_mouse); //mouse
            imageList.add(R.drawable.st1_pen); // ballpoint, fountain pen

            imageList.add(R.drawable.st1_pillow); //pillow
            imageList.add(R.drawable.st1_shoes); // running shoes, sandal -> shoes
            imageList.add(R.drawable.st1_tissue); //toilet tissue -> tissue
            imageList.add(R.drawable.st1_vase); //vase
            imageList.add(R.drawable.st1_wallet); //wallet
        } else if (level.equals("st2")) {
            imageList.add(R.drawable.st2_bowl);
            imageList.add(R.drawable.st2_coffeepot);
            imageList.add(R.drawable.st2_cup);
            imageList.add(R.drawable.st2_fryingpan);
            imageList.add(R.drawable.st2_ladle);

            imageList.add(R.drawable.st2_plate);
            imageList.add(R.drawable.st2_refrigerator);
            imageList.add(R.drawable.st2_spatula);
            imageList.add(R.drawable.st2_toaster);
            imageList.add(R.drawable.st2_wok);
        } else if (level.equals("st3")) {
            imageList.add(R.drawable.st3_banana);
            imageList.add(R.drawable.st3_brocoli);
            imageList.add(R.drawable.st3_crab);
            imageList.add(R.drawable.st3_cucumber);
            imageList.add(R.drawable.st3_lemon);

            imageList.add(R.drawable.st3_orange);
            imageList.add(R.drawable.st3_pineapple);
            imageList.add(R.drawable.st3_pizza);
            imageList.add(R.drawable.st3_shoppingcart);
            imageList.add(R.drawable.st3_strawberry);
        } else if (level.equals("st4")) {
            imageList.add(R.drawable.st4_baloon);
            imageList.add(R.drawable.st4_bench);
            imageList.add(R.drawable.st4_bus);
            imageList.add(R.drawable.st4_butterfly);
            imageList.add(R.drawable.st4_cat);

            imageList.add(R.drawable.st4_dog);
            imageList.add(R.drawable.st4_dragonfly);
            imageList.add(R.drawable.st4_streetsign);
            imageList.add(R.drawable.st4_swing);
            imageList.add(R.drawable.st4_trafficlight);
        } else if (level.equals("st5")) {
            imageList.add(R.drawable.st5_bakery);
            imageList.add(R.drawable.st5_barbershop);
            imageList.add(R.drawable.st5_bookshop);
            imageList.add(R.drawable.st5_butchershop);
            imageList.add(R.drawable.st5_church);

            imageList.add(R.drawable.st5_cinema);
            imageList.add(R.drawable.st5_greenhouse);
            imageList.add(R.drawable.st5_grocerystore);
            imageList.add(R.drawable.st5_shoeshop);
            imageList.add(R.drawable.st5_toyshop);
        } else if (level.equals("b")) {
            imageList.add(R.drawable.bonus_bear);
            imageList.add(R.drawable.bonus_camel);
            imageList.add(R.drawable.bonus_elephant);
            imageList.add(R.drawable.bonus_goose);
            imageList.add(R.drawable.bonus_hippo);

            imageList.add(R.drawable.bonus_lion);
            imageList.add(R.drawable.bonus_monkey);
            imageList.add(R.drawable.bonus_penguin);
            imageList.add(R.drawable.bonus_tiger);
            imageList.add(R.drawable.bonus_zebra);
        }
        else if (level.equals("t")) {
            imageList.add(R.drawable.t_sock1);
            imageList.add(R.drawable.t_sock2);
        }
    }
}
