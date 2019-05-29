package com.projekt.fuelprice;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.databinding.ListItemBinding;
import com.projekt.fuelprice.viewmodels.GasStationListItemVM;

import java.util.Arrays;
import java.util.Comparator;


public class GasStationsAdapter extends RecyclerView.Adapter<GasStationsAdapter.ViewHolder> {

    private GasStationListItemVM[] listItemVM = new GasStationListItemVM[0];
    private Context context;

    public GasStationsAdapter(Context context) {
        this.context = context;
    }


    private void sortListItems(){
        Arrays.sort(listItemVM, 0, listItemVM.length-1, new Comparator<GasStationListItemVM>() {
            @Override
            public int compare(GasStationListItemVM o1, GasStationListItemVM o2) {
                if(o1.getSelectedFuelPrice() == o2.getSelectedFuelPrice()){
                    if(o1.distance != -1 && o2.distance != -1){
                        return o1.distance > o2.distance ? 1 : -1;
                    }
                }
                return o1.getSelectedFuelPrice() > o2.getSelectedFuelPrice() ? 1 : -1;
            }
        });
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
        sortListItems();
        //null item
        listItemVM[gasStationListItemVMS.length] = new GasStationListItemVM(null);
        notifyDataSetChanged();
    }

    public void setSelectedFuelPrice(GasStation.FuelType fuelType){
        for(GasStationListItemVM vm : listItemVM){
            vm.setSelectedFuelPrice(fuelType);
        }
        sortListItems();
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

                }
            }
            );
        }

        public void bindGasStation(final GasStationListItemVM itemVM){
            binding.setVm(itemVM);
            binding.btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + itemVM.gasStation.lat + "," + itemVM.gasStation.lon);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    context.startActivity(mapIntent);
                }
            });
        }
    }
}
