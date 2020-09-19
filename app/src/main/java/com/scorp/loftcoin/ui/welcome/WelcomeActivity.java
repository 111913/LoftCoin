package com.scorp.loftcoin.ui.welcome;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.scorp.loftcoin.R;
import com.scorp.loftcoin.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity {

    public static final String KEY_SHOW_WELCOME = "show_welcome";

    private ActivityWelcomeBinding binding;
    private SnapHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.recyclerView.setAdapter(new WelcomeAdapter());

        helper = new PagerSnapHelper();
        helper.attachToRecyclerView(binding.recyclerView);
    }

    @Override
    protected void onDestroy() {
        helper.attachToRecyclerView(null);
        binding.recyclerView.setAdapter(null);
        super.onDestroy();
    }
}
