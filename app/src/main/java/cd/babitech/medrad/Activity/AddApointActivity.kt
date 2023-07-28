package cd.babitech.medrad.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cd.babitech.medrad.R
import cd.babitech.medrad.databinding.ActivityAddApointBinding

class AddApointActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddApointBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddApointBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}