package com.projekt.fuelprice.services.interfaces;

import com.projekt.fuelprice.data.GasStation;

public interface ApplicationSettingsService {
    GasStation.FuelType getSelectedFuelType();
    void setSelectedFuelType(GasStation.FuelType type);
}
