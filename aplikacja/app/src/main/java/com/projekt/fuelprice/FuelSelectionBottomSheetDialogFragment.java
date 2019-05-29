package com.projekt.fuelprice;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;


import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.databinding.FuelBottomSheetBinding;
import com.projekt.fuelprice.services.ApplicationSettingsService;
import com.projekt.fuelprice.viewmodels.FuelSelectionBottomSheetVM;

public class FuelSelectionBottomSheetDialogFragment extends BottomSheetDialogFragment {

    boolean iscolorLPG = true;
    boolean iscolor95 = true;
    boolean iscolor98 = true;
    boolean iscolorON = true;

    private ApplicationSettingsService settingsService;

    private FuelSelectionBottomSheetVM viewModel;

    private FuelSelectionBottomSheetDialogFragment(ApplicationSettingsService settingsService){
        this.settingsService = settingsService;
    }

    private FuelBottomSheetBinding binding;

    public static FuelSelectionBottomSheetDialogFragment newInstance(ApplicationSettingsService settingsService) {
        return new FuelSelectionBottomSheetDialogFragment(settingsService);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new FuelSelectionBottomSheetVM(settingsService);
        binding = DataBindingUtil.inflate(inflater, R.layout.fuel_bottom_sheet, container, false);
        binding.setVm(viewModel);

        Button btnLPG = binding.buttonLPG;
        final FrameLayout frameLPG = binding.frameLPG;
        Button btnON = binding.buttonON;
        final FrameLayout frameON = binding.frameON;
        Button btn95 = binding.button95;
        final FrameLayout frame95 = binding.frame95;
        Button btn98 = binding.button98;
        final FrameLayout frame98 = binding.frame98;


        if(settingsService.getSelectedFuelType()==GasStation.FuelType.LPG)
        {
            frameLPG.setBackgroundColor(Color.BLUE);
            iscolorLPG = false;
        }
        else if (settingsService.getSelectedFuelType()==GasStation.FuelType.ON)
        {
            frameON.setBackgroundColor(Color.BLUE);
            iscolorON = false;
        }
        else if (settingsService.getSelectedFuelType()==GasStation.FuelType.t95)
        {
            frame95.setBackgroundColor(Color.BLUE);
            iscolor95 = false;
        }
        else if (settingsService.getSelectedFuelType()==GasStation.FuelType.t98)
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
                    settingsService.setSelectedFuelType(GasStation.FuelType.LPG);
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
                    settingsService.setSelectedFuelType(GasStation.FuelType.ON);
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
                    settingsService.setSelectedFuelType(GasStation.FuelType.t95);
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
                    settingsService.setSelectedFuelType(GasStation.FuelType.t98);
                }

            }
        });
        return binding.getRoot();
    }

}