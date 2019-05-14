package com.projekt.fuelprice.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.util.Consumer;

import com.google.android.gms.maps.model.LatLng;
import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.data.GasStationsRepository;

/**
 * Zawiera logikę i dane dla mapy i tabelki.
 * Wspoldzielony miedzy MapFragment i GasStationsFragment
 */
public class GasStationsViewModel extends ViewModel {
    /**
     * Stacje pobrane z gasStationsRepo
     */
    private MutableLiveData<GasStation[]> gasStations = new MutableLiveData<GasStation[]>();

    private GasStationsRepository gasStationsRepo;

    public GasStationsViewModel(GasStationsRepository gasStationsRepository){
        this.gasStationsRepo = gasStationsRepository;
    }

    /**
     * Pobranie stacji
     * @param pos pozycja
     * @param radius promien wokol ktorego wyszukiwane sa stacje
     */
    public void loadGasStations(LatLng pos, int radius){
        gasStationsRepo.getGasStations(pos, radius, new Consumer<GasStation[]>() {
            @Override
            public void accept(GasStation[] fetchedGasStations) {
                gasStations.setValue(fetchedGasStations);
            }
        });
    }

    public LiveData<GasStation[]> getGasStations() {
        return gasStations;
    }
}
