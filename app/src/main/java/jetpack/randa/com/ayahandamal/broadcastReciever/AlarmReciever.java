package jetpack.randa.com.ayahandamal.broadcastReciever;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import io.reactivex.functions.Consumer;
import jetpack.randa.com.ayahandamal.AyahAndAmalApplication;
import jetpack.randa.com.ayahandamal.IntentKeys;
import jetpack.randa.com.ayahandamal.R;
import jetpack.randa.com.ayahandamal.activity.Add3amalActivity;
import jetpack.randa.com.ayahandamal.model.Al3amalCombined;
import jetpack.randa.com.ayahandamal.viewModel.HomeViewModel;

import javax.inject.Inject;

import static jetpack.randa.com.ayahandamal.IntentKeys.CHANNEL_ID;

public class AlarmReciever extends BroadcastReceiver {
    @Inject
    HomeViewModel viewModel;

    @Override
    public void onReceive(Context context, Intent intent) {
        AyahAndAmalApplication mApplication = ((AyahAndAmalApplication)context.getApplicationContext());
        mApplication.getComponent().inject(this);

        if(intent.getExtras() != null ){
            viewModel.get3amal(intent.getLongExtra(IntentKeys.Al3AMAL_ID_FOR_ALARM,0))
                    .subscribe(al3amalCombined -> {
                        NotificationManager notificationManager =
                                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        Intent notificationIntent = new Intent(context, Add3amalActivity.class);
                        notificationIntent.putExtra(IntentKeys.COMBINED_3AMAL_OBJECT, al3amalCombined);
                        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                                .setSmallIcon(R.drawable.icon)
                                .setContentTitle(context.getString(R.string.reminder))
                                .setContentText(al3amalCombined.getAl3amal().getAl3amal())
                                .setAutoCancel(true)
                                .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_LIGHTS|Notification.DEFAULT_VIBRATE)
                                .setSound(defaultSoundUri)
                                .setContentIntent(contentIntent);
                        notificationManager.notify(1, notificationBuilder.build());
                    }, throwable -> {

                    });
        }
    }
}
