package com.projekt.fuelprice;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.databinding.ListItemBinding;


public class GasStationsAdapter extends RecyclerView.Adapter<GasStationsAdapter.ViewHolder> {

    private GasStationListItemVM[] listItemVM = new GasStationListItemVM[0];
    /*
    TODO: default fueltype
     */
    private GasStation.FuelType fuelType = GasStation.FuelType.t95;
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
        GasStationListItemVM itemVM = listItemVM[i];
        viewHolder.bindGasStation(itemVM);
    }

    @Override
    public int getItemCount() {
        return listItemVM.length;
    }

    public void setGasStations(GasStation[] gasStations){
        listItemVM = new GasStationListItemVM[gasStations.length];
        for (int i = 0; i < gasStations.length; i++) {
            listItemVM[i] = new GasStationListItemVM(gasStations[i]);
        }
        notifyDataSetChanged();
    }

    public void setSelectedFuelPrice(GasStation.FuelType fuelType){
        this.fuelType = fuelType;
        notifyDataSetChanged();
    }

    public class GasStationListItemVM{
        public GasStation gasStation;

        public GasStationListItemVM(GasStation gasStation){
            this.gasStation = gasStation;
        }

        public float getSelectedFuelPrice(){
            switch (GasStationsAdapter.this.fuelType){
                case LPG:
                    return gasStation.priceLPG;
                case t95:
                    return gasStation.price95;
                case t98:
                    return gasStation.price98;
            }
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ListItemBinding binding;

        public ViewHolder(ListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindGasStation(GasStationListItemVM itemVM){
            binding.setVm(itemVM);
        }
    }
}
