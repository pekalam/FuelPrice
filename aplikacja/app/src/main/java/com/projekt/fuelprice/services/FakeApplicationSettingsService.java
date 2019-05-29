package com.projekt.fuelprice.services;

import com.projekt.fuelprice.data.GasStation;

public class FakeApplicationSettingsService implements ApplicationSettingsService {
    private GasStation.FuelType fuelType = GasStation.FuelType.t95;

    @Override
    public GasStation.FuelType getSelectedFuelType() {
        return fuelType;
    }

    @Override
    public void setSelectedFuelType(GasStation.FuelType type) {
        fuelType = type;
    }
}
