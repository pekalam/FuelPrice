package com.projekt.fuelprice.viewmodels;

import androidx.databinding.Bindable;

import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.services.ApplicationSettingsService;

public class FuelSelectionBottomSheetVM {
    private ApplicationSettingsService service;

    public FuelSelectionBottomSheetVM(ApplicationSettingsService applicationSettingsService){
        this.service = applicationSettingsService;
    }

    public boolean isT98(){
        return service.getSelectedFuelType() == GasStation.FuelType.t98;
    }

    public boolean isT95(){
        return service.getSelectedFuelType() == GasStation.FuelType.t95;
    }

    public boolean isTON(){
        return service.getSelectedFuelType() == GasStation.FuelType.ON;
    }

    public boolean isTLPG(){
        return service.getSelectedFuelType() == GasStation.FuelType.LPG;
    }
}
