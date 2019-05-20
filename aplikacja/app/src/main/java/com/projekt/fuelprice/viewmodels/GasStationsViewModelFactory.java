package com.projekt.fuelprice.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.projekt.fuelprice.data.GasStationsRepository;

public class GasStationsViewModelFactory implements ViewModelProvider.Factory {

    private GasStationsRepository gasStationsRepository;

    public GasStationsViewModelFactory(GasStationsRepository gasStationsRepository) {
        this.gasStationsRepository = gasStationsRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GasStationsViewModel(gasStationsRepository);
    }
}
