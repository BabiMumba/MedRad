package cd.babitech.medrad.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cd.babitech.medrad.R
import cd.babitech.medrad.databinding.ActivityThankBinding

class ThankActivity : AppCompatActivity() {
    lateinit var binding: ActivityThankBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThankBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toto.ivBack.setOnClickListener {
            onBackPressed()
        }
        val title = intent.getStringExtra("title")
        val message = intent.getStringExtra("message")


        binding.toto.titreTopBar.setText(title)
        binding.txtThank.text = message

    }
}