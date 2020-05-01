package com.example.rhythmnoodle;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import static com.example.rhythmnoodle.ChefSelect.ChefNo;


public class ThirdFragment  extends Fragment {

    // newInstance constructor for creating fragment with arguments
    public static ThirdFragment newInstance(int page, String title) {
        ThirdFragment fragment = new ThirdFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragment.setArguments(args);
        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rock, container, false);
        ImageButton button = (ImageButton) view.findViewById(R.id.rock_chef);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.rock_chef:
                        ChefNo = 3;
                        Intent intent1 = new Intent(getActivity(), RhythmNoodleActivity.class);
                        startActivity(intent1);
                        break;
                }
            }
        });
        return view;
    }
}