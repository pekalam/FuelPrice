package com.projekt.fuelprice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.projekt.fuelprice.databinding.FuelBottomSheetBinding;

public class FuelSelectionBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private FuelBottomSheetBinding binding;

    public static FuelSelectionBottomSheetDialogFragment newInstance() {
        return new FuelSelectionBottomSheetDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fuel_bottom_sheet, container, false);

        return binding.getRoot();

    }
}