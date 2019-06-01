package com.projekt.fuelprice.services;

import android.content.Context;
import android.os.Handler;

import com.projekt.fuelprice.VoiceCommandName;

public class FakeVoiceRecognitionService implements VoiceRecognitionService {
    @Override
    public void startListening(final Listener listener) {
        listener.onBegginingOfSpeech();
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
                        listener.onCommandRecognized(VoiceCommandName.NAVIGATE_TO, " do najtańszej stacji");
                    }
                }, 500);
            }
        }, 2000);

    }
}
