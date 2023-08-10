package cd.babitech.medrad.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cd.babitech.medrad.Auth.patient.LoginActivity
import cd.babitech.medrad.R
import cd.babitech.medrad.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPatient.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }



    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}