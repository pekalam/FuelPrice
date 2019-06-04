package com.projekt.fuelprice.voicerecog;

import android.content.Context;

import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.viewmodels.GasStationsViewModel;

//TODO: 5) fabryka zwrocila obiekt tej klasy na podstawie commandName
//kolejna komenda - kolejna klasa
public class NavigateVoiceCommand extends VoiceCommand {

    public NavigateVoiceCommand(String cmdTail) {
        super(cmdTail);
    }

    @Override
    public boolean execute(GasStationsViewModel gasStationsViewModel, Context context) {
        if(gasStationsViewModel.getSelectedGasStation().getValue() != null){
            gasStationsViewModel.navigateTo(gasStationsViewModel.getSelectedGasStation().getValue(),
                    context);
            return true;
        }else{
            return false;
        }

    }
}
