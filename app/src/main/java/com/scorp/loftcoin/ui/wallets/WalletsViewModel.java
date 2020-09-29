package com.scorp.loftcoin.ui.wallets;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.scorp.loftcoin.data.CurrencyRepo;
import com.scorp.loftcoin.data.Wallet;
import com.scorp.loftcoin.data.WalletsRepo;
import com.scorp.loftcoin.util.RxSchedulers;

import java.util.List;

import javax.inject.Inject;

import dagger.Binds;
import io.reactivex.Observable;

public class WalletsViewModel extends ViewModel {

    private final Observable<List<Wallet>> wallets;
    private final RxSchedulers schedulers;

    @Inject
    WalletsViewModel(WalletsRepo walletsRepo, CurrencyRepo currencyRepo, RxSchedulers schedulers) {
        this.schedulers = schedulers;
        wallets = currencyRepo.currency().switchMap(currency -> walletsRepo.wallets(currency));
    }

    @NonNull
    Observable<List<Wallet>> wallets(){
        return wallets.observeOn(schedulers.main());
    }
}
