package cd.babitech.medrad.Auth.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cd.babitech.medrad.R
import cd.babitech.medrad.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}