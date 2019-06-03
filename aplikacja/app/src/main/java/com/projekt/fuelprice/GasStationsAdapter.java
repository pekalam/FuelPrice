package com.projekt.fuelprice;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.projekt.fuelprice.viewmodels.GasStationsViewModel;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Comparator;


public class GasStationsAdapter extends RecyclerView.Adapter<GasStationsAdapter.ViewHolder> {

    private RecyclerView attachedRecycler;
    private GasStationListItemVM[] listItemVM = new GasStationListItemVM[0];
    private GasStationListItemVM[] initialOrderListItemVM = new GasStationListItemVM[0];
    private Context context;
    private GasStationsViewModel gasStationsViewModel;

    public GasStationsAdapter(Context context, GasStationsViewModel gasStationsViewModel, LifecycleOwner lifecycleOwner) {
        this.context = context;
        this.gasStationsViewModel = gasStationsViewModel;
        this.gasStationsViewModel.getSelectedGasStation().observe(lifecycleOwner, new Observer<GasStation>() {
            @Override
            public void onChanged(GasStation gasStation) {
                for(int i = 0; i < listItemVM.length; i++){
                    listItemVM[i].selected.set(false);
                    if(listItemVM[i].gasStation == gasStation){
                        if(attachedRecycler != null){
                            LinearLayoutManager manager = (LinearLayoutManager) attachedRecycler.getLayoutManager();
                            int recHeight = attachedRecycler.getHeight();
                            manager.scrollToPositionWithOffset(i, recHeight/2 - 90);
                            listItemVM[i].selected.set(true);
                        }
                    }
                }
            }
        });
        this.gasStationsViewModel.getSelectedFuelType().observe(lifecycleOwner, new Observer<GasStation.FuelType>() {
            @Override
            public void onChanged(GasStation.FuelType fuelType) {
                for(int i = 0; i < listItemVM.length; i++){
                    listItemVM[i].selected.set(false);
                }
            }
        });
    }

    private void sortListItems(){
        if(listItemVM.length > 0) {
            Arrays.sort(listItemVM, 0, listItemVM.length, new Comparator<GasStationListItemVM>() {
                @Override
                public int compare(GasStationListItemVM o1, GasStationListItemVM o2) {
                    if (o1.getSelectedFuelPrice() == o2.getSelectedFuelPrice()) {
                        if (o1.distance != -1 && o2.distance != -1) {
                            return o1.distance > o2.distance ? 1 : -1;
                        }
                    }
                    return o1.getSelectedFuelPrice() > o2.getSelectedFuelPrice() ? 1 : -1;
                }
            });
        }
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

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        this.attachedRecycler = recyclerView;
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        this.attachedRecycler = null;
        super.onDetachedFromRecyclerView(recyclerView);
    }

    public void setGasStations(GasStation[] gasStations){
        listItemVM = new GasStationListItemVM[gasStations.length];
        initialOrderListItemVM = new GasStationListItemVM[gasStations.length];
        for(int i = 0; i < gasStations.length; i++){
            listItemVM[i] = new GasStationListItemVM(gasStations[i]);
            initialOrderListItemVM[i] = listItemVM[i];
        }
        notifyDataSetChanged();
    }

    public void setGasStationsDistance(Double[] distances){
        if(listItemVM.length == 0 || distances.length == 0){
            return;
        }
        if(distances.length != listItemVM.length){
            throw new InvalidParameterException();
        }
        for(int i = 0; i < distances.length; i++){
            initialOrderListItemVM[i].distance = distances[i];
        }
    }

    public void setSelectedFuelType(GasStation.FuelType fuelType){
        if(listItemVM.length == 0){
            return;
        }
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
            /*binding.btn1.setOnTouchListener(new View.OnTouchListener() {
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
            );*/
        }

        public void bindGasStation(final GasStationListItemVM itemVM){
            binding.setVm(itemVM);
            binding.listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(GasStationsAdapter.this.attachedRecycler != null){
                        GasStationsAdapter.this.gasStationsViewModel.setSelectedGasStation(itemVM.gasStation);
                    }
                }
            });
            binding.btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GasStationsAdapter.this.gasStationsViewModel.navigateTo(itemVM.gasStation,
                                GasStationsAdapter.this.context);
                }
            });
        }
    }
}
