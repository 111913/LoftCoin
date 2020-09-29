package com.scorp.loftcoin.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
class WalletsRepoImpl implements WalletsRepo {

    private final FirebaseFirestore firestore;
    private final CoinsRepo coinsRepo;

    @Inject
    public WalletsRepoImpl(CoinsRepo coinsRepo) {
        this.coinsRepo = coinsRepo;
        this.firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public Observable<List<Wallet>> wallets(@NonNull Currency currency) {

        return Observable
                .<QuerySnapshot>create(emitter -> {
                    final ListenerRegistration registration = firestore.collection("wallets")
                            .addSnapshotListener((snapshots, e) -> {
                                if(emitter.isDisposed()) return;
                                if(snapshots != null){
                                    emitter.onNext(snapshots);
                                }
                                else if(e != null){
                                    emitter.tryOnError(e);
                                }
                            });
                    emitter.setCancellable(() -> registration.remove());
                })
                .map(snapshots -> snapshots.getDocuments())
                .switchMapSingle((documents) -> Observable
                        .fromIterable(documents)
                        .switchMapSingle((document) -> coinsRepo
                                .coin(currency, Objects.requireNonNull(document.getLong("coinId"), "coinId"))
                                .map((coin) -> Wallet.create(
                                        document.getId(),
                                        coin,
                                        document.getDouble("balance")
                                ))
                        )
                        .toList()
                );
    }

    @NonNull
    @Override
    public Observable<List<Transaction>> transaction(@NonNull Wallet wallet) {
        return Observable.empty();
    }
}
