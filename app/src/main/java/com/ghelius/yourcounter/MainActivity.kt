package com.ghelius.yourcounter

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ghelius.yourcounter.presentation.theme.YourcounterTheme
import com.ghelius.yourcounter.presentation.ui.StartupScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ui = StartupScreen()
        setContent {
            YourcounterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ui.startupScreen("Android")
                }
            }
        }
        this.requestSmsPermission(this)
    }
    private fun requestSmsPermission(context: Activity) {
        val permission = android.Manifest.permission.RECEIVE_SMS
        val grant = ContextCompat.checkSelfPermission(context, permission)
        if (grant != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                context,
                arrayOf(permission),
                REQUEST_CODE_SMS_PERMISSION
            )
        }
    }

    companion object {
        private const val REQUEST_CODE_SMS_PERMISSION = 1
        private const val TAG = "MainActivity"
    }
}