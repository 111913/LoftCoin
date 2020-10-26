package com.scorp.loftcoin;

import android.app.Application;
import android.content.Context;

import com.scorp.loftcoin.data.CoinsRepo;
import com.scorp.loftcoin.data.CurrencyRepo;
import com.scorp.loftcoin.data.DataModule;
import com.scorp.loftcoin.util.UtilModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(
        modules = {
                AppModule.class,
                DataModule.class,
                UtilModule.class
        }
)
abstract class AppComponent implements BaseComponent {

    @Component.Builder
    static abstract class Builder{
        @BindsInstance
        abstract Builder application(Application app);
        abstract AppComponent build();
    }
}
