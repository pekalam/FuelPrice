package com.projekt.fuelprice;


import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.databinding.FragmentGasStationsBinding;
import com.projekt.fuelprice.viewmodels.GasStationsViewModel;
import com.projekt.fuelprice.viewmodels.GasStationsViewModelFactory;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class GasStationsFragment extends Fragment {

    private GasStationsViewModel gasStationsViewModel;

    private FragmentGasStationsBinding binding;

    private RecyclerView recyclerView;
    
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
        recyclerView = fragment.findViewById(R.id.rec1);
        adapter = new GasStationsAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        /*
            Co ma sie stac jak lista stacji w viewmodel ulegnie zmianie
         */
        gasStationsViewModel.getGasStations().observe(this ,new Observer<GasStation[]>() {
            @Override
            public void onChanged(@Nullable GasStation[] gasStations) {
                adapter.setGasStations(gasStations);

            }
        });

        return binding.getRoot();
    }

}
