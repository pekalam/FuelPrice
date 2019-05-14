package com.projekt.fuelprice.services;

import android.app.Application;
import android.content.Context;
import android.support.v4.util.Consumer;
import android.util.JsonReader;
import android.util.JsonToken;

import com.google.android.gms.common.util.JsonUtils;
import com.projekt.fuelprice.data.GasStation;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class FakeTomTomApiClient implements AsyncMapApiClient {

    private Context mContext;

    public FakeTomTomApiClient(Context context){
        mContext = context;
    }


    @Override
    public void getNearbyGasStations(double latitude, double longitude, int radius, Consumer<GasStation[]> onSuccess, Consumer<Throwable> onError) {
        int totalGasStations;
        GasStation[] gasStations = null;
        int i = 0;
        try {
            InputStream jsonStream = mContext.getAssets().open("testsData/tomTomApiClient/nearby.json");
            JsonReader reader = new JsonReader(new InputStreamReader(jsonStream));
            reader.beginObject();
            while(reader.hasNext()){
                String name = reader.nextName();
                if(name.equals("summary")){
                    reader.beginObject();
                    while(reader.hasNext()){
                        if(reader.peek() == JsonToken.NAME)
                            name = reader.nextName();
                        else
                            reader.skipValue();
                        if(name.equals("numResults")){
                            totalGasStations = reader.nextInt();
                            gasStations = new GasStation[totalGasStations];
                        }

                    }
                    reader.endObject();
                }
                if(name.equals("results")){
                    reader.beginArray();


                    while(reader.hasNext()) {
                        reader.beginObject();
                        gasStations[i] = new GasStation();
                        gasStations[i].name = "Brak nazwy";
                        gasStations[i].brandName = "Nieznana";

                        while(reader.hasNext()) {

                            if(reader.peek() == JsonToken.NAME)
                                name = reader.nextName();
                            else
                                reader.skipValue();
                            if (name.equals("poi")) {
                                reader.beginObject();
                                while (reader.hasNext()) {
                                    if(reader.peek() == JsonToken.NAME)
                                        name = reader.nextName();
                                    else
                                        reader.skipValue();
                                    if (name.equals("name")) {
                                        gasStations[i].name = reader.nextString();
                                    }
                                    if(name.equals("brands")){
                                        reader.beginArray();

                                        while(reader.hasNext()){
                                            reader.beginObject();

                                            while(reader.hasNext()){
                                                if(reader.peek() == JsonToken.NAME)
                                                    name = reader.nextName();
                                                else
                                                    reader.skipValue();
                                                if(name.equals("name"))
                                                    gasStations[i].brandName = reader.nextString();
                                            }

                                            reader.endObject();
                                        }

                                        reader.endArray();
                                    }
                                }
                                reader.endObject();
                            }

                            if(name.equals("position")){
                                reader.beginObject();
                                while(reader.hasNext()){
                                    if(reader.peek() == JsonToken.NAME)
                                        name = reader.nextName();
                                    else
                                        reader.skipValue();
                                    if(name.equals("lat"))
                                        gasStations[i].lat = reader.nextDouble();
                                    if(name.equals("lon"))
                                        gasStations[i].lon = reader.nextDouble();
                                }
                                reader.endObject();
                            }
                        }


                        reader.endObject();
                        i++;
                    }


                    reader.endArray();
                }
            }
            reader.endObject();
            if(gasStations == null)
                onError.accept(new Throwable());
            onSuccess.accept(gasStations);
        } catch (IOException e) {
            e.printStackTrace();
            onError.accept(e);
        }

    }
}
