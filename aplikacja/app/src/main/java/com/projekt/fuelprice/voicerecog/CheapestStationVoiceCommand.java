package com.projekt.fuelprice.voicerecog;

import android.content.Context;

import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.viewmodels.GasStationsViewModel;

public class CheapestStationVoiceCommand extends VoiceCommand {
    public CheapestStationVoiceCommand(String cmdTail) {
        super(cmdTail);
    }

    @Override
    public boolean execute(GasStationsViewModel gasStationsViewModel, Context context) {
        GasStation[] stations = gasStationsViewModel.getGasStations().getValue();
        if(stations != null){
            GasStation.FuelType selectedType = gasStationsViewModel.getSelectedFuelType().getValue();
            GasStation cheapest = stations[0];
            for(int i = 1; i < stations.length; i++){
                if(stations[i].getPriceOf(selectedType) < cheapest.getPriceOf(selectedType)){
                    cheapest = stations[i];
                }
            }
            gasStationsViewModel.navigateTo(cheapest, context);
            return true;
        }else{
            return false;
        }
    }
}
