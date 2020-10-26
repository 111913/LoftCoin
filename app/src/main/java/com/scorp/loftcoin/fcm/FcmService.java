package com.scorp.loftcoin.fcm;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.scorp.loftcoin.BaseComponent;
import com.scorp.loftcoin.LoftApp;
import com.scorp.loftcoin.R;
import com.scorp.loftcoin.util.Notifier;
import com.scorp.loftcoin.ui.main.MainActivity;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class FcmService extends FirebaseMessagingService {

    private final CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    Notifier notifier;

    @Override
    public void onCreate() {
        super.onCreate();
        final BaseComponent baseComponent = ((LoftApp) getApplication()).getComponent();
        DaggerFcmComponent.builder().baseComponent(baseComponent).build().inject(this);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        final RemoteMessage.Notification notification = remoteMessage.getNotification();
        if(notification != null){
            disposable.add(notifier.sendMessage(
                    Objects.toString(notification.getTitle(), getString(R.string.default_channel_name)),
                    Objects.toString(notification.getBody(), "Somethings wrong!"),
                    MainActivity.class
            ).subscribe());
        }
        Timber.d("%s", notification);
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    @Override
    public void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }
}
