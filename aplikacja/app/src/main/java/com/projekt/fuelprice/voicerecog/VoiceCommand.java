package com.projekt.fuelprice.voicerecog;

import com.projekt.fuelprice.viewmodels.GasStationsViewModel;

public abstract class VoiceCommand {
    public VoiceCommand(String cmdTail){}
    public abstract void execute(GasStationsViewModel gasStationsViewModel);
}
