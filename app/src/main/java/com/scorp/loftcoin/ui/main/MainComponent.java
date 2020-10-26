package com.scorp.loftcoin.ui.main;

import android.content.SharedPreferences;

import com.scorp.loftcoin.BaseComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                MainModule.class
        },
        dependencies = {
                BaseComponent.class
        }
)
public abstract class MainComponent {

        abstract void inject(MainActivity activity);
}
