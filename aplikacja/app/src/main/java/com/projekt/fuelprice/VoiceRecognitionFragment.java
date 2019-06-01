package com.projekt.fuelprice;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.databinding.FragmentOverlayBinding;
import com.projekt.fuelprice.viewmodels.GasStationsViewModel;
import com.projekt.fuelprice.viewmodels.GasStationsViewModelFactory;
import com.projekt.fuelprice.viewmodels.VoiceRecognitionFragmentVM;
import com.projekt.fuelprice.viewmodels.VoiceRecognitionFragmentVMFactory;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class VoiceRecognitionFragment extends Fragment{

    @Inject
    GasStationsViewModelFactory gasStationsViewModelFactory;
    GasStationsViewModel gasStationsViewModel;

    @Inject
    VoiceRecognitionFragmentVMFactory voiceRecognitionFragmentVMFactory;
    private VoiceRecognitionFragmentVM voiceRecognitionFragmentVM;

    private FragmentOverlayBinding binding;

    public VoiceRecognitionFragment() {
        // Required empty public constructor
    }

    public static VoiceRecognitionFragment newInstance() {
        VoiceRecognitionFragment fragment = new VoiceRecognitionFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        gasStationsViewModel = ViewModelProviders.of(getActivity(), gasStationsViewModelFactory).get(GasStationsViewModel.class);
        voiceRecognitionFragmentVM = ViewModelProviders.of(this, voiceRecognitionFragmentVMFactory).get(VoiceRecognitionFragmentVM.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_overlay, container, false);
        binding.setVm(voiceRecognitionFragmentVM);
        binding.setLifecycleOwner(this);

        binding.getRoot().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        voiceRecognitionFragmentVM.startListening();
        super.onResume();
    }
}
