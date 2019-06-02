package com.projekt.fuelprice.viewmodels;
import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.projekt.fuelprice.R;
import com.projekt.fuelprice.data.GasStation;

public class GasStationListItemVM{
    public GasStation gasStation;
    public double distance = Double.NaN;
    private GasStation.FuelType selectedFuelType;

    public GasStationListItemVM(GasStation gasStation){
        this.gasStation = gasStation;
    }

    public void setSelectedFuelPrice(GasStation.FuelType type){
        this.selectedFuelType = type;
    }

    public float getSelectedFuelPrice(){
        switch (selectedFuelType){
            case LPG:
                return gasStation.priceLPG;
            case t95:
                return gasStation.price95;
            case t98:
                return gasStation.price98;
            case ON:
                return gasStation.priceON;
        }
        return 0;
    }

    public String formattedDistance(){
        if(Double.isNaN(distance)) {
            return "";
        }
        return String.format("%.2f km", distance);
    }

    @BindingAdapter("android:src")
    public static void setImageResource(ImageView img, int res){
        img.setImageResource(res);
    }

    public int getSelectedFuelIcon(){
        switch (selectedFuelType){
            case LPG:
                return R.drawable.lpg;
            case t95:
                return R.drawable.pb95;
            case t98:
                return R.drawable.pb98;
            case ON:
                return R.drawable.on;
        }
        return R.drawable.lpg;
    }

    public String formattedPrice(){
        return String.format("%.2f zł", getSelectedFuelPrice());
    }
}
