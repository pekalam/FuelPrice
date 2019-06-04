package com.projekt.fuelprice.services.real;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.util.Log;

import com.projekt.fuelprice.voicerecog.VoiceCommandName;
import com.projekt.fuelprice.services.interfaces.VoiceRecognitionService;

import java.util.ArrayList;

public class RealRecognitionListener implements RecognitionListener {

    private VoiceRecognitionService.Listener listener;

    public RealRecognitionListener(VoiceRecognitionService.Listener listener){
        this.listener = listener;
    }

    private boolean recognizeCommand(String text){
        //TODO: 2) na podstawie rozpoznanego tekstu (male znaki) wywolywane sa 2 funkcje
        String lowerText = text.toLowerCase();
        String tail = "";
        VoiceCommandName commandName = VoiceCommandName.NOT_RECOGNIZED;
        if(lowerText.startsWith("nawiguj")){
            tail = text.substring(lowerText.indexOf("nawiguj"));
            commandName = VoiceCommandName.NAVIGATE;
        }
        else if(lowerText.startsWith("wybierz najtańsz")){
            commandName = VoiceCommandName.NAVIGATE_CHEAPEST;
        }
        else if(lowerText.startsWith("wybierz najbliższ")){
            commandName = VoiceCommandName.NAVIGATE_NEAREST;
        }

        if(commandName != VoiceCommandName.NOT_RECOGNIZED){
            listener.onRecognized(text);
            listener.onCommandRecognized(commandName, tail);
            return true;
        }
        return false;
    }

    private void commandNotRecognized(String text){
        listener.onRecognized(text);
        listener.onCommandRecognized(VoiceCommandName.NOT_RECOGNIZED, "");
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        listener.onReadyForSpeech();
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
        listener.onEndOfSpeech();
    }

    @Override
    public void onError(int error) {
        if(error == 7){
            //TODO: odpowiedz jesli wystapil blad
            commandNotRecognized("Nie rozumiem");
        }
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> txtResults = results.getStringArrayList("results_recognition");
        if(txtResults != null){
            //najlepsze dopasowanie
            String first = txtResults.get(0);
            for (String txt: txtResults
                 ) {
                if(recognizeCommand(txt)){
                    return;
                }
            }
            commandNotRecognized(first);
        }else{
            //TODO: odpowiedz jesli wystapil blad
            commandNotRecognized("Nie rozumiem");
        }
        Log.d("asd", "Asd");
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        ArrayList<String> result = partialResults
                .getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
    }
}
