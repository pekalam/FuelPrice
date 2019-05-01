package com.projekt.fuelprice.services;

import android.support.v4.util.Consumer;
import android.util.Log;

import com.loopj.android.http.*;
import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.utils.QueryStringBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class TomTomApiClient implements AsyncMapApiClient {

    public static String getApiUrl() {
        return API_URL;
    }

    public static void setApiUrl(String apiUrl) {
        API_URL = apiUrl;
    }

    private static String API_URL = "https://api.tomtom.com/search/2/";

    private static AsyncHttpClient httpClient = new AsyncHttpClient();



    @Override
    public void getNearbyGasStations(double latitude, double longitude, int radius, final Consumer<GasStation[]> onSuccess, final Consumer<Throwable> onError) {
        String url = new QueryStringBuilder(API_URL + "nearbySearch/.json?")
                .put("key", "3Fzu4cQZMkYrLj9IVbQkTBh0LpGsdqgh")
                .put("lat", String.valueOf(latitude))
                .put("lon", String.valueOf(longitude))
                .put("radius", String.valueOf(radius))
                .put("countrySet", "PL")
                .put("categorySet", "7311")
                .build();


        httpClient.get(url, null, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                onError.accept(new Throwable("Unknown response"));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                onError.accept(throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onError.accept(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                onError.accept(new Throwable("Unknown response (" + responseString + ")"));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                        int totalGasStations = response.getJSONObject("summary").getInt("numResults");
                        Log.d("tomtom", response.toString());
                        GasStation[] stations = new GasStation[totalGasStations];
                        JSONArray results = response.getJSONArray("results");
                        for(int i = 0; i < totalGasStations; i++){
                            JSONObject ob = results.getJSONObject(i);
                            stations[i] = new GasStation();

                            JSONObject poi = ob.getJSONObject("poi");
                            if(poi.has("name"))
                                stations[i].name = poi.getString("name");
                            else
                                stations[i].name = "Bez nazwy";
                            if(poi.has("brands")){
                                JSONArray brands = poi.getJSONArray("brands");
                                if(brands.length() > 0)
                                    stations[i].brandName = brands.getJSONObject(0)
                                        .getString("name");
                                else
                                    stations[i].brandName = "Nieznana";
                            }else
                                stations[i].brandName = "Nieznana";

                            stations[i].lat = ob.getJSONObject("position")
                                    .getDouble("lat");
                            stations[i].lon = ob.getJSONObject("position")
                                    .getDouble("lat");
                        }
                        onSuccess.accept(stations);

                } catch (JSONException e) {
                    e.printStackTrace();
                    onError.accept(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                onError.accept(throwable);
            }
        });
    }
}
