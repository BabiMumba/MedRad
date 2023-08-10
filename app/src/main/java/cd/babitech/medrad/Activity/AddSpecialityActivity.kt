package cd.babitech.medrad.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cd.babitech.medrad.Model.specialite
import cd.babitech.medrad.R
import cd.babitech.medrad.Unit.DATA
import cd.babitech.medrad.Unit.Void
import cd.babitech.medrad.databinding.ActivityAddSpecialityBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class AddSpecialityActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddSpecialityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSpecialityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            val ref = FirebaseFirestore.getInstance().collection(DATA.special)
            val id = System.currentTimeMillis().toString()
            Void.loading(true,binding.progressBar,binding.btnNext)
            val hashMap = HashMap<String?, Any?>()
            hashMap["domaine"] = "${binding.specialitEdtxt.text}"
            hashMap["image"] = "https://cdn-icons-png.flaticon.com/128/3270/3270928.png"
         //   val specilite = specialite("","")
            ref.document("Domaine$id").set(hashMap)
                .addOnSuccessListener {
                    Void.loading(false,binding.progressBar,binding.btnNext)
                    binding.specialitEdtxt.setText("")
                }
                .addOnFailureListener {
                    Void.loading(false,binding.progressBar,binding.btnNext)
                    Void.toas(this,"${it.message}")

                }
        }



    }
}