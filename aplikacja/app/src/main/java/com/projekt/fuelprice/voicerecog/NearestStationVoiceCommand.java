package com.projekt.fuelprice.voicerecog;

import android.content.Context;

import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.viewmodels.GasStationsViewModel;

public class NearestStationVoiceCommand extends VoiceCommand {
    public NearestStationVoiceCommand(String cmdTail) {
        super(cmdTail);
    }

    @Override
    public boolean execute(GasStationsViewModel gasStationsViewModel, Context context) {
        if(gasStationsViewModel.getGasStations().getValue() != null &&
            gasStationsViewModel.getGasStations().getValue().length > 0){
            if(gasStationsViewModel.getGasStationsDistance().getValue() != null &&
            gasStationsViewModel.getGasStationsDistance().getValue().length > 0){
                GasStation[] stations = gasStationsViewModel.getGasStations().getValue();
                Double[] distance = gasStationsViewModel.getGasStationsDistance().getValue();
                int n = 0;
                for(int i = 1; i < stations.length; i++){
                    if(distance[i] < distance[n]){
                        n = i;
                    }
                }
                gasStationsViewModel.navigateTo(stations[n], context);
                return true;
            }
            return false;
        }else{
            return false;
        }

    }
}
