package cd.babitech.medrad.Auth.doctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
        binding.btnNext.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference(DATA.doctor)
            val id = ref.push().key.toString()
            Void.loading(true,binding.progressBar,binding.btnNext)
            val hashMap = HashMap<String?, Any?>()
            hashMap["nom"] = nom_complet.text.toString()
            hashMap["mail"] = mail.text.toString()
            hashMap["specialite"] = specialite
            hashMap["experiance"] = experiance.text.toString()
            hashMap["frais"] = frais.text.toString()
            hashMap["adresse"] = adres.text.toString()
            hashMap["clinique"] = clinique.text.toString()
            hashMap["langue"] = langue
            hashMap["biographie"] = biographie.text.toString()
            hashMap["horaire"] = horaire.text.toString()
            ref.child(id).setValue(hashMap)
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