package com.projekt.fuelprice.services;

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
         * Zwraca jako parametr rozpoznaną komendę
         * @param command rozpoznana komenda.
         *       Jeżeli ma wartość null rozpoznany tekst nie jest komendą
         */
        void onCommandRecognized(VoiceCommandName command);

        void onEndOfSpeech();
        void onBegginingOfSpeech();
    }
    /**
     * Zaczyna nasłuchiwać w celu rozpoznania komend
     * @param listener
     */
    void startListening(Listener listener);
}
