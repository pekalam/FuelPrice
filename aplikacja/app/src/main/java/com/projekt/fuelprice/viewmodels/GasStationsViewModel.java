package com.projekt.fuelprice.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.core.util.Consumer;

import com.google.android.gms.maps.model.LatLng;
import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.data.GasStationsRepository;
import com.projekt.fuelprice.services.interfaces.ApplicationSettingsService;
import com.projekt.fuelprice.services.interfaces.DistanceService;
import com.projekt.fuelprice.services.interfaces.LocationService;
import com.projekt.fuelprice.utils.DistanceUtils;

import java.util.ArrayList;

/**
 * ViewModel wspólny dla wszystkich fragmentów aplikacji
 * TODO: zmiana nazwy viewmodelu
 */
public class GasStationsViewModel extends ViewModel {

    /**
     * Minimalny, domyslny promien (w metrach) w jakim wyszukiwane sa stacje
     */
    public static int MIN_SEARCHING_RADIUS = 20000; //TODO 20KM?
    /**
     * Maksymalny promien (w metrach) w jakim wyszukiwane sa stacje
     */
    public static int MAX_SEARCHING_RADIUS = 70000; //TODO: 70KM?
    /**
     * Minimalna odleglosc o jaka musi zmienic sie obecne polozenie uzytkownika w
     * stosunku do poprzedniego polozenia aby pobrac nowe stacje
     */
    public static int MIN_RADIUS_DIFF = 4000;

    /**
     * Aktualna lokalizacja klienta
     */
    private MutableLiveData<LatLng> currentPosition = new MutableLiveData<LatLng>();
    /**
     * Stacje pobrane z repozytorium stacji paliw
     */
    private MutableLiveData<GasStation[]> gasStations = new MutableLiveData<GasStation[]>();
    /**
     * Dystans dla kazdej ze stacji mierzony od obecnej lokalizacji klienta
     */
    private MutableLiveData<Double[]> gasStationsDistance = new MutableLiveData<Double[]>();
    /**
     * Typ paliwa wybrany przez klienta
     */
    private MutableLiveData<GasStation.FuelType> selectedFuelType = new MutableLiveData<GasStation.FuelType>();
    /**
     * Stacja wybrana przez klienta na mapie lub na liscie
     */
    private MutableLiveData<GasStation> selectedGasStation = new MutableLiveData<GasStation>();
    /**
     * Promien podany metrach wokol ktorego wyszukiwane sa stacje
     */
    private MutableLiveData<Integer> searchingRadius = new MutableLiveData<Integer>();

    /**
     * Serwisy wykorzystywane przez view model
     */
    private GasStationsRepository gasStationsRepo;
    private DistanceService distanceService;
    private LocationService locationService;
    private ApplicationSettingsService applicationSettingsService;

    private boolean _locationServiceStarted = false;
    //Promien dla ktorego zostaly pobrane stacje
    private int _loadRadius = -1;
    //Pozycja dla ktorej zostaly pobrane stacje
    private LatLng _loadPos = null;

    public GasStationsViewModel(GasStationsVMDepedencies viewModelDepedencies){
        this.gasStationsRepo = viewModelDepedencies.gasStationsRepository;
        this.distanceService = viewModelDepedencies.distanceService;
        this.locationService = viewModelDepedencies.locationService;
        this.applicationSettingsService = viewModelDepedencies.applicationSettingsService;
        //pobranie i ustawienie typu paliwa wybranego poprzednio przez klienta
        GasStation.FuelType selected = applicationSettingsService.getSelectedFuelType();
        selectedFuelType.setValue(selected);
        searchingRadius.setValue(MIN_SEARCHING_RADIUS);
    }

    public void forceLoadGasStations(){
        LatLng pos = currentPosition.getValue();
        if(pos == null){
            Log.d("A:GasStationsViewModel", "Force load null pos");
            return;
        }
        Integer radius = searchingRadius.getValue();
        Log.d("A:GasStationsViewModel", "Force load");
        loadGasStations(pos, radius);
    }

