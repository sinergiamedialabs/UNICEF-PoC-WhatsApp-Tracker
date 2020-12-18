package com.synergia.datapoc

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.io.File

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkNotificationStatus()
    }

    private fun checkNotificationStatus() {
        if (Settings.Secure.getString(this.contentResolver, "enabled_notification_listeners").contains(applicationContext.packageName)) {
            AppDatabase.getDatabase(this)!!.msgDao().getAll().observe(this, Observer { it ->
                if (it.isNotEmpty()) {
                    val data=it as ArrayList<MessageLog>
                    msg_log_recycler_view.layoutManager = LinearLayoutManager(this)
                    msg_log_recycler_view.setHasFixedSize(true)
                    msg_log_recycler_view.adapter = MsgLogAdapter(data)
                }
            })
        } else {
            /*AlertDialogHelper.showDialog(
                this@MainActivity,
                "Enable Special Access",
                "Need Notification Access",
                "Ok",
                null,
                DialogInterface.OnClickListener { dialog, _ ->
                    //service is not enabled try to enabled by calling...
                    startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
                    dialog.cancel()
                   this.finish()
                }
            )*/


        }
    }


}