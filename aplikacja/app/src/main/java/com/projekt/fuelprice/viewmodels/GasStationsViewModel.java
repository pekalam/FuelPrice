package com.projekt.fuelprice.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.core.util.Consumer;

import com.google.android.gms.maps.model.LatLng;
import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.data.GasStationsRepository;
import com.projekt.fuelprice.services.ApplicationSettingsService;
import com.projekt.fuelprice.services.DistanceService;
import com.projekt.fuelprice.services.LocationService;

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

    private MutableLiveData<LatLng> currentPosition = new MutableLiveData<LatLng>();

    private MutableLiveData<GasStation.FuelType> selectedFuelType = new MutableLiveData<GasStation.FuelType>();

    private GasStationsRepository gasStationsRepo;

    private DistanceService distanceService;

    private LocationService locationService;

    private ApplicationSettingsService applicationSettingsService;


    private boolean _locationServiceStarted = false;

    public GasStationsViewModel(GasStationsRepository gasStationsRepository, DistanceService distanceService, LocationService locationService, ApplicationSettingsService applicationSettingsService){
        this.gasStationsRepo = gasStationsRepository;
        this.distanceService = distanceService;
        this.locationService = locationService;
        this.applicationSettingsService = applicationSettingsService;
        GasStation.FuelType selected = applicationSettingsService.getSelectedFuelType();
        selectedFuelType.setValue(selected);
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

    public void findCurrentPositionContinuous(){
        if(!_locationServiceStarted) {
            locationService.startService(new LocationService.Listener() {
                @Override
                public void onLocationChanged(LatLng newLocation) {
                    currentPosition.postValue(newLocation);
                }
            });
        }
    }

    public void stopFindCurrentPosition(){
        _locationServiceStarted = false;
        locationService.stopService();
    }

    public void findCurrrentPositionOneFix(final LocationService.Listener listener){
        locationService.singleRequest(new LocationService.Listener() {
            @Override
            public void onLocationChanged(LatLng newLocation) {
                listener.onLocationChanged(newLocation);
            }
        });
    }

    public void getDistanceToGasStations(final GasStation[] gasStations, final Consumer<double[]> onDistancesFound){
        final double[] distances = new double[gasStations.length];
        final ArrayList<Integer> complete = new ArrayList<Integer>();
        for (int i = 0; i < gasStations.length; i++) {
            final int stationInd = i;
            LatLng myPos = getCurrentPosition().getValue();
            distanceService.findDistance(myPos.latitude, myPos.longitude, gasStations[i].lat, gasStations[i].lon, new DistanceService.Listener() {
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

    public LiveData<GasStation.FuelType> getSelectedFuelType() {
        return selectedFuelType;
    }

    public void setSelectedFuelType(GasStation.FuelType selectedFuelType) {
        applicationSettingsService.setSelectedFuelType(selectedFuelType);
        this.selectedFuelType.postValue(selectedFuelType);
    }

    public LiveData<GasStation[]> getGasStations() {
        return gasStations;
    }

    public LiveData<LatLng> getCurrentPosition(){return currentPosition;}
}
