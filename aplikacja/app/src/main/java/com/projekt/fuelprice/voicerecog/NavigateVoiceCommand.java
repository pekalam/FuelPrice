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
        //TODO 6): korzystajac z gasStationsViewModel, context i ewentualnie szukajac parametrow w
        //this.cmdTail mozna wykonac komende

        //np. nawigacja do pierwszej wyszukanej stacji (nie najtanszej):
        GasStation[] stacje = gasStationsViewModel.getGasStations().getValue();
        gasStationsViewModel.navigateTo(stacje[0], context);
        //jak bedzie 0 stacji to return false
        return true;
    }
}
