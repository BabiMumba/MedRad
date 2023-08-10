package cd.babitech.medrad.Fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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
        getdata()
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
        binding.nameUser.setText(nom)
        binding.mailUser.setText(mail)
        binding.numeberUser.setText(numero)
        binding.adresse.setText(adresse)
    }

    /*fun updatedata(email: String, firstName: String,password: String,number: String,adress:String){
        Void.loading(true,binding.progressBar,binding.btnNext)
        val firestore = FirebaseFirestore.getInstance()
        val user = User(firstName,email,number,password, Calendar.getInstance().time.toString(),adress)
        val userDocument = firestore.collection(DATA.user).document(DATA.id_user)
        userDocument.set(user, SetOptions.merge())
            .addOnSuccessListener {
                val user = user
                // Enregistrement réussi
                Void.loading(false,binding.progressBar,binding.btnNext)
                Void.toas(requireActivity(),"Compte modifie")
                save_share(user)

            }
            .addOnFailureListener {
                Void.loading(false,binding.progressBar,binding.btnNext)
                // Erreur lors de l'enregistrement
                Log.d("FAILED","Erreur de connexion : ${it.message}")
            }

    }*/
    fun save_share(user: User){
        val sharedPreferences = requireActivity().getSharedPreferences(DATA.PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        if (user != null) {
            val editor = sharedPreferences.edit()
            editor.putString("nom", user.nom)
            editor.putString("mail", user.mail)
            editor.putString("numero", user.numero)
            editor.putString(DATA.adresse, user.address)
            editor.apply()
        }

    }

}
