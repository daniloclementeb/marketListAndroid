package com.clemente.danilo.androiddevelopment.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.clemente.danilo.androiddevelopment.activity.R;
import com.clemente.danilo.androiddevelopment.activity.SplashActivity;
import com.clemente.danilo.androiddevelopment.dao.ListDAO;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MeuFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        //Verifica se a mensagem contém uma carga útil de dados
        if(remoteMessage.getData().size() > 0){
            //propaganda
            if (!remoteMessage.getData().get("item").isEmpty()) {
                String item = remoteMessage.getData().get("item");
                ListDAO dao = new ListDAO(getApplicationContext());
                if (dao.validaItem(item)) {
                    showNotification(remoteMessage.getData().get("descricao"),
                            remoteMessage.getData().get("descricao"));
                }

            } else {
                showNotification(remoteMessage.getData().get("descricao"),
                        remoteMessage.getData().get("descricao"));
            }

        }

        if(remoteMessage.getNotification() != null){
            showNotification(
                    remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody());
        }
    }

    private void showNotification(String titulo, String mensagem) {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(titulo)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(mensagem)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
