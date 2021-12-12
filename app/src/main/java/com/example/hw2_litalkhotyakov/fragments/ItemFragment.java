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

import com.example.hw2_litalkhotyakov.MySharedPreferences;
import com.example.hw2_litalkhotyakov.R;
import com.example.hw2_litalkhotyakov.fragments.callBacks.ButtonFragmentCallBack;
import com.example.hw2_litalkhotyakov.fragments.callBacks.ItemFragmentCallBack;
import com.example.hw2_litalkhotyakov.fragments.callBacks.OnItemClickedCallBack;
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
    private ItemFragmentCallBack itemFragmentCallBack;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    private ItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment getInstance(ItemFragmentCallBack itemFragmentCallBack) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        fragment.itemFragmentCallBack = itemFragmentCallBack;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameRecords = MySharedPreferences.getMe().getGameRecord();
        if (gameRecords == null){
            gameRecords = new ArrayList<>();
            gameRecords.add(new GameRecord(new LatLng(1,1),200,new Date(),"lital"));
            gameRecords.add(new GameRecord(new LatLng(2,2),100,new Date(),"david"));
            gameRecords.add(new GameRecord(new LatLng(3,3),320,new Date(),"mor"));
        }
        //sort
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
            MyGamesRecordAdapter myGamesRecordAdapter = new MyGamesRecordAdapter(gameRecords);
            myGamesRecordAdapter.setOnItemClickedCallBack(onItemClickedCallBack);
            recyclerView.setAdapter(myGamesRecordAdapter);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), VERTICAL);
            recyclerView.addItemDecoration(dividerItemDecoration);
        }
        return view;
    }

    private OnItemClickedCallBack onItemClickedCallBack = new OnItemClickedCallBack() {
        @Override
        public void onItemClicked(GameRecord gameRecord) {
            itemFragmentCallBack.itemClicked(gameRecord);
        }
    };



    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }
}