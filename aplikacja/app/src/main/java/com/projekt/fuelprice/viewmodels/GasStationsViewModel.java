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
import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * ViewModel wspólny dla wszystkich fragmentów aplikacji
 * TODO: zmiana nazwy viewmodelu
 */
public class GasStationsViewModel extends ViewModel {

    public static int DEFAULT_SEARCHING_RADIUS = 2000;
    public static int MIN_SEARCHING_RADIUS = 1000;
    public static int MAX_SEARCHING_RADIUS = 30000;

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

    public GasStationsViewModel(GasStationsVMDepedencies viewModelDepedencies){
        this.gasStationsRepo = viewModelDepedencies.gasStationsRepository;
        this.distanceService = viewModelDepedencies.distanceService;
        this.locationService = viewModelDepedencies.locationService;
        this.applicationSettingsService = viewModelDepedencies.applicationSettingsService;
        //pobranie i ustawienie typu paliwa wybranego poprzednio przez klienta
        GasStation.FuelType selected = applicationSettingsService.getSelectedFuelType();
        selectedFuelType.setValue(selected);
        searchingRadius.setValue(DEFAULT_SEARCHING_RADIUS);
    }

    public void forceLoadGasStations(){
        LatLng pos = currentPosition.getValue();
        Integer radius = searchingRadius.getValue();
        loadGasStations(pos, radius);
    }

    /**
     * Pobranie stacji dla danej lokalizacji i w okreslonym promieniu
     * @param pos lokalizacja
     * @param radius promien wokol ktorego wyszukiwane sa stacje
     */
    private void loadGasStations(LatLng pos, int radius){
        if(radius <= MIN_SEARCHING_RADIUS){
            throw new InvalidParameterException();
        }
        if(radius > MAX_SEARCHING_RADIUS){
            radius = MAX_SEARCHING_RADIUS;
        }
        gasStationsRepo.getGasStations(pos, radius, new Consumer<GasStation[]>() {
            @Override
            public void accept(GasStation[] fetchedGasStations) {
                gasStations.setValue(fetchedGasStations);
                LatLng myPosition = currentPosition.getValue();
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
        if(!_locationServiceStarted) {
            locationService.startService(new LocationService.Listener() {
                @Override
                public void onLocationChanged(LatLng newLocation) {
                    currentPosition.setValue(newLocation);
                    Integer radius = searchingRadius.getValue();
                    if(radius == null){
                        loadGasStations(newLocation, DEFAULT_SEARCHING_RADIUS);
                    }else{
                        loadGasStations(newLocation, radius);
                    }
                }
            });
            _locationServiceStarted = true;
        }
    }

    /**
     * Zatrzymuje usluge pobierania aktualnej lokalizacji klienta
     */
    public void stopFindingCurrentPosition(){
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

    public void setSearchingRadius(int radius){searchingRadius.setValue(radius);}

    public LiveData<Integer> getSearchingRadius(){return searchingRadius;}

}
