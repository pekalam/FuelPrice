package com.projekt.fuelprice.services.real;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

    private void resolveUnknownBrandName(GasStation gasStation){
        if(gasStation.brandName == null || gasStation.brandName.isEmpty()) {
            if (gasStation.name.toLowerCase().contains("orlen")) {
                gasStation.brandName = "Orlen";
            }
            if (gasStation.name.toLowerCase().contains("źródełko")) {
                gasStation.brandName = "Źródełko";
            }
            if (gasStation.name.toLowerCase().contains("watkem")) {
                gasStation.brandName = "Watkem";
            }
        }
    }

    private int getLogo(GasStation gasStation, boolean mini){
        int logoId;
        resolveUnknownBrandName(gasStation);
        switch (gasStation.brandName){
            case "Orlen":
                logoId = mini ? R.drawable.orlen_mini : R.drawable.orlen;
                break;
            case "Lotos":
                logoId = mini ? R.drawable.lotos_mini : R.drawable.lotos;
                break;
            case "Shell":
                logoId = mini ? R.drawable.shell_mini : R.drawable.shell1;
                break;
            case "BP":
                logoId = mini ? R.drawable.bp_mini : R.drawable.bp;
                break;
            case "Watkem":
                logoId = mini ? R.drawable.nieoznakowana_mini : R.drawable.watkem;
                break;
            case "Zrodelko":
                logoId = mini ? R.drawable.zrodelko_mini : R.drawable.zrodelko1;
                break;
            case "Valdi":
                logoId = mini ? R.drawable.nieoznakowana_mini : R.drawable.valdi;
                break;
            default:
                logoId = mini ? R.drawable.nieoznakowana_mini : R.drawable.nieoznakowana;
                break;
        }
        return logoId;
    }

    @Override
    public void getGasStationLogo(GasStation gasStation, final GasStationLogoService.Listener listener){
        int logoId = getLogo(gasStation, false);
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

    @Override
    public void getGasStationMiniLogo(GasStation gasStation, final ListenerMiniLogo listener) throws Exception {
        int logoId = getLogo(gasStation, true);
        Glide
                .with(context)
                .asBitmap()
                .load(logoId)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        listener.onSuccess(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        listener.onSuccess(((BitmapDrawable)placeholder).getBitmap());
                    }
                });
    }
}
