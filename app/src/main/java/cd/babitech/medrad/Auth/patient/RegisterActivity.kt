package cd.babitech.medrad.Auth.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import cd.babitech.medrad.MainActivity
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
            loginUser(binding.email.text.toString(),binding.password.text.toString())

        }



    }
    private fun loginUser(email: String, password: String) {
        Void.loading(true,binding.progressBar,binding.loginBtn)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Void.loading(false,binding.progressBar,binding.loginBtn)
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
        val userDocument = firestore.collection("users").document(DATA.id_user)

        val userData = hashMapOf(
            "nom" to firstName,
            "mail" to email,
            "mot de passe" to password,
            "numero" to number,
            "date" to date_dins,
            "heure" to Calendar.getInstance().time
        )

        userDocument.set(userData)
            .addOnSuccessListener {
                // Enregistrement réussi
                Void.toas(this,"Compte creer")
                Void.Intent_page(this, MainActivity::class.java)

            }
            .addOnFailureListener {
                // Erreur lors de l'enregistrement
                Log.d("FAILED","Erreur de connexion : ${it.message}")
            }
    }
}