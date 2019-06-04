package com.projekt.fuelprice;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
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
import com.projekt.fuelprice.voicerecog.VoiceCommand;

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

    private boolean removeOnReturn = false;

    public VoiceRecognitionFragment() {
        // Required empty public constructor
    }

    public static VoiceRecognitionFragment newInstance() {
        VoiceRecognitionFragment fragment = new VoiceRecognitionFragment();
        return fragment;
    }

    private void delayedRemove(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                removeFragment();
            }
        }, 1500);
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

        //funkcja wykonywana po rozpoznaniu mowy
        voiceRecognitionFragmentVM.getRecognizedVoiceCommand().observe(getViewLifecycleOwner(), new Observer<VoiceCommand>() {
            @Override
            public void onChanged(final VoiceCommand voiceCommand) {
                if(voiceCommand == null){
                    binding.overlayRoot.setBackgroundColor(getContext().getResources().getColor(R.color.red_overlay));
                    binding.commandName.setText("Nie rozpoznano komendy");
                    delayedRemove();

                }else{
                    binding.overlayRoot.setBackgroundColor(getContext().getResources().getColor(R.color.green_overlay));
                    //wykonanie komendy
                    removeOnReturn = true;
                    if(!voiceCommand.execute(gasStationsViewModel, getActivity())){
                        binding.overlayRoot.setBackgroundColor(getContext().getResources().getColor(R.color.red_overlay));
                        binding.commandName.setText("Błąd");
                        delayedRemove();
                    }else {
                        binding.commandName.setText(voiceCommand.getClass().getName());
                    }
                }
            }
        });

        voiceRecognitionFragmentVM.getVoiceRecognitionAvailable().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean available) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Brak funkcji rozpoznawania mowy")
                        .setMessage("Funkcja rozpoznawania mowy nie jest dostępna na Twoim urządzeniu")
                        .setPositiveButton("Powrót", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeFragment();
                            }
                        }).show();
            }
        });

        return binding.getRoot();
    }

    private void removeFragment(){
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .remove(this)
                .commit();
    }

    @Override
    public void onResume() {
        if(removeOnReturn) {
            removeFragment();
        }else {
            voiceRecognitionFragmentVM.startListening(getContext());
        }
        super.onResume();
    }
}
