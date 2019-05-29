package com.projekt.fuelprice;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.databinding.ListItemBinding;
import com.projekt.fuelprice.viewmodels.GasStationListItemVM;


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

    public void setGasStations(GasStationListItemVM[] gasStationListItemVMS){
        listItemVM = new GasStationListItemVM[gasStationListItemVMS.length + 1];
        System.arraycopy(gasStationListItemVMS, 0, listItemVM, 0, gasStationListItemVMS.length);
        //null item
        listItemVM[gasStationListItemVMS.length] = new GasStationListItemVM(null);
        notifyDataSetChanged();
    }

    public void setSelectedFuelPrice(GasStation.FuelType fuelType){
        this.fuelType = fuelType;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ListItemBinding binding;

        public ViewHolder(final ListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.btn1.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    switch(event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            binding.btn1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.arrow_disable));
                            return true;
                        case MotionEvent.ACTION_UP:
                            binding.btn1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.arrow_enable));
                           return true;
                        case MotionEvent.ACTION_OUTSIDE:
                            binding.btn1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.arrow_enable));
                           return true;
                    }
                    return false;
                   //
                }
            }
            );
        }

        public void bindGasStation(GasStationListItemVM itemVM){
            binding.setVm(itemVM);
        }
    }
}
