package com.example.hw2_litalkhotyakov.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.hw2_litalkhotyakov.R;
import com.example.hw2_litalkhotyakov.fragments.callBacks.ButtonFragmentCallBack;
import com.example.hw2_litalkhotyakov.fragments.callBacks.ItemFragmentCallBack;
import com.example.hw2_litalkhotyakov.fragments.callBacks.OnItemClickedCallBack;
import com.example.hw2_litalkhotyakov.modules.GameRecord;

public class ScoreFragment extends Fragment {
    private MapsFragment mapsFragment;
    private ScoreFragment(){}

    public static ScoreFragment getInstance() {
        ScoreFragment fragment = new ScoreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_score, container, false);
        mapsFragment = MapsFragment.getInstance();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.frame1, ItemFragment.getInstance(itemFragmentCallBack)).commit();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.frame2, mapsFragment).commit();

        return view;
    }

    private ItemFragmentCallBack itemFragmentCallBack = new ItemFragmentCallBack() {
        @Override
        public void itemClicked(GameRecord gameRecord) {
            mapsFragment.setGameRecord(gameRecord);
        }
    };


}
