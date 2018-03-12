package kulkarni.aditya.nowplayinghistory;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by maverick on 3/11/18.
 */

public class NowPlayingListenerService extends NotificationListenerService {
    private static final String TAG = "NowPlayingListener";
    private DatabaseManager databaseManager;
    private String CHANNEL_ID = "NowPlaying";
    private NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Inside on create");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "binded");
        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManagerCompat.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        databaseManager = new DatabaseManager(NowPlayingListenerService.this);
        Log.d(TAG, "onNotificationPosted");
        Log.d(TAG, "ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
        Log.d(TAG + "Title", sbn.getNotification().extras.getString("android.title"));
        if (sbn.getPackageName().equals("com.google.intelligence.sense")) {
            HashMap<String, String> map = new HashMap<>();
            map.put(Constants.SONG_TITLE, sbn.getNotification().extras.getString("android.title"));
            databaseManager.insertSong(map);

//            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(NowPlayingListenerService.this, CHANNEL_ID)
//                    .setSmallIcon(R.drawable.ic_notifications)
//                    .setContentTitle("New song added")
//                    .setContentText("Trying something new after a long time")
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//            notificationManager.notify(1, mBuilder.build());
            //TODO : Add action to notification
        }
    }
}
