package com.projekt.fuelprice.di.modules;

import com.projekt.fuelprice.services.interfaces.VoiceRecognitionService;
import com.projekt.fuelprice.viewmodels.VoiceRecognitionFragmentVMFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module(
        includes = AppModule.class
)
public class VoiceRecogntionVMFactoryModule {
    @Provides
    VoiceRecognitionFragmentVMFactory provideVoiceRecognitionFragmentVMFactory(@Named("voiceRecognitionService") VoiceRecognitionService voiceRecognitionService){
        return new VoiceRecognitionFragmentVMFactory(voiceRecognitionService);
    }
}
