package cd.babitech.medrad.Auth.doctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cd.babitech.medrad.R
import cd.babitech.medrad.databinding.ActivityRegisterDoctBinding

class RegisterDoctActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterDoctBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterDoctBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}