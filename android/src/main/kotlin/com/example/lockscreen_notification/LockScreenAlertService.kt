package com.example.lockscreen_notification

import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import org.json.JSONObject

class LockScreenAlertService: Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        createNotificationChannel()
        val notificationIntent = Intent(this, LockScreenNotificationActivity::class.java)

        if (intent?.hasExtra("start")==false&&intent?.hasExtra("content") == true){
            notificationIntent.putExtra("content",intent.getStringExtra("content"))

            val jsonData = JSONObject(intent.getStringExtra("content"))

            val json = JSONObject(jsonData.getString("data"))


            val message = json.getString("message")
            val title = json.getString("title")

            val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val notification: Notification = NotificationCompat.Builder(this, "LOCK_SCREEN_ALERT")
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.noti_icon)
                .setSound(null)
                .setCategory(NotificationCompat.CATEGORY_ERROR)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setFullScreenIntent(pendingIntent, true)
                .build()

            startForeground(2, notification)
        }
        else{
            val notification: Notification = NotificationCompat.Builder(this, "LOCK_SCREEN_ALERT")
                .setContentTitle("Ippopay Store app")
                .setContentText("Background Service is Running...")
                .setSmallIcon(R.drawable.noti_icon)
                .setSound(null)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            startForeground(1, notification)
        }

       return START_STICKY
    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
               "LOCK_SCREEN_ALERT",
                "Ippo Store" + "Service Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}