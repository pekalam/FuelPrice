package com.projekt.fuelprice.services;

import android.content.Context;

import com.projekt.fuelprice.VoiceCommandName;

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

        void onEndOfSpeech();
        void onBegginingOfSpeech();
    }
    /**
     * Zaczyna nasłuchiwać w celu rozpoznania komend
     * @param listener
     */
    void startListening(Listener listener);
}
