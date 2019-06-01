package com.projekt.fuelprice.services.real;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.projekt.fuelprice.R;
import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.services.interfaces.GasStationLogoService;

public class GasStationLocalLogoService implements GasStationLogoService {

    private Context context;

    public GasStationLocalLogoService(Context context){
        this.context = context;
    }

    @Override
    public void getGasStationLogo(GasStation gasStation, final GasStationLogoService.Listener listener){
        int logoId;
        switch (gasStation.brandName){
            case "Orlen":
                logoId = R.drawable.orlen;
                break;
            case "Lotos":
                logoId = R.drawable.lotos;
                break;
            case "Shell":
                logoId = R.drawable.shell1;
                break;
            case "BP":
                logoId = R.drawable.bp;
                break;
            case "Watkem":
                logoId = R.drawable.watkem;
                break;
            case "Zrodelko":
                logoId = R.drawable.zrodelko1;
                break;
            case "Valdi":
                logoId = R.drawable.valdi;
                break;
            default:
                logoId = R.drawable.nieoznakowana;
                break;
        }

        {
            Glide
                    .with(context)
                    .asDrawable()
                    .load(logoId)
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            listener.onSuccess(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                            listener.onSuccess(placeholder);
                        }
                    });
        }

    }
}
