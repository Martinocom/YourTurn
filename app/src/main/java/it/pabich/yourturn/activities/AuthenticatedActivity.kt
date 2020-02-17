package it.pabich.yourturn.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import it.pabich.yourturn.db.DataBase
import it.pabich.yourturn.model.User

abstract class AuthenticatedActivity : AppCompatActivity() {

    fun onUserNotAuthenticated() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun launchAuthenticated(operation: () -> Unit) {
        if (DataBase.checkConnection() == User.UserStatus.AUTHENTICATED) {
            operation()
        } else {
            onUserNotAuthenticated()
        }
    }
}