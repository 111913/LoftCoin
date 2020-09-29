package com.scorp.loftcoin.data;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import com.google.auto.value.AutoValue;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface CoinsRepo {

    @NonNull
    Observable<List<Coin>> listings(@NonNull Query query);

    @NonNull
    Single<Coin> coin(Currency currency, long id);

    @AutoValue
    abstract class Query{

        @NonNull
        public static Builder builder(){
            return new AutoValue_CoinsRepo_Query.Builder()
                    .forceUpdate(true)
                    .sortBy(SortBy.RANK);
        }

        abstract String currency();
        abstract boolean forceUpdate();
        abstract SortBy sortBy();

        @AutoValue.Builder
        public abstract static class Builder{
            public abstract Builder currency(String currency);
            public abstract Builder forceUpdate(boolean forceUpdate);
            public abstract Builder sortBy(SortBy sortBy);
            public abstract Query build();
        }
    }
}