    public void navigateTo(@NonNull GasStation gasStation, @NonNull Context context){
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + gasStation.lat + "," + gasStation.lon);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        context.startActivity(mapIntent);
    }

    /**
     * Pobranie stacji dla danej lokalizacji i w okreslonym promieniu
     * @param pos lokalizacja
     * @param radius promien wokol ktorego wyszukiwane sa stacje
     */
    private void loadGasStations(LatLng pos, final int radius){
        Log.d("A:GasStationsViewModel", "loadGasStations radius: " + radius);
        if(_loadPos != null && _loadRadius != -1){
            if(radius == _loadRadius){
                if(DistanceUtils.distanceBetween(_loadPos.latitude, _loadPos.longitude, pos.latitude, pos.longitude) <= MIN_RADIUS_DIFF){
                    Log.d("A:GasStationsViewModel", "cached stations loaded");
                    return;
                }
            }
            if(radius < _loadRadius){
                if(DistanceUtils.distanceBetween(_loadPos.latitude, _loadPos.longitude, pos.latitude, pos.longitude) + radius <= _loadRadius){
                    Log.d("A:GasStationsViewModel", "cached stations loaded");
                    return;
                }
            }
        }
        gasStationsRepo.getGasStations(pos, radius, new Consumer<GasStation[]>() {
            @Override
            public void accept(GasStation[] fetchedGasStations) {
                gasStations.setValue(fetchedGasStations);
                //Brak zmierzonej odleglosci dla pobranych stacji
                gasStationsDistance.setValue(new Double[0]);
                LatLng myPosition = currentPosition.getValue();
                _loadPos = myPosition;
                _loadRadius = radius;
                getDistanceToGasStations(fetchedGasStations, myPosition);
            }
        });
    }

    /**
     * Pobiera odleglosci od podanej lokalizacji do wszystkich podanych stacji
     * @param gasStations
     * @param myPos
     */
    private void getDistanceToGasStations(GasStation[] gasStations, LatLng myPos){
        Log.d("A:GasStationsViewModel", "getDistance");
        final Double[] distances = new Double[gasStations.length];
        final ArrayList<Integer> found = new ArrayList<>();
        for (int i = 0; i < gasStations.length; i++) {
            final int stationInd = i;
            distanceService.findDistance(myPos.latitude, myPos.longitude, gasStations[i].lat, gasStations[i].lon, new DistanceService.Listener() {
                @Override
                public void onDistanceFound(double distance) {
                    distances[stationInd] = distance;
                    found.add(1);
                    if(found.size() == distances.length){
                        gasStationsDistance.setValue(distances);
                    }
                }

                @Override
                public void onDistanceServiceError() {
                    distances[stationInd] = Double.NaN;
                    found.add(1);
                }
            });
        }
    }

    /**
     * Rozpoczyna wyszukiwanie aktualnej lokalizacji klienta
     */
    public void startFindingCurrentPosition(){
        Log.d("A:GasStationsViewModel", "startFindingCurrentPosition");
        if(!_locationServiceStarted) {
            locationService.startService(new LocationService.Listener() {
                @Override
                public void onLocationChanged(LatLng newLocation) {
                    currentPosition.setValue(newLocation);
                    Integer radius = searchingRadius.getValue();
                    loadGasStations(newLocation, radius);
                }
            });
            _locationServiceStarted = true;
        }
    }

    /**
     * Zatrzymuje usluge pobierania aktualnej lokalizacji klienta
     */
    public void stopFindingCurrentPosition(){
        Log.d("A:GasStationsViewModel", "stopFindingCurrentPosition");
        if(_locationServiceStarted) {
            locationService.stopService();
            _locationServiceStarted = false;
        }
    }

    /**
     * Uruchamia usluge jednorazowego pobrania lokalizacji
     * @param listener
     */
    public void findCurrrentPositionOnce(final LocationService.Listener listener){
        locationService.singleRequest(new LocationService.Listener() {
            @Override
            public void onLocationChanged(LatLng newLocation) {
                listener.onLocationChanged(newLocation);
            }
        });
    }

    public LiveData<GasStation.FuelType> getSelectedFuelType() { return selectedFuelType; }
    /**
     * Zapisuje preferowany przez klienta typ paliwa
     * @param selectedFuelType
     */
    public void setSelectedFuelType(GasStation.FuelType selectedFuelType) {
        //Zapis w pamieci urządzenia preferowanego typu paliwa
        applicationSettingsService.setSelectedFuelType(selectedFuelType);
        this.selectedFuelType.postValue(selectedFuelType);
    }

    public LiveData<GasStation> getSelectedGasStation(){ return selectedGasStation; }

    public void setSelectedGasStation(GasStation station){ selectedGasStation.setValue(station); }

    public LiveData<GasStation[]> getGasStations() { return gasStations; }

    public LiveData<LatLng> getCurrentPosition(){return currentPosition;}

    public LiveData<Double[]> getGasStationsDistance() { return gasStationsDistance; }

    public void setSearchingRadius(int radius){
        if(radius < MIN_SEARCHING_RADIUS){
            Log.d("A:GasStationsViewModel", "Ustawiono zbyt maly promien: " + radius);
            radius = MIN_SEARCHING_RADIUS;
        }
        if(radius > MAX_SEARCHING_RADIUS){
            Log.d("A:GasStationsViewModel", "Ustawiono za duzy promien: " + radius);
            radius = MAX_SEARCHING_RADIUS;
        }
        Log.d("A:GasStationsViewModel", "Ustawiono promien: " + radius);
        searchingRadius.setValue(radius);
    }

    public LiveData<Integer> getSearchingRadius(){return searchingRadius;}

}
