package com.scorp.loftcoin.ui.rates;

import android.transition.TransitionInflater;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.scorp.loftcoin.data.Coin;
import com.scorp.loftcoin.data.CoinsRepo;
import com.scorp.loftcoin.data.CurrencyRepo;
import com.scorp.loftcoin.data.SortBy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

public class RatesViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isRefreshing = new MutableLiveData<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final MutableLiveData<AtomicBoolean> forceRefresh = new MutableLiveData<>(new AtomicBoolean(true));
    private final MutableLiveData<SortBy> sortBy = new MutableLiveData<>(SortBy.RANK);
    private final LiveData<List<Coin>> coins;

    private int sortingIndex = 1;

    // AppComponent(BaseComponent) -> MainComponent -> Fragment(BaseComponent) -> RatesComponent -> RatesViewModel()

    @Inject
    public RatesViewModel(CoinsRepo coinsRepo, CurrencyRepo currencyRepo){
        //   t           t(f)         t(f)      t(f)     t(f)        t
        //(f|t) -> forceRefresh -> currency -> sortBy -> query -> listings
        final LiveData<CoinsRepo.Query> query = Transformations.switchMap(forceRefresh, (r) -> {
            //r == true || false
            return Transformations.switchMap(currencyRepo.currency(), (c) -> {
                // r == true
                r.set(true);
                isRefreshing.postValue(true);
                return Transformations.map(sortBy, (s) -> {
                    return CoinsRepo.Query.builder()
                            .currency(c.code())
                            .forceUpdate(r.getAndSet(false))
                            .sortBy(s)
                            .build();
                });
            });
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
        forceRefresh.postValue(new AtomicBoolean(true));
    }

    void switchSortingOrder(){
        sortBy.postValue(SortBy.values()[sortingIndex++ % SortBy.values().length]);
    }

}
