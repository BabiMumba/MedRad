package cd.babitech.medrad.Fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import cd.babitech.medrad.Activity.EditProfilActivity
import cd.babitech.medrad.MainActivity
import cd.babitech.medrad.Model.User
import cd.babitech.medrad.R
import cd.babitech.medrad.Unit.DATA
import cd.babitech.medrad.Unit.Void
import cd.babitech.medrad.databinding.FragmentProfilBinding
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.Calendar

class ProfilFragment : Fragment() {
    lateinit var binding: FragmentProfilBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfilBinding.inflate(layoutInflater)
        getdata()

        binding.editBtn.setOnClickListener {
            startActivity(Intent(requireActivity(),EditProfilActivity::class.java))
        }
       /* binding.btnNext.setOnClickListener {
            updatedata(binding.email.text.toString(),
                binding.name.text.toString(),
                "",
                binding.numero.text.toString(),
                binding.adresse.text.toString(),
                )
        }*/

        return binding.root
    }
    fun getdata(){
        val sharedPreferences = requireActivity().getSharedPreferences(DATA.PREF_NAME, Context.MODE_PRIVATE)
        val nom  = sharedPreferences.getString(DATA.nom,"").toString()
        val numero  = sharedPreferences.getString(DATA.numero,"").toString()
        val mail  = sharedPreferences.getString(DATA.mail,"").toString()
        val adresse  = sharedPreferences.getString(DATA.adresse,"").toString()
        val profil  = sharedPreferences.getString(DATA.profil,null).toString()

        binding.nameUser.setText(nom)
        binding.mailUser.setText(mail)
        binding.numeberUser.setText(numero)
        binding.adresse.setText(adresse)

        if (profil!=null){
            Glide
                .with(this)
                .load(profil)
                .centerInside()
                .into(binding.profilImage)
        }

    }
    override fun onResume() {
        getdata()
        super.onResume()
    }

}
