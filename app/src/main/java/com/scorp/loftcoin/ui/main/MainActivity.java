package com.scorp.loftcoin.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.scorp.loftcoin.R;
import com.scorp.loftcoin.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}