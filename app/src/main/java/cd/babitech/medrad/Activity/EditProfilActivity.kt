package cd.babitech.medrad.Activity

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
import cd.babitech.medrad.Model.User
import cd.babitech.medrad.R
import cd.babitech.medrad.Unit.DATA
import cd.babitech.medrad.Unit.Void
import cd.babitech.medrad.databinding.ActivityEditProfilBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.Calendar

class EditProfilActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditProfilBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getdata()
        binding.toolbar.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.toolbar.titreTopBar.setText("Modifier votre compte")

        binding.btnNextSave.setOnClickListener {
            updatedata(
                binding.name.text.toString(),
                binding.name.text.toString(),
                "",
                binding.numberPhone.text.toString(),
                binding.adresse.text.toString(),
            )
        }
    }
    fun getdata(){
        val sharedPreferences = this.getSharedPreferences(DATA.PREF_NAME, Context.MODE_PRIVATE)
        val nom  = sharedPreferences.getString(DATA.nom,"").toString()
        val numero  = sharedPreferences.getString(DATA.numero,"").toString()
        val mail  = sharedPreferences.getString(DATA.mail,"").toString()
        val adresse  = sharedPreferences.getString(DATA.adresse,"").toString()
        binding.name.setText(nom)
        binding.numberPhone.setText(numero)
        binding.mail.setText(mail)
        binding.adresse.setText(adresse)

    }

    fun updatedata(email: String, firstName: String,password: String,number: String,adress:String){
        Void
        Void.loading(true,binding.progressBar,binding.btnNextSave)
        val firestore = FirebaseFirestore.getInstance()
        val user = User(firstName,email,number,password, Calendar.getInstance().time.toString(),adress)
        val userDocument = firestore.collection(DATA.user).document(DATA.id_user)
        userDocument.set(user, SetOptions.merge())
            .addOnSuccessListener {
                val user = user
                // Enregistrement réussi
                Void.loading(false,binding.progressBar,binding.btnNextSave)
                Void.toas(this,"Compte modifie")
                save_share(user)

            }
            .addOnFailureListener {
                Void.loading(false,binding.progressBar,binding.btnNextSave)
                // Erreur lors de l'enregistrement
                Log.d("FAILED","Erreur de connexion : ${it.message}")
            }

    }
    fun save_share(user: User){
        val sharedPreferences = this.getSharedPreferences(DATA.PREF_NAME, AppCompatActivity.MODE_PRIVATE)
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