package com.projekt.fuelprice.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import android.content.Context;
import androidx.annotation.NonNull;

import com.projekt.fuelprice.data.GasStationsRepository;
import com.projekt.fuelprice.data.GasStationsRepositoryFactory;
import com.projekt.fuelprice.services.AsyncMapApiClient;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;

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
