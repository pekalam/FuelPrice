package com.projekt.fuelprice;


import androidx.core.util.Consumer;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.databinding.FragmentGasStationsBinding;
import com.projekt.fuelprice.services.PermissionsService;
import com.projekt.fuelprice.viewmodels.GasStationListItemVM;
import com.projekt.fuelprice.viewmodels.GasStationsViewModel;
import com.projekt.fuelprice.viewmodels.GasStationsViewModelFactory;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class GasStationsFragment extends Fragment {

    private GasStationsViewModel gasStationsViewModel;

    private FragmentGasStationsBinding binding;

    private GasStationsAdapter adapter;

    @Inject
    GasStationsViewModelFactory gasStationsViewModelFactory;


    public GasStationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        gasStationsViewModel = ViewModelProviders.of(getActivity(), gasStationsViewModelFactory).get(GasStationsViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gas_stations, container, false);

        adapter = new GasStationsAdapter(getContext());
        binding.rec1.setAdapter(adapter);
        binding.rec1.setLayoutManager(new LinearLayoutManager(this.getContext()));
        /*
            Co ma sie stac jak lista stacji w viewmodel ulegnie zmianie
         */
        gasStationsViewModel.getGasStations().observe(this ,new Observer<GasStation[]>() {
            @Override
            public void onChanged(@Nullable GasStation[] gasStations) {
                final GasStationListItemVM[] listItemVMs = new GasStationListItemVM[gasStations.length];
                for (int i = 0; i < gasStations.length; i++) {
                    listItemVMs[i] = new GasStationListItemVM(gasStations[i]);
                }
                gasStationsViewModel.getDistanceToGasStations(gasStations, new Consumer<double[]>() {
                    @Override
                    public void accept(double[] distances) {
                        for (int i = 0; i < distances.length; i++) {
                            listItemVMs[i].distance = distances[i];
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
                adapter.setGasStations(listItemVMs);

            }
        });

        return binding.getRoot();
    }

}
