package com.scorp.loftcoin.data;

import androidx.annotation.NonNull;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Currency {

    public abstract String symbol();

    public abstract String code();

    public abstract String name();

    @NonNull
    static Currency create(String symbol, String code, String name) {
        return new AutoValue_Currency(symbol, code, name);
    }
}
