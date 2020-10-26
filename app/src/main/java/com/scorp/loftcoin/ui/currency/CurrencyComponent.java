package com.scorp.loftcoin.ui.currency;

import androidx.lifecycle.ViewModelProvider;

import com.scorp.loftcoin.BaseComponent;
import com.scorp.loftcoin.util.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        CurrencyModule.class,
        ViewModelModule.class
}, dependencies = {
        BaseComponent.class
})
abstract class CurrencyComponent {

    abstract ViewModelProvider.Factory viewModelFactory();

}
