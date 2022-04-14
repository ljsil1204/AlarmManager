package com.leejinsil.alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.leejinsil.alarmmanager.Const.Companion.NOTIFICATION_ID

class BootComleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action == "android.intent.action.BOOT_COMPLETED"){

            Log.d("BootComlete", "부팅완료")

//          AlarmManager 생성
            val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

//          PendingIntent 생성
            val receiverIntent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, receiverIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                MainActivity.mCalendar.timeInMillis,
                pendingIntent
            )

        }

    }

}