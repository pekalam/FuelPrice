package com.projekt.fuelprice.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.projekt.fuelprice.services.VoiceRecognitionService;

public class VoiceRecognitionFragmentVMFactory implements ViewModelProvider.Factory {

    private VoiceRecognitionService voiceRecognitionService;

    public VoiceRecognitionFragmentVMFactory(VoiceRecognitionService voiceRecognitionService){
        this.voiceRecognitionService = voiceRecognitionService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new VoiceRecognitionFragmentVM(voiceRecognitionService);
    }
}
