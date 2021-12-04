package com.example.hw2_litalkhotyakov.fragments;

import static androidx.recyclerview.widget.RecyclerView.HORIZONTAL;
import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hw2_litalkhotyakov.R;
import com.example.hw2_litalkhotyakov.modules.GameRecord;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ItemFragment extends Fragment {
    private AppCompatActivity activity;
    private List<GameRecord> gameRecords;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    private ItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment getInstance() {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameRecords = new ArrayList<>();
        gameRecords.add(new GameRecord(new LatLng(1,1),200,new Date(),"lital"));
        gameRecords.add(new GameRecord(new LatLng(1,2),100,new Date(),"david"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyGamesRecordAdapter(gameRecords));
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), VERTICAL);
            recyclerView.addItemDecoration(dividerItemDecoration);

        }
        return view;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }
}