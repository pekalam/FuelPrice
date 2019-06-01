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
        if(lowerText.startsWith("nawiguj")){
            String tail = text.substring(lowerText.indexOf("nawiguj")); //reszta tekstu oprocz slowa kluczowego
            //tail ma sluzyc jako ew. parametry typu " do Orlen"
            listener.onRecognized(text); //jaki tekst sluzyl do rozpoznania
            listener.onCommandRecognized(VoiceCommandName.NAVIGATE_TO, tail); //nazwa komendy utworzona w 1) i reszta tekstu
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
        Log.d("asd", "Asd");
        listener.onReadyForSpeech();
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
        listener.onEndOfSpeech();
    }

    @Override
    public void onError(int error) {
        Log.d("asd", "Asd");
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
        Log.d("asd", "Asd");
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        Log.d("asd", "Asd");
    }
}
