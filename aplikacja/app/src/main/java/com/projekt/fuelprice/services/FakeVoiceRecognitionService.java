package com.projekt.fuelprice.services;

import android.os.Handler;

import com.projekt.fuelprice.VoiceCommandName;

public class FakeVoiceRecognitionService implements VoiceRecognitionService {
    @Override
    public void startListening(final Listener listener) {
        listener.onBegginingOfSpeech();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onEndOfSpeech();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listener.onRecognized("Nawiguj do najtańszej stacji");
                        listener.onCommandRecognized(VoiceCommandName.NAVIGATE_TO);
                    }
                }, 200);
            }
        }, 1500);

    }
}
