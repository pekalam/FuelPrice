package com.projekt.fuelprice.services.interfaces;

import android.content.Context;

import com.projekt.fuelprice.voicerecog.VoiceCommandName;

/**
 * Rozpoznaje wypowiadane komendy
 */
public interface VoiceRecognitionService {
    interface Listener{
        /**
         * Zwraca jako parametr dowolny rozpoznany tekst
         * @param text
         */
        void onRecognized(String text);
        /**
         * Zwraca czesc dowolnego rozpoznanego tekstu
         * @param text
         */
        void onPartiallyRecognized(String text);
        /**
         * Zwraca rozpoznana komende
         * @param command komenda
         * @param cmdTail pozostala czesc tekstu po wydzieleniu slowa kluczowego
         */
        void onCommandRecognized(VoiceCommandName command, String cmdTail);

        /**
         * Zakonczono rejestrowanie mowy
         */
        void onEndOfSpeech();

        /**
         * Rozpoczeto rejestrowanie mowy
         */
        void onReadyForSpeech();

        /**
         * Usluga rozpoznawania mowy nie jest dostepna
         */
        void onRecognitionNotAvailable();
    }
    /**
     * Zaczyna nasłuchiwać w celu rozpoznania komend
     * @param listener
     */
    void startListening(Listener listener);
}
