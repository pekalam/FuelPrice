package com.projekt.fuelprice;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projekt.fuelprice.databinding.FragmentOverlayBinding;

import java.util.ArrayList;
import java.util.Locale;

public class OverlayFragment extends Fragment implements RecognitionListener {

    private FragmentOverlayBinding binding;

    public OverlayFragment() {
        // Required empty public constructor
    }

    public static OverlayFragment newInstance() {
        OverlayFragment fragment = new OverlayFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_overlay, container, false);
        binding.overlayRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to listen to user vocal input and return the result to the same activity.
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                // Use a language model based on free-form speech recognition.
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                        getContext().getPackageName());

                // Add custom listeners.
                SpeechRecognizer sr = SpeechRecognizer.createSpeechRecognizer(getContext());
                sr.setRecognitionListener(OverlayFragment.this);
                sr.startListening(intent);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.d("end", "error");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d("end", "error");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.d("end", "error");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.d("end", "error");
    }

    @Override
    public void onEndOfSpeech() {
        Log.d("end", "error");
    }

    @Override
    public void onError(int error) {
        Log.d("end", "error");
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> result = results
                .getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.d("end", "error");
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        Log.d("end", "error");
    }
}
