package com.projekt.fuelprice;

import android.content.Context;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.util.Consumer;

import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.services.TomTomApiClient;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.junit.Assert.*;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TomTomApiClientTests {
    private MockWebServer createMockWebServer(MockResponse[] responses) throws IOException {
        MockWebServer server = new MockWebServer();
        for (MockResponse resp: responses
             ) {
            server.enqueue(resp);
        }
        server.start();
        String url = server.url("").toString();
        TomTomApiClient.setApiUrl(url);
        return server;
    }


    @Test
    public void getNearbyGasStations_ValidParams_ReturnsStations() throws InterruptedException, IOException {

        Context testContext = InstrumentationRegistry.getInstrumentation().getContext();
        Scanner s = new Scanner(testContext.getAssets().open("testsData/tomTomApiClient/nearby.json"))
                .useDelimiter("\\A");
        String json = s.hasNext() ? s.next() : "";
        MockWebServer server = createMockWebServer(new MockResponse[]{
                new MockResponse().setBody(json)
        });
        final CountDownLatch lock = new CountDownLatch(1);

        Thread ui = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                TomTomApiClient client = new TomTomApiClient();
                client.getNearbyGasStations(1, 1, 1000, new Consumer<GasStation[]>() {
                    @Override
                    public void accept(GasStation[] gasStations) {
                        if(gasStations.length == 10)
                            assertTrue(true);
                        else
                            fail("gasStations.length != 0");
                        lock.countDown();
                    }}, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable err) {
                        fail("Client error " + err.getMessage());
                    }
                });
                Looper.loop();
            }
        });
        ui.start();
        lock.await(2000, TimeUnit.MILLISECONDS);
        ui.interrupt();


        assertTrue(lock.getCount() == 0);

        server.close();
    }
}
