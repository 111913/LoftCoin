package com.scorp.loftcoin.ui.rates;

import android.os.Bundle;
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

import com.scorp.loftcoin.BaseComponent;
import com.scorp.loftcoin.R;
import com.scorp.loftcoin.databinding.FragmentRatesBinding;

import javax.inject.Inject;

public class RatesFragment extends Fragment {

    private final RatesComponent ratesComponent;

    private FragmentRatesBinding binding;

    private RatesAdapter adapter;
    private RatesViewModel viewModel;

    @Inject
    public RatesFragment(BaseComponent baseComponent) {
        ratesComponent = DaggerRatesComponent.builder()
                .baseComponent(baseComponent)
                .build();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, ratesComponent.viewModelFactory()).get(RatesViewModel.class);

        adapter = ratesComponent.ratesAdapter();
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
        binding.recyclerRates.setAdapter(adapter);
        binding.recyclerRates.setHasFixedSize(true);

        viewModel.coins().observe(getViewLifecycleOwner(), (coins) -> adapter.submitList(coins));
        viewModel.isRefreshing().observe(getViewLifecycleOwner(), (refreshing) -> binding.refresher.setRefreshing(refreshing));

        binding.refresher.setOnRefreshListener(viewModel::refresh);
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
        else if(R.id.sort == item.getItemId()){
            viewModel.switchSortingOrder();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        binding.recyclerRates.setAdapter(null);
        super.onDestroyView();
    }
}
