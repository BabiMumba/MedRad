package cd.babitech.medrad.Auth.doctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import cd.babitech.medrad.Model.doctormd
import cd.babitech.medrad.R
import cd.babitech.medrad.Unit.DATA
import cd.babitech.medrad.Unit.Void
import cd.babitech.medrad.databinding.ActivityRegisterDoctBinding
import com.google.firebase.database.FirebaseDatabase

class RegisterDoctActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterDoctBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterDoctBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val nom_complet = binding.name
        val mail = binding.mail
        val specialite = binding.specilisation.selectedItem.toString()
        val experiance = binding.experiance
        val frais = binding.frais
        val numero = binding.numberPhone
        val adres = binding.adresse
        val clinique = binding.hopital
        val langue = binding.langue.selectedItem.toString()
        val biographie = binding.biographie
        val horaire = binding.horaire
        val degree = binding.degree
        binding.btnNext.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference(DATA.doctor)
            val id = ref.push().key.toString()
            Void.loading(true,binding.progressBar,binding.btnNext)
            val profil = "https://www.flmedical.org/Florida/images/ImageLibrary/Membership/Physician%20Profiles/Black%20History%20Month%202022/bhm-main-thumbnail.jpg"
          val docteur_info =doctormd(nom_complet.text.toString(),
              degree.text.toString(),
              profil,
              experiance.text.toString(),
              numero.text.toString(),
              adres.text.toString(),
              langue,
              mail.text.toString(),
              frais.text.toString(),
              specialite,
              clinique.text.toString(),
              horaire.text.toString(),
              biographie.text.toString(),
              id,
          )
            ref.child(id).setValue(docteur_info)
                .addOnSuccessListener {
                    Toast.makeText(this, "compte cree", Toast.LENGTH_SHORT).show()
                    Void.loading(false,binding.progressBar,binding.btnNext)

                }
                .addOnFailureListener {
                    Void.loading(false,binding.progressBar,binding.btnNext)
                    Void.toas(this,"${it.message}")

                }

        }




    }
}