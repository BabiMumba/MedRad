package cd.babitech.medrad.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
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
import com.google.firebase.storage.StorageReference
import java.util.Calendar

class EditProfilActivity : AppCompatActivity() {
    var filepath: Uri? = null
    var ex_path:Uri? = null
    var lien_image:String = ""
    private lateinit var storageReference: StorageReference
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
        binding.pickProfilTxt.setOnClickListener {
            Void.pick_image(this,512,512,2000)
        }

        binding.btnNextSave.setOnClickListener {
            updatedata(
                binding.mail.text.toString(),
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
        val profil  = sharedPreferences.getString(DATA.profil,"").toString()
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
    //on activity result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imageUri : Uri
        if (requestCode == 101 && resultCode == RESULT_OK) {
            imageUri = data?.data!!
            filepath = data.data!!
            binding.imageProfil.setImageURI(imageUri)
            binding.pickProfilTxt.visibility = View.GONE
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