package com.projekt.fuelprice;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.view.LayoutInflater;
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

        voiceRecognitionFragmentVM.getRecognizedCommandName().observe(getViewLifecycleOwner(), new Observer<VoiceCommandName>() {
            @Override
            public void onChanged(VoiceCommandName voiceCommandName) {
                /*//TODO: VoiceCommandFactory tworzy konkretną klasę implementująca VoiceCommand
                //i przyjmujaca gasStationsViewModel w konstruktorze na podstawie voiceCommandName
                //np stworzy NavigateVoiceCommand i tam beda wywolane odpowiednie metody gasStationsViewModel
                //zeby rozpoczac nawigacje do konkretnej stacji
                //VoiceCommand to interfejs z metodą invoke()
                VoiceCommand cmd = VoiceCommandFactory.createCommand(voiceCommandName, gasStationsViewModel);
                cmd.invoke();*/


                if(voiceCommandName == VoiceCommandName.NAVIGATE_TO){
                    //Dzialajacy przyklad co moze być w NavigateVoiceCommand:
                    GasStation stations[] = gasStationsViewModel.getGasStations().getValue();
                    if(stations.length > 0) {
                        GasStation cheap = stations[0];
                        for (int i = 1; i < stations.length; i++) {
                            if (stations[i].price95 < cheap.price95){
                                cheap = stations[i];
                            }
                        }
                        gasStationsViewModel.navigateTo(cheap, getContext());
                    }
                }
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
