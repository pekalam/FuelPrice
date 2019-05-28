package com.projekt.fuelprice.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.projekt.fuelprice.R;
import com.projekt.fuelprice.data.GasStation;

public class FuelTypeSettings {
    /**
     * Domyślny typ paliwa
     */
    private static GasStation.FuelType DEFAULT_FUEL_TYPE = GasStation.FuelType.t98;


    /**
     * Zwraca domyslny typ paliwa
     * @return
     */
    public static GasStation.FuelType getDefaultFuelType(){
        return DEFAULT_FUEL_TYPE;
    }

    /**
     * Zwraca domyślne / wybrane przez użytkownika paliwo
     * @param context
     * @return wybrane paliwo
     */
    public static GasStation.FuelType getSelectedFuelType(Context context){
        String fName = context.getString(R.string.preferences_file_name);
        String fuelTypeKey = context.getString(R.string.preferences_fuel_type_key);
        SharedPreferences preferences = context.getSharedPreferences(fName, Context.MODE_PRIVATE);

        if(preferences.contains(fuelTypeKey)){
            int selectedTypeInt = preferences.getInt(fuelTypeKey, DEFAULT_FUEL_TYPE.asInt());
            return GasStation.FuelType.intToFuelType(selectedTypeInt);
        }else{
            return DEFAULT_FUEL_TYPE;
        }
    }

    /**
     * Zapisuje wybrany przez uzytkownika typ paliwa
     * @param context
     * @return
     */
    public static void setSelectedFuelType(Context context, GasStation.FuelType type){
        String fName = context.getString(R.string.preferences_file_name);
        String fuelTypeKey = context.getString(R.string.preferences_fuel_type_key);
        SharedPreferences preferences = context.getSharedPreferences(fName, Context.MODE_PRIVATE);

        preferences.edit().putInt(fuelTypeKey, type.asInt()).commit();
    }
}
