package com.scorp.loftcoin.ui.rates;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.scorp.loftcoin.data.CmcCoinsRepo;
import com.scorp.loftcoin.data.Coin;
import com.scorp.loftcoin.data.CoinsRepo;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RatesPresenter {

    private final Handler handler = new Handler(Looper.getMainLooper());

    private final ExecutorService executor;
    private final CoinsRepo repo;

    RatesView view;
    private List<? extends Coin> coins = Collections.emptyList();

    RatesPresenter(){
        this.executor = Executors.newSingleThreadExecutor();
        this.repo = new CmcCoinsRepo();
        refresh();
    }

    void attach(@NonNull RatesView view){
        this.view = view;
        if(!coins.isEmpty()){
            view.showCoins(coins);
        }
    }

    void detach(@NonNull RatesView view){
        this.view = null;
    }

    private void onSuccess(List<? extends Coin> coins){
        this.coins = coins;
        if(view != null) {
            view.showCoins(coins);
        }
    }

    private void onError(IOException e){

    }

    final void refresh(){
        executor.submit(() -> {
            try{
                final List<? extends Coin> coins = repo.listings("USD");
                handler.post(() -> onSuccess(coins));
            }catch (IOException e){
                handler.post(() -> onError(e));
            }
        });
    }
}
