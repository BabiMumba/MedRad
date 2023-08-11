package cd.babitech.medrad.Auth.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import cd.babitech.medrad.MainActivity
import cd.babitech.medrad.Model.User
import cd.babitech.medrad.R
import cd.babitech.medrad.Unit.DATA
import cd.babitech.medrad.Unit.Void
import cd.babitech.medrad.databinding.ActivityRegisterBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()

        binding.loginBtn.setOnClickListener {
            if (ChampValide()){
                loginUser(binding.email.text.toString(),binding.password.text.toString())
            }


        }



    }
    private fun loginUser(email: String, password: String) {
        Void.loading(true,binding.progressBar,binding.loginBtn)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Connecté avec succès
                    saveUserData(email,binding.name.text.toString(),password,binding.numberPhone.text.toString())

                } else {
                    Void.loading(false,binding.progressBar,binding.loginBtn)
                    // Une erreur s'est produite lors de la connexion
                    Toast.makeText(
                        baseContext,
                        "Erreur de connexion : ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
    fun saveUserData(email: String, firstName: String,password: String,number: String) {
        val sdf = SimpleDateFormat("dd/M/yyyy HH:M")
        val date_dins = sdf.format(Date()).toString()
        val firestore = FirebaseFirestore.getInstance()
        val userDocument = firestore.collection(DATA.user).document(DATA.id_user)
        val profil = "https://e7.pngegg.com/pngimages/799/987/png-clipart-computer-icons-avatar-icon-design-avatar-heroes-computer-wallpaper-thumbnail.png"
        val user = User(firstName,email,number,password,Calendar.getInstance().time.toString(),"",profil)
        userDocument.set(user)
            .addOnSuccessListener {
                val user = user
                // Enregistrement réussi
                Void.loading(false,binding.progressBar,binding.loginBtn)
                Void.toas(this,"Compte creer")
                saveUserDataLocally(user)
                Void.Intent_page(this, MainActivity::class.java)

            }
            .addOnFailureListener {
                Void.loading(false,binding.progressBar,binding.loginBtn)
                // Erreur lors de l'enregistrement
                Log.d("FAILED","Erreur de connexion : ${it.message}")
            }
    }
    fun saveUserDataLocally(user: User?) {
        val sharedPreferences = getSharedPreferences(DATA.PREF_NAME, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        if (user != null) {
            val editor = sharedPreferences.edit()
            editor.putString("nom", user.nom)
            editor.putString("mail", user.mail)
            editor.putString("numero", user.numero)
            editor.putString(DATA.profil, user.profil)
            editor.apply()
            Void.loading(false,binding.progressBar,binding.loginBtn)
            Void.Intent_page(this,MainActivity::class.java)

        }
    }
    private fun  ChampValide():Boolean {
        return if (binding.name.text.length < 3 || binding.name.text.isEmpty() || binding.name.text.length > 15) {
            Void.toas(this, "Verifiez votre nom caractere min 3 max 15")
            false
        } else if (binding.email.text.toString().isEmpty()) {
            Void.toas(this, "Votre mail")
            false
        }  else if (binding.numberPhone.text.toString().isEmpty()) {
            Void.toas(this, "votre numero")
            false
        }else if (binding.password.text.length < 6 || binding.name.text.isEmpty()) {
            Void.toas(this, "mot de passe incomplet caractere min 6")
            false
        }else if (binding.passwordConfirm.text.isEmpty()) {
            Void.toas(this, "confirmer votre mot de passe")
            false
        } else if (binding.password.text.toString() != binding.passwordConfirm.text.toString()) {
            Void.toas(this, "mot de passe different")
            false
        }else {
            true
        }
    }

}
