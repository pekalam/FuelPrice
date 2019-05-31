package com.projekt.fuelprice.services;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;

import java.util.ArrayList;

public class RealRecognitionListener implements RecognitionListener {

    private VoiceRecognitionService.Listener listener;

    public RealRecognitionListener(VoiceRecognitionService.Listener listener){
        this.listener = listener;
    }

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {

    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> result = results
                .getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
}
