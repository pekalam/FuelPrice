package com.projekt.fuelprice.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.projekt.fuelprice.data.GasStationsRepository;
import com.projekt.fuelprice.services.DistanceService;

public class GasStationsViewModelFactory implements ViewModelProvider.Factory {

    private GasStationsRepository gasStationsRepository;

    private DistanceService distanceService;

    public GasStationsViewModelFactory(GasStationsRepository gasStationsRepository, DistanceService distanceService) {
        this.gasStationsRepository = gasStationsRepository;
        this.distanceService = distanceService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GasStationsViewModel(gasStationsRepository, distanceService);
    }
}
