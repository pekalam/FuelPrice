package com.projekt.fuelprice.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import android.content.Context;
import androidx.annotation.NonNull;

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
