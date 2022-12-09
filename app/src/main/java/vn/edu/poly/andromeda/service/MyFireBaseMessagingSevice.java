package vn.edu.poly.andromeda.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import vn.edu.poly.andromeda.MyAplication;
import vn.edu.poly.andromeda.R;
import vn.edu.poly.andromeda.fragment.NotificationFragment;

public class MyFireBaseMessagingSevice extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        RemoteMessage.Notification notification = message.getNotification();
        if(notification  == null){
            return;
        }
        String strTilte = notification.getTitle();
        String strMessage = notification.getBody();

        sendNotìication(strTilte,strMessage);

    }

    private void sendNotìication(String strTilte, String strMessage) {
        Intent intent = new Intent(this, NotificationFragment.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MyAplication.CHANNEL_ID)
                .setContentTitle(strTilte)
                .setContentText(strMessage)
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent);

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager != null){
            notificationManager.notify(1,notification);
        }

    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }
}
