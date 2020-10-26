package com.scorp.loftcoin.ui.rates;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.scorp.loftcoin.data.Coin;
import com.scorp.loftcoin.data.CoinsRepo;
import com.scorp.loftcoin.data.FakeCoin;
import com.scorp.loftcoin.data.TestCoinRepo;
import com.scorp.loftcoin.data.TestCurrencyRepo;
import com.scorp.loftcoin.util.TestRxSchedulers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import io.reactivex.observers.TestObserver;

import static com.google.common.truth.Truth.assertThat;


@RunWith(AndroidJUnit4.class)
public class RatesViewModelTest {

    private TestCoinRepo coinRepo;
    private TestCurrencyRepo currencyRepo;

    RatesViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        currencyRepo = new TestCurrencyRepo(ApplicationProvider.getApplicationContext());
        coinRepo = new TestCoinRepo();

        viewModel = new RatesViewModel(coinRepo, currencyRepo, new TestRxSchedulers());
        
    }

    @Test
    public void coins_test() {
        final TestObserver<List<Coin>> coinsTest = viewModel.coins().test();
        viewModel.isRefreshing().test().assertValue(true);
        final List<Coin> coins = Arrays.asList(new FakeCoin(), new FakeCoin());
        coinRepo.listings.onNext(coins);
        viewModel.isRefreshing().test().assertValue(false);
        coinsTest.assertValue(coins);

        CoinsRepo.Query query = coinRepo.lastListingsQuery;
        assertThat(query).isNotNull();
        assertThat(query.forceUpdate()).isTrue();

        viewModel.switchSortingOrder();

        viewModel.isRefreshing().test().assertValue(false);
        coinRepo.listings.onNext(coins);
        viewModel.isRefreshing().test().assertValue(false);

        query = coinRepo.lastListingsQuery;
        assertThat(query).isNotNull();
        assertThat(query.forceUpdate()).isFalse();
    }
}