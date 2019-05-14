package com.projekt.fuelprice;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.viewmodels.GasStationsViewModel;
import com.projekt.fuelprice.viewmodels.GasStationsViewModelFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class GasStationsFragment extends Fragment {

    private GasStationsViewModel gasStationsViewModel;

    private TextView testowy;

    /*
        Trzeba utworzyc RecyclerView w layoucie fragment_gas_stations
        Dodac RecyclerView do fragmentu i utworzyc do niego referencje tak jak tutaj do testowy
        W onCreateView ustawic dla tego RecyclerView adapter i layoutmanagera
        https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#10
     */

    public GasStationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        gasStationsViewModel = ViewModelProviders.of(getActivity(), new GasStationsViewModelFactory(this.getActivity().getApplication())).get(GasStationsViewModel.class);

        /*
            Co ma sie stac jak lista stacji w viewmodel ulegnie zmianie
         */
        gasStationsViewModel.getGasStations().observe(this ,new Observer<GasStation[]>() {
            @Override
            public void onChanged(@Nullable GasStation[] gasStations) {
                testowy.setText(gasStations[0].brandName);

                /*
                Ustawienie danych dla adaptera listy
                adapter.setGasStations(gasStations) ??
                 */
            }
        });


        // Inflate the layout for this fragment
        View fragment = inflater.inflate(R.layout.fragment_gas_stations, container, false);
        this.testowy = fragment.findViewById(R.id.testowy);
        return fragment;
    }

}
