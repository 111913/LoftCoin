package com.scorp.loftcoin.ui.wallets;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.scorp.loftcoin.BaseComponent;
import com.scorp.loftcoin.util.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        WalletsModule.class,
        ViewModelModule.class
}, dependencies = {
        BaseComponent.class
})
public abstract class WalletsComponent {

    abstract ViewModelProvider.Factory viewModelFactory();

    abstract WalletsAdapter walletsAdapter();

}
