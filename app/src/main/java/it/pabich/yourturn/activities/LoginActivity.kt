package it.pabich.yourturn.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import it.pabich.yourturn.R
import it.pabich.yourturn.db.DataBase
import it.pabich.yourturn.db.Errors
import it.pabich.yourturn.model.User
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignIn: GoogleSignInClient

    companion object {
        private const val RC_SIGN_IN = 500
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Init FireBase
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignIn = GoogleSignIn.getClient(this, gso)

        activity_login_btn_login_google.setOnClickListener {
            startLoading()
            val signInIntent = googleSignIn.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    public override fun onStart() {
        startLoading()
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                GlobalScope.launch {
                    val account = task.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account!!)
                }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                runOnUiThread {Snackbar.make(activity_login_rtl, "Authentication Failed.", Snackbar.LENGTH_SHORT).show() }
                updateUI(null)
            }
        }
    }

    private suspend fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        val dbUser = auth.signInWithCredential(credential).await()

        if (dbUser != null) {
            val userStatus = DataBase.checkIfUserHasAllData(auth.currentUser)

            if (userStatus == User.UserStatus.FULLY_REGISTERED)
                updateUI(auth.currentUser)
            else
                DataBase.registerUser(auth.currentUser, { updateUI(auth.currentUser)}, {error, s -> updateUI(error, s) })
        } else {
            runOnUiThread {Snackbar.make(activity_login_rtl, "Authentication Failed.", Snackbar.LENGTH_SHORT).show() }
            updateUI(null)
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        runOnUiThread {
            if (user != null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            stopLoading()
        }
    }

    private fun updateUI(error: Errors, errorMessage: String?) {
        Snackbar.make(activity_login_rtl, "Authentication Failed. ${error.name}: $errorMessage", Snackbar.LENGTH_SHORT).show()
    }

    private fun startLoading() {
        activity_login_rtl_loading.visibility = View.VISIBLE
    }

    private fun stopLoading() {
        activity_login_rtl_loading.visibility = View.GONE
    }
}
