package com.projekt.fuelprice.services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import androidx.annotation.NonNull;

import com.projekt.fuelprice.VoiceRecognitionFragment;

import java.util.Locale;

public class RealVoiceRecognitionService implements VoiceRecognitionService {

    private Context context;

    public RealVoiceRecognitionService(Context context){
        this.context = context;
    }

    @Override
    public void startListening(Listener listener) {
        // Intent to listen to user vocal input and return the result to the same activity.
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // Use a language model based on free-form speech recognition.
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                context.getPackageName());

        // Add custom listeners.
        SpeechRecognizer sr = SpeechRecognizer.createSpeechRecognizer(context);
        sr.setRecognitionListener(new RealRecognitionListener(listener));
        sr.startListening(intent);
    }
}
