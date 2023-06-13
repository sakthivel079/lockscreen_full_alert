package com.example.lockscreen_notification

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import io.flutter.Log

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import java.lang.ref.WeakReference

/** LockscreenNotificationPlugin */
class LockscreenNotificationPlugin: FlutterPlugin, MethodCallHandler,ActivityAware {

  private lateinit var channel : MethodChannel
  var activity: Context?=null;
  var activityRef:Activity? = null;
  private var weakApplicationContext:  WeakReference<Context>? = null

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "lockscreen_notification")
    channel.setMethodCallHandler(this)
    weakApplicationContext = WeakReference(flutterPluginBinding.applicationContext)
    activity = weakApplicationContext?.get()
  }

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onMethodCall(call: MethodCall, result: Result) {
    when(call.method){
      "start"->{
        activity?.startForegroundService(Intent(activity, LockScreenAlertService::class.java).putExtra("start","started"));
        Log.e("service started","okay")
      }
      "dialog"->{
          val content = call.argument<String>("content")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          if (!Settings.canDrawOverlays(activity)) {
            val intent = Intent(
              Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse(
                "package:com.ippopay.store"
              )
            )
            activityRef?.startActivityForResult(intent, 0, null);
          }
        }

        activity?.startForegroundService(Intent(activity, LockScreenAlertService::class.java).putExtra("content",content));
        Log.e("service started","okay")

      }
    }
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    val act = WeakReference(binding.activity);
    activityRef = act.get()
  }

  override fun onDetachedFromActivityForConfigChanges() {
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {

  }

  override fun onDetachedFromActivity() {
  }
}
