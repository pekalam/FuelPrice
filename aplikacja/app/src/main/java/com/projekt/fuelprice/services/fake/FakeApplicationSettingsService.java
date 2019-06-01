package com.projekt.fuelprice.services.fake;

import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.services.interfaces.ApplicationSettingsService;

public class FakeApplicationSettingsService implements ApplicationSettingsService {
    private GasStation.FuelType fuelType = GasStation.FuelType.LPG;

    @Override
    public GasStation.FuelType getSelectedFuelType() {
        return fuelType;
    }

    @Override
    public void setSelectedFuelType(GasStation.FuelType type) {
        fuelType = type;
    }
}
