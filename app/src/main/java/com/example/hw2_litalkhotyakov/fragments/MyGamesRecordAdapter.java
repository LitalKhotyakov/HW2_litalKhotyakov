package com.example.hw2_litalkhotyakov.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hw2_litalkhotyakov.databinding.FragmentItemBinding;
import com.example.hw2_litalkhotyakov.fragments.callBacks.OnItemClickedCallBack;
import com.example.hw2_litalkhotyakov.modules.GameRecord;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class MyGamesRecordAdapter extends RecyclerView.Adapter<MyGamesRecordAdapter.ViewHolder> {

    private final List<GameRecord> gameRecords;
    private OnItemClickedCallBack onItemClickedCallBack;

    public MyGamesRecordAdapter(List<GameRecord> gameRecords) {
        this.gameRecords = gameRecords;
    }

    public void setOnItemClickedCallBack(OnItemClickedCallBack onItemClickedCallBack) {
        this.onItemClickedCallBack = onItemClickedCallBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.gameRecord = gameRecords.get(position);
        holder.name.setText("Name: " + gameRecords.get(position).getName());
        holder.date.setText("Date: " +gameRecords.get(position).getDate().toString());
        holder.score.setText("Score: " +gameRecords.get(position).getScore() + "");
    }

    @Override
    public int getItemCount() {
        return gameRecords.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final MaterialTextView name;
        public final MaterialTextView date;
        public final MaterialTextView score;
        public GameRecord gameRecord;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            name = binding.itemName;
            date = binding.itemDate;
            score = binding.itemScore;
            binding.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickedCallBack != null) {
                        onItemClickedCallBack.onItemClicked(gameRecord);
                    }
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + date.getText() + "'";
        }
    }
}