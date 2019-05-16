package com.projekt.fuelprice;


import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.databinding.FragmentGasStationsBinding;
import com.projekt.fuelprice.viewmodels.GasStationsViewModel;
import com.projekt.fuelprice.viewmodels.GasStationsViewModelFactory;



public class GasStationsFragment extends Fragment {

    private GasStationsViewModel gasStationsViewModel;

    private FragmentGasStationsBinding binding;

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gas_stations, container, false);
        /*
            Co ma sie stac jak lista stacji w viewmodel ulegnie zmianie
         */
        gasStationsViewModel.getGasStations().observe(this ,new Observer<GasStation[]>() {
            @Override
            public void onChanged(@Nullable GasStation[] gasStations) {

                /*
                Ustawienie danych dla adaptera listy
                adapter.setGasStations(gasStations) ??
                 */
            }
        });

        return binding.getRoot();
    }

}
