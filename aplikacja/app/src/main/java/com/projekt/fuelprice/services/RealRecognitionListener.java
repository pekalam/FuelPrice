package com.projekt.fuelprice.services;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.util.Log;

import com.projekt.fuelprice.VoiceCommandName;

import java.util.ArrayList;

public class RealRecognitionListener implements RecognitionListener {

    private VoiceRecognitionService.Listener listener;

    public RealRecognitionListener(VoiceRecognitionService.Listener listener){
        this.listener = listener;
    }

    private boolean recognizeCommand(String text){
        String lowerText = text.toLowerCase();
        if(lowerText.startsWith("nawiguj")){
            String tail = text.substring(lowerText.indexOf("nawiguj"));
            listener.onRecognized(text);
            listener.onCommandRecognized(VoiceCommandName.NAVIGATE_TO, tail);
            return true;
        }
        return false;
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.d("asd", "Asd");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d("asd", "Asd");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.d("asd", "Asd");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.d("asd", "Asd");
    }

    @Override
    public void onEndOfSpeech() {
        Log.d("asd", "Asd");
    }

    @Override
    public void onError(int error) {
        Log.d("asd", "Asd");
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> result = results
                .getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
        for (String txt: result
             ) {
            if(recognizeCommand(txt)){
                return;
            }
        }
        Log.d("asd", "Asd");
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        ArrayList<String> result = partialResults
                .getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
        Log.d("asd", "Asd");
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        Log.d("asd", "Asd");
    }
}
