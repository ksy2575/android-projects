package com.example.rhythmnoodle;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import me.relex.circleindicator.CircleIndicator;

public class ChefSelect extends AppCompatActivity {
    static int ChefNo=7;
    static int pos = 10;
    FragmentPagerAdapter adapterViewPager;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_chef);

        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        mediaPlayer = MediaPlayer.create(this, R.raw.select);
        mediaPlayer.setLooping(true);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(vpPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
    }
    @Override
    protected void onRestart() {
        super.onRestart();

        mediaPlayer = MediaPlayer.create(this, R.raw.select);
        mediaPlayer.setLooping(true);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }
        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
        // Returns the fragment to display for that page
        @Override

        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    pos = 1;
                    fragment = FirstFragment.newInstance(0, "Page # 1");
                    break;
                case 1:
                    pos = 2;
                    fragment = SecondFragment.newInstance(1, "Page # 2");
                    break;
                case 2:
                    pos = 3;
                    fragment =  ThirdFragment.newInstance(2, "Page # 3");
                    break;
                default:
                    break;
            }

            return fragment;
        }
        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }
    }

}