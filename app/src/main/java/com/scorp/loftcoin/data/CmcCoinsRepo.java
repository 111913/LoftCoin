package com.scorp.loftcoin.data;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.schedulers.Schedulers;

@Singleton
class CmcCoinsRepo implements CoinsRepo {

    private final CmcApi api;
    private final LoftDatabase db;

    @Inject
    public CmcCoinsRepo(CmcApi api, LoftDatabase db){
        this.api = api;
        this.db = db;
    }

    @Override
    public Observable<List<Coin>> listings(@NonNull Query query) {

        return Observable
                .fromCallable(() -> query.forceUpdate() || db.coins().coinsCount() == 0)
                .switchMap((f) -> f ? api.listings(query.currency()) : Observable.empty())
                .map((listings) -> mapToRoomCoins(query, listings.data()))
                .doOnNext((coins) -> db.coins().insert(coins))
                .switchMap((coins) -> fetchFromDb(query))
                .switchIfEmpty(fetchFromDb(query))
                .<List<Coin>>map(ArrayList::new)
                .subscribeOn(Schedulers.io())
        ;

    }

    private ObservableSource<List<RoomCoin>> fetchFromDb(Query query) {
        if(query.sortBy() == SortBy.PRICE){
            return db.coins().fetchAllSortByPrice();
        }
        else {
            return db.coins().fetchAllSortByRank();
        }
    }

    private List<RoomCoin> mapToRoomCoins(Query query, List<? extends Coin> data){
        List<RoomCoin> roomCoins = new ArrayList<>(data.size());
        for (Coin coin : data) {
            roomCoins.add(RoomCoin.create(
                    coin.name(),
                    coin.symbol(),
                    coin.rank(),
                    coin.price(),
                    coin.change24h(),
                    query.currency(),
                    coin.id()
            ));
        }
        return roomCoins;
    }

}
