package com.scorp.loftcoin;

import android.content.Context;

import com.scorp.loftcoin.data.CoinsRepo;
import com.scorp.loftcoin.data.CurrencyRepo;
import com.scorp.loftcoin.util.Notifier;
import com.scorp.loftcoin.data.WalletsRepo;
import com.scorp.loftcoin.util.ImageLoader;
import com.scorp.loftcoin.util.RxSchedulers;

public interface BaseComponent {
    Context context();
    CoinsRepo coinsRepo();
    CurrencyRepo currencyRepo();
    WalletsRepo walletsRepo();
    ImageLoader imageLoader();
    RxSchedulers schedulers();
    Notifier notifier();
}
