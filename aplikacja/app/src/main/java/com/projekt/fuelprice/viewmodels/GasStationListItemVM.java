package com.projekt.fuelprice.viewmodels;

import com.projekt.fuelprice.data.GasStation;

public class GasStationListItemVM{
    public GasStation gasStation;
    public double distance = -1;

    public GasStationListItemVM(GasStation gasStation){
        this.gasStation = gasStation;
    }

    public float getSelectedFuelPrice(){
        //TODO: odczyt z configuracji (cachowany)
        switch (GasStation.FuelType.t95){
            case LPG:
                return gasStation.priceLPG;
            case t95:
                return Math.round(gasStation.price95*100)/100f;
            case t98:
                return gasStation.price98;
        }
        return 0;
    }

    public String formattedDistance(){
        if(distance == -1) {
            return "";
        }
        return String.format("%.2f km", distance);
    }

    public boolean isNullItem(){
        if(gasStation == null)
            return true;
        return false;
    }
}
