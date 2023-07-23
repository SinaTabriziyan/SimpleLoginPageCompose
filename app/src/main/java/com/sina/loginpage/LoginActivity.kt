package com.sina.loginpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sina.loginpage.interfaces.LoginForm
import com.sina.loginpage.ui.theme.LoginPageTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                LoginForm()
            }
        }
    }
}