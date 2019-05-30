package com.projekt.fuelprice;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;


import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.databinding.FuelBottomSheetBinding;
import com.projekt.fuelprice.services.ApplicationSettingsService;
import com.projekt.fuelprice.viewmodels.GasStationsViewModel;
import com.projekt.fuelprice.viewmodels.GasStationsViewModelFactory;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;

public class FuelSelectionBottomSheetDialogFragment extends BottomSheetDialogFragment {

    boolean iscolorLPG = true;
    boolean iscolor95 = true;
    boolean iscolor98 = true;
    boolean iscolorON = true;


    @Inject
    GasStationsViewModelFactory gasStationsViewModelFactory;
    private GasStationsViewModel gasStationsViewModel;

    private FuelSelectionBottomSheetDialogFragment(){
    }

    private FuelBottomSheetBinding binding;

    public static FuelSelectionBottomSheetDialogFragment newInstance() {
        return new FuelSelectionBottomSheetDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        gasStationsViewModel = ViewModelProviders.of(getActivity(), gasStationsViewModelFactory).get(GasStationsViewModel .class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fuel_bottom_sheet, container, false);

        Button btnLPG = binding.buttonLPG;
        final FrameLayout frameLPG = binding.frameLPG;
        Button btnON = binding.buttonON;
        final FrameLayout frameON = binding.frameON;
        Button btn95 = binding.button95;
        final FrameLayout frame95 = binding.frame95;
        Button btn98 = binding.button98;
        final FrameLayout frame98 = binding.frame98;


        if(gasStationsViewModel.getSelectedFuelType().getValue()==GasStation.FuelType.LPG)
        {
            frameLPG.setBackgroundColor(Color.BLUE);
            iscolorLPG = false;
        }
        else if (gasStationsViewModel.getSelectedFuelType().getValue()==GasStation.FuelType.ON)
        {
            frameON.setBackgroundColor(Color.BLUE);
            iscolorON = false;
        }
        else if (gasStationsViewModel.getSelectedFuelType().getValue()==GasStation.FuelType.t95)
        {
            frame95.setBackgroundColor(Color.BLUE);
            iscolor95 = false;
        }
        else if (gasStationsViewModel.getSelectedFuelType().getValue()==GasStation.FuelType.t98)
        {
            frame98.setBackgroundColor(Color.BLUE);
            iscolor98 = false;
        }

        btnLPG.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(iscolorLPG)
                {
                    frameLPG.setBackgroundColor(Color.BLUE);
                    frameON.setBackgroundColor(Color.WHITE);
                    frame95.setBackgroundColor(Color.WHITE);
                    frame98.setBackgroundColor(Color.WHITE);
                    iscolorLPG = false;
                    iscolorON = true;
                    iscolor95 = true;
                    iscolor98 = true;
                    gasStationsViewModel.setSelectedFuelType(GasStation.FuelType.LPG);
                }

            }
        });

        btnON.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(iscolorON)
                {
                    frameLPG.setBackgroundColor(Color.WHITE);
                    frameON.setBackgroundColor(Color.BLUE);
                    frame95.setBackgroundColor(Color.WHITE);
                    frame98.setBackgroundColor(Color.WHITE);
                    iscolorLPG = true;
                    iscolorON = false;
                    iscolor95 = true;
                    iscolor98 = true;
                    gasStationsViewModel.setSelectedFuelType(GasStation.FuelType.ON);
                }


            }
        });

        btn95.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(iscolor95)
                {
                    frameLPG.setBackgroundColor(Color.WHITE);
                    frameON.setBackgroundColor(Color.WHITE);
                    frame95.setBackgroundColor(Color.BLUE);
                    frame98.setBackgroundColor(Color.WHITE);
                    iscolorLPG = true;
                    iscolorON = true;
                    iscolor95 = false;
                    iscolor98 = true;
                    gasStationsViewModel.setSelectedFuelType(GasStation.FuelType.t95);
                }

            }
        });

        btn98.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(iscolor98)
                {
                    frameLPG.setBackgroundColor(Color.WHITE);
                    frameON.setBackgroundColor(Color.WHITE);
                    frame95.setBackgroundColor(Color.WHITE);
                    frame98.setBackgroundColor(Color.BLUE);
                    iscolorLPG = true;
                    iscolorON = true;
                    iscolor95 = true;
                    iscolor98 = false;
                    gasStationsViewModel.setSelectedFuelType(GasStation.FuelType.t98);
                }

            }
        });
        return binding.getRoot();
    }

}