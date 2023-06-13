package com.example.lockscreen_notification

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.lockscreen_notification.databinding.ActivityLockScreenNotificationBinding
import org.json.JSONObject

class LockScreenNotificationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock_screen_notification)

        if (intent?.hasExtra("content") == true){


            val jsonString = intent.getStringExtra("content")
            Log.e("data is",jsonString.toString())

            val jsonData = JSONObject(jsonString)
            val json = JSONObject(jsonData.getString("data"))
            val amount = json.getString("amount")
            val transId = json.getString("trans_id")

            val amountText = findViewById<TextView>(R.id.amountText);
            val transIdText = findViewById<TextView>(R.id.transIdText);
            val submitButton = findViewById<Button>(R.id.submitButton);


            amountText.text= "₹$amount"
                    transIdText.text="Transaction Id :$transId"
                    submitButton.setOnClickListener{
                        finish()
//                        stopService(Intent(this@LockScreenNotificationActivity,
//                            LockScreenAlertService::class.java))
                    }

        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(false)
            setTurnScreenOn(false)
        } else {
            window.clearFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
            )
        }
    }
    
}