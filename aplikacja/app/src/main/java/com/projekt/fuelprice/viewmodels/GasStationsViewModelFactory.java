package com.projekt.fuelprice.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import com.projekt.fuelprice.data.GasStationsRepositoryFactory;

public class GasStationsViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {
    private Context context;

    public GasStationsViewModelFactory(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    /*
        Wybierana jest tu konkretna implementacja GasStationRepository dla ViewModelu
     */
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GasStationsViewModel(new GasStationsRepositoryFactory(context).create());
    }
}
