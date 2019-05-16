package com.projekt.fuelprice;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.databinding.ListItemBinding;


public class GasStationsAdapter extends RecyclerView.Adapter<GasStationsAdapter.ViewHolder> {

    private GasStation[] gasStations = new GasStation[0];
    private Context context;

    public GasStationsAdapter(Context context) {
        this.context = context;
    }

    

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        GasStation station = gasStations[i];
        viewHolder.bindGasStation(station);
    }

    @Override
    public int getItemCount() {
        return gasStations.length;
    }

    public void setGasStations(GasStation[] gasStations){
        this.gasStations = gasStations;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ListItemBinding binding;

        public ViewHolder(ListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindGasStation(GasStation station){
            binding.tekst1.setText(String.valueOf(station.price95));
            binding.tekst2.setText("x");
            switch (station.brandName){
                case "Shell":
                    binding.image1.setImageDrawable(context.getResources().getDrawable(R.drawable.shell));
                    break;
                case "Orlen":
                    binding.image1.setImageDrawable(context.getResources().getDrawable(R.drawable.orlen));
                    break;
            }
        }
    }
}
