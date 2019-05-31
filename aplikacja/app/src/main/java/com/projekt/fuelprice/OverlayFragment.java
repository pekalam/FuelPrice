package com.projekt.fuelprice;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projekt.fuelprice.databinding.FragmentOverlayBinding;

public class OverlayFragment extends Fragment {

    private FragmentOverlayBinding binding;

    public OverlayFragment() {
        // Required empty public constructor
    }

    public static OverlayFragment newInstance() {
        OverlayFragment fragment = new OverlayFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_overlay, container, false);
        return binding.getRoot();
    }
}
