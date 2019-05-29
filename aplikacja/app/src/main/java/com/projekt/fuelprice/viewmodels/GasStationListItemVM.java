package com.projekt.fuelprice.viewmodels;

import android.database.Observable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.projekt.fuelprice.R;
import com.projekt.fuelprice.data.GasStation;

public class GasStationListItemVM{
    public GasStation gasStation;
    public double distance = -1;
    private GasStation.FuelType selectedFuelType = GasStation.FuelType.LPG;

    public GasStationListItemVM(GasStation gasStation){
        this.gasStation = gasStation;
    }

    public void setSelectedFuelPrice(GasStation.FuelType type){
        this.selectedFuelType = type;
    }

    public float getSelectedFuelPrice(){
        switch (selectedFuelType){
            case LPG:
                return gasStation.priceLPG;
            case t95:
                return gasStation.price95;
            case t98:
                return gasStation.price98;
            case ON:
                return gasStation.priceON;
        }
        return 0;
    }

    public GasStation.FuelType getSelectedFuelType(){
        return selectedFuelType;
    }

    public String formattedDistance(){
        if(distance == -1) {
            return "";
        }
        return String.format("%.2f km", distance);
    }

    public String formattedPrice(){
        return String.format("%.2f zł", getSelectedFuelPrice());
    }

    public boolean isNullItem(){
        if(gasStation == null)
            return true;
        return false;
    }
}
