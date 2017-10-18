package cariteman.hans.cariteman;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMessagingServce";

    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d hh:mm a");

    private SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // String notificationTitle = null, notificationBody = null;

        SimpleDateFormat sdfDay = new SimpleDateFormat("EEEE");
        SimpleDateFormat sdfMonth = new SimpleDateFormat("MMMM");
//        newEvent.setDateResponse(sdfDay.format(eventDate) + ", "+ sdfMonth.format(eventDate) + " "

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            // Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            // notificationTitle = remoteMessage.getNotification().getTitle();
            // notificationBody = remoteMessage.getNotification().getBody();
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        // sendNotification(notificationTitle, notificationBody);

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            try {
                pushNotification(remoteMessage.getData().get("name") + " - " + remoteMessage.getData().get("category"),
                        "at " +
                                remoteMessage.getData().get("location") + " on " +
                                dateFormat.format(datetimeFormat.parse(remoteMessage.getData().get("date"))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
//                            remoteMessage.getData().get("date"));
        }
    }

    private void pushNotification(String notificationTitle, String notificationBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setContentTitle(notificationTitle)
                .setContentText(notificationBody)
                .setSound(defaultSoundUri);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}