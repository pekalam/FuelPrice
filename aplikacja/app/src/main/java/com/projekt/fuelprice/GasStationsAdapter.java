package com.projekt.fuelprice;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


public class GasStationsAdapter extends RecyclerView.Adapter {

    /*
    https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#10
     */

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class GasStationViewHolder extends RecyclerView.ViewHolder{

        public GasStationViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
