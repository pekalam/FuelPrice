package com.projekt.fuelprice.data;

import android.content.Context;

import com.projekt.fuelprice.services.FakeTomTomApiClient;

/**
 * TODO: DI ?
 */
public class GasStationsRepositoryFactory {

    private Context context;

    public GasStationsRepositoryFactory(Context context){
        this.context = context;
    }

    public GasStationsRepository create(){
        return new WebGasStationsRepository(new FakeTomTomApiClient(this.context));
    }
}
