package com.projekt.fuelprice.services;

import android.content.Context;

import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.utils.FuelTypeSettings;

public class RealApplicationSettingsService implements ApplicationSettingsService {
    private Context context;

    public RealApplicationSettingsService(Context context){
        this.context = context;
    }

    @Override
    public GasStation.FuelType getSelectedFuelType() {
        return FuelTypeSettings.getSelectedFuelType(context);
    }

    @Override
    public void setSelectedFuelType(GasStation.FuelType type) {
        setSelectedFuelType(type);
    }
}
