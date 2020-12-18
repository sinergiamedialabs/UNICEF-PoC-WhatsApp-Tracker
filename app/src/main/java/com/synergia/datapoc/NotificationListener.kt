package com.synergia.datapoc

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.text.DateFormat
import java.util.*

class NotificationListener : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        if (sbn?.packageName == "com.whatsapp") {
            val date = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(Date())
            val sender = sbn.notification.extras.getString("android.title")
            val msg = sbn.notification.extras.getString("android.text")
            if (!sender!!.contains("whatsapp", true)) {
                val messageLog=MessageLog(sender,date,msg!!)
                GlobalScope.launch {AppDatabase.getDatabase(applicationContext)!!.msgDao().insert(messageLog)  }

            }

        }
    }
}
