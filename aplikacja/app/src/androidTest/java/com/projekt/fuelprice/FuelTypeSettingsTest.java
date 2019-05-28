package com.projekt.fuelprice;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.utils.FuelTypeSettings;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class FuelTypeSettingsTest {

    @Test
    public void returns_default_FuelType(){
        Context context = InstrumentationRegistry.getTargetContext();
        String fName = context.getString(R.string.preferences_file_name);
        context.getSharedPreferences(fName, Context.MODE_PRIVATE).edit().clear().commit();

        GasStation.FuelType returned = FuelTypeSettings.getSelectedFuelType(context);

        assertEquals(returned, FuelTypeSettings.getDefaultFuelType());
    }

    @Test
    public void returns_saved_FuelType(){
        Context context = InstrumentationRegistry.getTargetContext();
        String fName = context.getString(R.string.preferences_file_name);
        context.getSharedPreferences(fName, Context.MODE_PRIVATE).edit().clear().commit();

        GasStation.FuelType type = GasStation.FuelType.LPG;
        FuelTypeSettings.setSelectedFuelType(context, type);
        GasStation.FuelType returned = FuelTypeSettings.getSelectedFuelType(context);

        GasStation.FuelType type2 = GasStation.FuelType.t95;
        FuelTypeSettings.setSelectedFuelType(context, type2);
        GasStation.FuelType returned2 = FuelTypeSettings.getSelectedFuelType(context);

        assertEquals(returned, GasStation.FuelType.LPG);
        assertEquals(returned2, GasStation.FuelType.t95);
    }
}
