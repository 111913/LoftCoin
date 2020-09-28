package com.scorp.loftcoin;

import android.content.Context;

import com.scorp.loftcoin.data.CoinsRepo;
import com.scorp.loftcoin.data.CurrencyRepo;
import com.scorp.loftcoin.util.ImageLoader;

public interface BaseComponent {
    Context context();
    CoinsRepo coinsRepo();
    CurrencyRepo currencyRepo();
    ImageLoader imageLoader();
}
