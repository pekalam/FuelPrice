package com.projekt.fuelprice.data;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class GasStation {
    public enum FuelType{
        t98(0), t95(1), LPG(2), ON(3);

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
    public Bitmap miniLogo;

    public GasStation(){
        randomPrices();
    }

    public float getPriceOf(FuelType type){
        switch (type){
            case LPG:
                return priceLPG;
            case t95:
                return price95;
            case t98:
                return price98;
            case ON:
                return priceON;
                default:
                    return 0;
        }
    }

    public void randomPrices(){
        price95 = 4.0f + (float)Math.random() * (4.7f - 4.0f);
        price98 = 4.0f + (float)Math.random() * (4.7f - 4.0f);
        priceLPG = 4.0f + (float)Math.random() * (4.7f - 4.0f);
        priceON = 4.0f + (float)Math.random() * (4.7f - 4.0f);
    }
}
