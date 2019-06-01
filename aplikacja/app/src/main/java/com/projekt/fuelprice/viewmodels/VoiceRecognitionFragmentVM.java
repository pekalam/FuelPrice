package com.projekt.fuelprice.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.projekt.fuelprice.voicerecog.VoiceCommand;
import com.projekt.fuelprice.voicerecog.VoiceCommandFactory;
import com.projekt.fuelprice.voicerecog.VoiceCommandName;
import com.projekt.fuelprice.services.interfaces.VoiceRecognitionService;

public class VoiceRecognitionFragmentVM extends ViewModel {

    private VoiceRecognitionService voiceRecognitionService;
    private MutableLiveData<String> recognizedString = new MutableLiveData<>();
    private MutableLiveData<VoiceCommand> recognizedCommand = new MutableLiveData<>();

    private MutableLiveData<Boolean> voiceRecognitionAvailable = new MutableLiveData<>();

    public VoiceRecognitionFragmentVM(@NonNull VoiceRecognitionService voiceRecognitionService){
        this.voiceRecognitionService = voiceRecognitionService;
    }

    public void startListening(Context context){
        voiceRecognitionService.startListening(new VoiceRecognitionService.Listener() {
            @Override
            public void onRecognized(String text) {
                recognizedString.setValue(text);
            }

            @Override
            public void onPartiallyRecognized(String text) {
                recognizedString.setValue(text);
            }

            @Override
            public void onCommandRecognized(VoiceCommandName commandName, String tail) {
                if(commandName == VoiceCommandName.NOT_RECOGNIZED){
                    recognizedCommand.setValue(null);
                    return;
                }
                //TODO: 3) na podstawie commandName i tail fabryka tworzy komende
                VoiceCommand cmd = VoiceCommandFactory.create(commandName, tail);
                if(cmd != null){
                    recognizedCommand.setValue(cmd);
                }else{
                    recognizedCommand.setValue(null);
                }
            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onReadyForSpeech() {
                recognizedString.setValue("Proszę mówić");
            }

            @Override
            public void onRecognitionNotAvailable() {
                voiceRecognitionAvailable.setValue(false);
            }
        });
    }

    public LiveData<String> getRecognizedString(){
        return recognizedString;
    }

    public LiveData<VoiceCommand> getRecognizedVoiceCommand(){
        return recognizedCommand;
    }

    public LiveData<Boolean> getVoiceRecognitionAvailable() {
        return voiceRecognitionAvailable;
    }
}
