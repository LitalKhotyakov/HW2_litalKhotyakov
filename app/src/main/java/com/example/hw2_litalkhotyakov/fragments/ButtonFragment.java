package com.example.hw2_litalkhotyakov.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hw2_litalkhotyakov.R;
import com.example.hw2_litalkhotyakov.fragments.callBacks.ButtonFragmentCallBack;
import com.google.android.material.button.MaterialButton;


public class ButtonFragment extends Fragment {
    private MaterialButton fragment1_BTN_easy;
    private MaterialButton fragment1_BTN_hard;
    private MaterialButton fragment1_BTN_Top10;
    private AppCompatActivity activity;
    private ButtonFragmentCallBack buttonFragmentCallBack;

    private ButtonFragment(){}

    public static ButtonFragment getInstance(AppCompatActivity appCompatActivity,ButtonFragmentCallBack buttonFragmentCallBack){
        ButtonFragment buttonFragment = new ButtonFragment();
        buttonFragment.setActivity(appCompatActivity);
        buttonFragment.buttonFragmentCallBack = buttonFragmentCallBack;
        Bundle args = new Bundle();
        buttonFragment.setArguments(args);
        return buttonFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_button, container, false);
        findViews(view);
        initViews();


        return view;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    private void initViews() {
        fragment1_BTN_easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonFragmentCallBack != null) {
                    buttonFragmentCallBack.easyButtonClicked();
                }
            }
        });

        fragment1_BTN_hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonFragmentCallBack != null) {
                    buttonFragmentCallBack.hardButtonClicked();
                }
            }
        });

        fragment1_BTN_Top10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonFragmentCallBack != null) {
                    buttonFragmentCallBack.top10ButtonClicked();
                }
            }
        });
    }

    private void findViews(View view) {
        fragment1_BTN_easy = view.findViewById(R.id.fragment1_BTN_easy);
        fragment1_BTN_hard = view.findViewById(R.id.fragment1_BTN_hard);
        fragment1_BTN_Top10 = view.findViewById(R.id.fragment1_BTN_Top10);
    }
}