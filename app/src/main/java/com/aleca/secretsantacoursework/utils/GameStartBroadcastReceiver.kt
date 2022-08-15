package com.aleca.secretsantacoursework.utils

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import com.aleca.secretsantacoursework.R

class GameStartBroadcastReceiver : BroadcastReceiver(){

    //    private var contactDetailId: Int = -1
//    private lateinit var nameContact: String
//    private lateinit var notificationManager: NotificationManager
//
//    companion object {
//        const val CHANNEL_ID = "channelID"
//    }
//
//    override fun onReceive(context: Context, intent: Intent) {
//        contactDetailId = intent.getIntExtra(ID, -1)
//        nameContact = intent.getStringExtra(TEXT_NOTIFICATION).orEmpty()
//        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(
//                CHANNEL_ID,
//                context.getString(R.string.name_notification_channel),
//                importance
//            ).apply {
//                description = context.getString(R.string.description_notification)
//            }
//            notificationManager.createNotificationChannel(channel)
//        }
//        val intentReply = getIntentActionNotification(context, intent)
//        createAlarm(context, intentReply)
//    }
//
//    private fun getIntentActionNotification(context: Context, intent: Intent): Intent {
//        //Интент для активити, которую мы хотим запускать при нажатии на уведомление
//        val intentReply = Intent(context, MainActivity::class.java)
//        intentReply.apply {
//            putExtra(CONTACT_DETAIL_ID, intent.getIntExtra(ID, -1))
//        }
//        val pendingIntent = PendingIntent.getActivity(context, 0, intentReply, FLAG_UPDATE_CURRENT)
//        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
//            .setSmallIcon(R.drawable.icon_notificate)
//            .setContentTitle(context.getString(R.string.title_notification))
//            .setContentText(String.format("%s %s", context.getString(R.string.text_notification), nameContact))
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setAutoCancel(true)
//            .setContentIntent(pendingIntent)
//        notificationManager.notify(contactDetailId, builder.build())
//        return intentReply
//    }
//
//    private fun createAlarm(context: Context, intentReply: Intent) {
//        val calendar: Calendar = Calendar.getInstance()
//        calendar.timeInMillis = System.currentTimeMillis()
//        val pendingIntentRepeatNotification = PendingIntent.getBroadcast(context, contactDetailId, intentReply, FLAG_UPDATE_CURRENT)
//        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
//        alarmManager.setRepeating(
//            AlarmManager.RTC_WAKEUP,
//            calendar.timeInMillis,
//            INTERVAL_DAY * INTERVAL_YEAR,
//            pendingIntentRepeatNotification
//        )
//    }
//
//    fun cancelAlarmNotificationContact(context: Context, intent: Intent) {
//        val contactDetailId = intent.getIntExtra(ID, -1)
//        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
//        val pendingIntent = PendingIntent.getBroadcast(
//            context,
//            contactDetailId, intent,
//            FLAG_UPDATE_CURRENT
//        )
//        alarmManager.cancel(pendingIntent)
//    }
    override fun onReceive(context: Context?, intent: Intent?) {
        TODO("Not yet implemented")
    }
}