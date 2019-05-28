package com.projekt.fuelprice.data;

import android.graphics.drawable.Drawable;

public class GasStation {
    public enum FuelType{
        t98(0), t95(1), LPG(2);

        private int type;

        FuelType(int type){
            this.type = type;
        }

        public int asInt(){
            return type;
        }

        public static FuelType intToFuelType(int type){
            for(FuelType t : FuelType.values()){
                if(t.type == type) {
                    return t;
                }
            }
            return t98;
        }
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
