package com.scorp.loftcoin;

import android.content.Context;

import com.scorp.loftcoin.data.CoinsRepo;
import com.scorp.loftcoin.data.CurrencyRepo;

public interface BaseComponent {
    Context context();
    CoinsRepo coinsRepo();
    CurrencyRepo currencyRepo();
}
