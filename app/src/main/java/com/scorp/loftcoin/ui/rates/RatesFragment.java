package com.scorp.loftcoin.ui.rates;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.scorp.loftcoin.R;
import com.scorp.loftcoin.data.Coin;
import com.scorp.loftcoin.databinding.FragmentRatesBinding;
import com.scorp.loftcoin.util.PercentFormatter;
import com.scorp.loftcoin.util.PriceFormatter;

import java.util.List;

import timber.log.Timber;

public class RatesFragment extends Fragment {

    private FragmentRatesBinding binding;

    private RatesAdapter adapter;
    private RatesViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RatesViewModel.class);
        adapter = new RatesAdapter(new PriceFormatter(), new PercentFormatter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rates, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        binding = FragmentRatesBinding.bind(view);
        binding.recyclerRates.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.recyclerRates.swapAdapter(adapter, false);
        binding.recyclerRates.setHasFixedSize(true);

        viewModel.coins().observe(getViewLifecycleOwner(), (coins) -> adapter.submitList(coins));
        viewModel.isRefreshing().observe(getViewLifecycleOwner(), (refreshing) -> binding.refresher.setRefreshing(refreshing));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.rates_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(R.id.currency_dialog == item.getItemId()){
            NavHostFragment.findNavController(this)
                .navigate(R.id.currency_dialog);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        binding.recyclerRates.swapAdapter(null, false);
        super.onDestroyView();
    }
}
