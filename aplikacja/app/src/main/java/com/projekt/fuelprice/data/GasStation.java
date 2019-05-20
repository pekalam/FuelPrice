package com.projekt.fuelprice.data;

import android.graphics.drawable.Drawable;

public class GasStation {
    public enum FuelType{
        t98, t95, LPG
    }

    public String brandName;
    public String name;
    public double lat;
    public double lon;
    public float price98;
    public float price95;
    public float priceLPG;
    public float priceON;
    public Drawable logo;

    public GasStation(){
        randomPrices();
    }

    public void randomPrices(){
        price95 = 4.0f + (float)Math.random() * (4.7f - 4.0f);
        price98 = 4.0f + (float)Math.random() * (4.7f - 4.0f);
        priceLPG = 4.0f + (float)Math.random() * (4.7f - 4.0f);
        priceON = 4.0f + (float)Math.random() * (4.7f - 4.0f);
    }
}
