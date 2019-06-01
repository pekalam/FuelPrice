package com.projekt.fuelprice.di.components;


import android.app.Application;

import com.projekt.fuelprice.FuelPriceApplication;
import com.projekt.fuelprice.di.modules.AppModule;
import com.projekt.fuelprice.di.modules.AppComponentInjectorContributors;
import com.projekt.fuelprice.di.modules.GasStationsRepositoryModule;
import com.projekt.fuelprice.di.modules.GasStationsVMFactoryModule;
import com.projekt.fuelprice.di.modules.VoiceRecogntionVMFactoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {
        AppModule.class,
        GasStationsRepositoryModule.class,
        GasStationsVMFactoryModule.class,
        VoiceRecogntionVMFactoryModule.class,
        AppComponentInjectorContributors.class,
        AndroidSupportInjectionModule.class
})
public interface AppComponent {
    @Component.Builder
    interface Builder{
        @BindsInstance Builder application(Application app);
        AppComponent build();
    }
    void inject(FuelPriceApplication app);
}
