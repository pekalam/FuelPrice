package com.projekt.fuelprice;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.util.Consumer;

import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.services.AsyncMapApiClient;
import com.projekt.fuelprice.services.FakeTomTomApiClient;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class FakeTomTomApiClientTests {
    int numberOfStations = 0;
    GasStation[] stations;

    @Test
    public void returns_nearby_gas_stations(){

        AsyncMapApiClient client = new FakeTomTomApiClient(InstrumentationRegistry.getContext());
        client.getNearbyGasStations(1, 1, 1, new Consumer<GasStation[]>() {
            @Override
            public void accept(GasStation[] gasStations) {
                numberOfStations = gasStations.length;
                stations = gasStations;
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                fail(throwable.getMessage());
            }
        });
        assertTrue(numberOfStations == 10);
        assertTrue(stations[0].brandName.equals("Shell"));
        assertTrue(stations[0].lat == 50.04268);
        assertTrue(stations[0].lon == 21.99056);
    }
}
