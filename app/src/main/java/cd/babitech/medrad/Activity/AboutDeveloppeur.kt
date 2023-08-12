package cd.babitech.medrad.Activity

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import cd.babitech.medrad.databinding.ActivityAboutDeveloppeurBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class AboutDeveloppeur : AppCompatActivity() {
    lateinit var binding: ActivityAboutDeveloppeurBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutDeveloppeurBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clicmethode()



    }
    fun clicmethode(){
        binding.btnCodeSource.setOnClickListener {
            val github_url = "https://github.com/BabiMumba/MedRad"
            val i = Intent(Intent.ACTION_VIEW)
            i.data= Uri.parse(github_url)
            startActivity(i)
        }
        binding.wtsp.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse("https://api.whatsapp.com/send?phone=243975937553")
            startActivity(i)
        }

        binding.gith.setOnClickListener {
            val url = "https://github.com/BabiMumba"
            val i = Intent(Intent.ACTION_VIEW)
            i.data=Uri.parse(url)
            startActivity(i)
        }
        binding.mailBrn.setOnClickListener {
            val i = Intent(Intent.ACTION_SENDTO)
            i.data = Uri.parse("mailto:babimumba243@gmail.com")
            startActivity(i)
        }
        binding.callBtn.setOnClickListener {
            Dexter.withContext(
                applicationContext
            )
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
                        val nume = "tel:+243975937553"
                        val i = Intent(Intent.ACTION_CALL)
                        i.data=Uri.parse(nume)
                        startActivity(i)
                    }

                    override fun onPermissionDenied(permissionDeniedResponse: PermissionDeniedResponse) {
                        Toast.makeText(
                            this@AboutDeveloppeur,
                            "vous devez accepter pour continuer",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissionRequest: PermissionRequest,
                        permissionToken: PermissionToken
                    ) {
                        permissionToken.continuePermissionRequest()
                    }
                }).check()
        }

        }
    }