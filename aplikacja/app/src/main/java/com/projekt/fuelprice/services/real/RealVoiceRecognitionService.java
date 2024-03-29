package com.projekt.fuelprice.services.real;

import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import com.projekt.fuelprice.services.interfaces.VoiceRecognitionService;
import com.projekt.fuelprice.services.real.RealRecognitionListener;

import java.util.Locale;

public class RealVoiceRecognitionService implements VoiceRecognitionService {

    private Context context;

    public RealVoiceRecognitionService(Context context){
        this.context = context;
    }

    @Override
    public void startListening(Listener listener) {
        if(!SpeechRecognizer.isRecognitionAvailable(context)){
            listener.onRecognitionNotAvailable();
            return;
        }
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);

        SpeechRecognizer sr = SpeechRecognizer.createSpeechRecognizer(context);
        sr.setRecognitionListener(new RealRecognitionListener(listener));
        sr.startListening(intent);
    }
}
