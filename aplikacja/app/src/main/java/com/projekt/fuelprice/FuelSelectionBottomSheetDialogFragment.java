package com.projekt.fuelprice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.projekt.fuelprice.databinding.FuelBottomSheetBinding;
import com.projekt.fuelprice.services.ApplicationSettingsService;
import com.projekt.fuelprice.viewmodels.FuelSelectionBottomSheetVM;

public class FuelSelectionBottomSheetDialogFragment extends BottomSheetDialogFragment {

    /*TODO funkcje boolean isCos w FuelSelectionBottomSheetVM moga byc wykorzystywane w xml
        tak jak w fuel_bottom_sheet.xml przy wyborze koloru tego tekstu @{vm.Cos ? kolor2 : kolor1}
        tutaj mozna dac w onClick dla buttonow zmiane za pomocą settingsService
        jak zmienicie wybrane paliwo settingsService to wywolajcie w onClick binding.invalidateAll
     */

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

        return binding.getRoot();

    }
}