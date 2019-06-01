package com.projekt.fuelprice.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.projekt.fuelprice.VoiceCommandName;
import com.projekt.fuelprice.services.interfaces.VoiceRecognitionService;

public class VoiceRecognitionFragmentVM extends ViewModel {

    private VoiceRecognitionService voiceRecognitionService;
    private MutableLiveData<String> recognizedString = new MutableLiveData<>();

    public VoiceRecognitionFragmentVM(@NonNull VoiceRecognitionService voiceRecognitionService){
        this.voiceRecognitionService = voiceRecognitionService;
    }

    public void startListening(){
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
            public void onCommandRecognized(VoiceCommandName command, String tail) {
            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onBegginingOfSpeech() {
                recognizedString.setValue("Proszę mówić");
            }
        });
    }

    public LiveData<String> getRecognizedString(){
        return recognizedString;
    }
}
