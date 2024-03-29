package com.projekt.fuelprice.services.fake;

import android.os.Handler;

import com.projekt.fuelprice.voicerecog.VoiceCommandName;
import com.projekt.fuelprice.services.interfaces.VoiceRecognitionService;

public class FakeVoiceRecognitionService implements VoiceRecognitionService {
    static int i = 0;
    @Override
    public void startListening(final Listener listener) {
        listener.onReadyForSpeech();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onPartiallyRecognized("Nawig");
            }
        }, 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onEndOfSpeech();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listener.onRecognized("Nawiguj do najtańszej stacji");
                        if(i == 0) {
                            listener.onCommandRecognized(VoiceCommandName.NAVIGATE, " do najtańszej stacji");
                            i++;
                        }
                        else if(i==1){
                            listener.onCommandRecognized(VoiceCommandName.NOT_RECOGNIZED, " do najtańszej stacji");
                            i++;

                        }else{
                            listener.onRecognitionNotAvailable();
                            i++;
                            i = i %3;
                        }
                    }
                }, 500);
            }
        }, 2000);

    }
}
