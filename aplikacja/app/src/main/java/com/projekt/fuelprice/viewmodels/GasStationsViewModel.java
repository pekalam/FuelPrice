package com.projekt.fuelprice.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.core.util.Consumer;

import com.google.android.gms.maps.model.LatLng;
import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.data.GasStationsRepository;
import com.projekt.fuelprice.services.DistanceService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

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

    private DistanceService distanceService;

    public GasStationsViewModel(GasStationsRepository gasStationsRepository, DistanceService distanceService){
        this.gasStationsRepo = gasStationsRepository;
        this.distanceService = distanceService;
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

                //Sortowanie pod wzgledem ceny paliwa
                Arrays.sort(fetchedGasStations, new Comparator<GasStation>() {
                    @Override
                    public int compare(GasStation o1, GasStation o2) {
                        return 1;
                    }
                });
                gasStations.setValue(fetchedGasStations);
            }
        });
    }

    // TODO: jak to zrobic normalnie bez bibliotek
    public void getDistanceToGasStations(final GasStation[] gasStations, final Consumer<double[]> onDistancesFound){
        final double[] distances = new double[gasStations.length];
        final ArrayList<Integer> complete = new ArrayList<Integer>();
        for (int i = 0; i < gasStations.length; i++) {
            final int stationInd = i;
            distanceService.findDistance(gasStations[i].lat, gasStations[i].lon, gasStations[i].lat, gasStations[i].lon, new DistanceService.Listener() {
                @Override
                public void onDistanceFound(double distance) {
                    distances[stationInd] = distance;
                    complete.add(1);
                    if(complete.size() == gasStations.length){
                        onDistancesFound.accept(distances);
                    }
                }

                @Override
                public void onDistanceServiceError() {
                    //TODO
                }
            });
        }
    }

    public LiveData<GasStation[]> getGasStations() {
        return gasStations;
    }
}
