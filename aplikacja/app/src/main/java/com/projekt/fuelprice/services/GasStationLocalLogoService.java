package com.projekt.fuelprice.services;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.projekt.fuelprice.R;
import com.projekt.fuelprice.data.GasStation;

import java.util.concurrent.ExecutionException;

public class GasStationLocalLogoService implements GasStationLogoService {

    private Context context;

    public GasStationLocalLogoService(Context context){
        this.context = context;
    }

    @Override
    public void getGasStationLogo(GasStation gasStation, final GasStationLogoService.Listener listener){
        int logoId = -1;
        switch (gasStation.brandName){
            case "Orlen":
                logoId = R.drawable.orlen;
                break;
            case "Lotos":
                logoId = R.drawable.lotos;
                break;
            case "Shell":
                logoId = R.drawable.shell;
                break;
        }

        if(logoId != -1){
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
        else {
            listener.onSuccess(context.getResources().getDrawable(R.drawable.orlen));
        }

    }
}
