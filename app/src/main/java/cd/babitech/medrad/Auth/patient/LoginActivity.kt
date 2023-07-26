package cd.babitech.medrad.Auth.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cd.babitech.medrad.R
import cd.babitech.medrad.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}