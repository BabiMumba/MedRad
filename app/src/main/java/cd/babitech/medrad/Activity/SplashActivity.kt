package cd.babitech.medrad.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import cd.babitech.medrad.Auth.AuthActivity
import cd.babitech.medrad.MainActivity
import cd.babitech.medrad.R
import cd.babitech.medrad.Unit.Void
import cd.babitech.medrad.databinding.ActivitySplashBinding
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        //supportActionBar!!.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            checkUser()
        },3000)

    }
    private fun checkUser() {
        val AUTH = FirebaseAuth.getInstance()
        val firebaseUser = AUTH.currentUser
        if (firebaseUser == null) {
            Void.Intent_page(this,AuthActivity::class.java)
        } else {
            Void.Intent_page(this,MainActivity::class.java)
        }
        finish()
    }
}