package com.projekt.fuelprice.voicerecog;

public class VoiceCommandFactory {
    //TODO: 4) na podstawie commandName zwracana jest komenda
    public static VoiceCommand create(VoiceCommandName commandName, String cmdTail){
        switch (commandName){
            case NAVIGATE:
                return new NavigateVoiceCommand(cmdTail);
            case NAVIGATE_NEAREST:
                return new NearestStationVoiceCommand(cmdTail);
            case NAVIGATE_CHEAPEST:
                return new CheapestStationVoiceCommand(cmdTail);
                default:
                    return null;
        }
    }
}
