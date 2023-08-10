package cd.babitech.medrad.Activity


import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import cd.babitech.medrad.R
import cd.babitech.medrad.databinding.ActivityNotificationBinding
import com.bumptech.glide.Glide


class NotificationActivity : AppCompatActivity() {
    lateinit var binding: ActivityNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this).asGif().load(R.raw.vide).into(binding.emptyListe)
        binding.toto.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.toto.titreTopBar.setText("Notification")


    }
}