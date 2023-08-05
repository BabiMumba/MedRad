package cd.babitech.medrad.Fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cd.babitech.medrad.MainActivity
import cd.babitech.medrad.Model.User
import cd.babitech.medrad.R
import cd.babitech.medrad.Unit.DATA
import cd.babitech.medrad.Unit.Void
import cd.babitech.medrad.databinding.FragmentProfilBinding
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

        return binding.root
    }
    fun getdata(){
        val sharedPreferences = requireActivity().getSharedPreferences(DATA.PREF_NAME, Context.MODE_PRIVATE)
        val nom  = sharedPreferences.getString(DATA.nom,"").toString()
        val numero  = sharedPreferences.getString(DATA.numero,"").toString()
        val mail  = sharedPreferences.getString(DATA.mail,"").toString()
        val adresse  = sharedPreferences.getString(DATA.adresse,"").toString()
        binding.name.setText(nom)
        binding.email.setText(mail)
        binding.numero.setText(numero)
        binding.adresse.setText(adresse)
    }
    fun updatedata(email: String, firstName: String,password: String,number: String){
        val firestore = FirebaseFirestore.getInstance()
        val user = User(firstName,email,number,password, Calendar.getInstance().time.toString(),"")
        val userDocument = firestore.collection(DATA.user).document(DATA.id_user)
        userDocument.set(user, SetOptions.merge())
            .addOnSuccessListener {
                val user = user
                // Enregistrement réussi
                Void.loading(false,binding.progressBar,binding.btnNext)
                Void.toas(requireActivity(),"Compte modifie")
                //saveUserDataLocally(user)
                Void.Intent_page(requireActivity(), MainActivity::class.java)

            }
            .addOnFailureListener {
                Void.loading(false,binding.progressBar,binding.btnNext)
                // Erreur lors de l'enregistrement
                Log.d("FAILED","Erreur de connexion : ${it.message}")
            }

    }

}
