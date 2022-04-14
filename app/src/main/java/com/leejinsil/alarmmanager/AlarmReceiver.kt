package com.leejinsil.alarmmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.leejinsil.alarmmanager.Const.Companion.CHANNEL_ID
import com.leejinsil.alarmmanager.Const.Companion.NOTIFICATION_ID

class AlarmReceiver : BroadcastReceiver() {

    lateinit var notificationManager : NotificationManager

    override fun onReceive(context: Context?, intent: Intent?) {

        notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
        deliverNotification(context)

    }

    fun createNotificationChannel(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ) {

            val notificationCannel = NotificationChannel(CHANNEL_ID, "채널이름", NotificationManager.IMPORTANCE_HIGH)

            notificationCannel.enableLights(true) //불빛
            notificationCannel.lightColor = Color.RED // 색상
            notificationCannel.enableVibration(true) //진동여부
            notificationCannel.description = "채널 정보입니다." // 상세내용

            notificationManager.createNotificationChannel(notificationCannel) // 채널등록

        }

    }

    fun deliverNotification(context: Context){

        val contentIntent = Intent(context, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.alarm)
            .setContentTitle("제목")
            .setContentText("내용입니다.")
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        notificationManager.notify(NOTIFICATION_ID, builder.build())

    }

}