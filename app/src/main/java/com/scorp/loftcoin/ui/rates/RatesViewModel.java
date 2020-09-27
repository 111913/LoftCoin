package com.scorp.loftcoin.ui.rates;

import android.transition.TransitionInflater;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.scorp.loftcoin.data.Coin;
import com.scorp.loftcoin.data.CoinsRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

public class RatesViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isRefreshing = new MutableLiveData<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final MutableLiveData<Boolean> forceRefresh = new MutableLiveData<>(false);
    private final LiveData<List<Coin>> coins;

    // AppComponent(BaseComponent) -> MainComponent -> Fragment(BaseComponent) -> RatesComponent -> RatesViewModel()

    @Inject
    public RatesViewModel(CoinsRepo coinsRepo){
        //(f|t) -> forceRefresh -> sort -> query -> listings
        final LiveData<CoinsRepo.Query> query = Transformations
                .map(forceRefresh, (r) -> {
                    isRefreshing.postValue(true);
                    return CoinsRepo.Query.builder()
                            .forceUpdate(r)
                            .currency("USD")
                            .build();
                });
        final LiveData<List<Coin>> coins = Transformations.switchMap(query, coinsRepo::listings);
        this.coins = Transformations.map(coins, (c) -> {
            isRefreshing.postValue(false);
            return c;
        });
    }

    @NonNull
    LiveData<List<Coin>> coins(){
        return coins;
    }

    @NonNull
    LiveData<Boolean> isRefreshing() {
        return isRefreshing;
    }

    final void refresh() {
        forceRefresh.postValue(true);
    }

}
