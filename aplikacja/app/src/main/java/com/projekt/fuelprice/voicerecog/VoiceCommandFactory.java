package com.projekt.fuelprice.voicerecog;

public class VoiceCommandFactory {
    //TODO: 4) na podstawie commandName zwracana jest komenda
    public static VoiceCommand create(VoiceCommandName commandName, String cmdTail){
        switch (commandName){
            case NAVIGATE_TO:
                return new NavigateVoiceCommand(cmdTail);
                default:
                    return null;
        }
    }
}
