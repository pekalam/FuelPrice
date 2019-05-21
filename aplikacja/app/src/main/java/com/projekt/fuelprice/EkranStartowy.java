package com.projekt.fuelprice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.projekt.fuelprice.databinding.ActivityEkranStartowyBinding;

public class EkranStartowy extends AppCompatActivity implements View.OnClickListener {

    private ActivityEkranStartowyBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekran_startowy);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ekran_startowy);
        binding.ekranStartowy.setOnClickListener(this);
    }

    /**
     * On ekranStartowy click
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
