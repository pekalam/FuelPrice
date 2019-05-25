package com.projekt.fuelprice;

import android.graphics.drawable.Drawable;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.services.GasStationLocalLogoService;
import com.projekt.fuelprice.services.GasStationLogoService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class GasStationLocalLogoServiceTests {

    @Test
    public void getGasStationLogo_when_good_brandName_returns_logo() throws Exception {
        GasStationLogoService service = new GasStationLocalLogoService(InstrumentationRegistry.getTargetContext());
        GasStation station = new GasStation();
        station.brandName = "Shell";
        final CountDownLatch lock = new CountDownLatch(1);

        final Drawable expected = InstrumentationRegistry.getTargetContext().getDrawable(R.drawable.shell);
        service.getGasStationLogo(station, new GasStationLogoService.Listener() {
            @Override
            public void onSuccess(Drawable logo) {
                /*
                TODO: porownanie 2 drawable
                 */
                if(logo != null)
                    lock.countDown();
            }
        });

        lock.await(1000, TimeUnit.MILLISECONDS);
        Assert.assertTrue(lock.getCount() == 0);
    }
}
