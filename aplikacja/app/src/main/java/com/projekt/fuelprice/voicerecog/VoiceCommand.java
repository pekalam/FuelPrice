package com.projekt.fuelprice.voicerecog;

import android.content.Context;

import com.projekt.fuelprice.viewmodels.GasStationsViewModel;

public abstract class VoiceCommand {
    protected String cmdTail;
    public VoiceCommand(String cmdTail){
        this.cmdTail = cmdTail;
    }
    public abstract boolean execute(GasStationsViewModel gasStationsViewModel, Context context);
}
