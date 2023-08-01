package cd.babitech.medrad.Auth.patient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import cd.babitech.medrad.MainActivity
import cd.babitech.medrad.Model.User
import cd.babitech.medrad.R
import cd.babitech.medrad.Unit.DATA
import cd.babitech.medrad.Unit.Void
import cd.babitech.medrad.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.loginBtn.setOnClickListener {
            loginUser(binding.email.text.toString(),binding.password.text.toString())
        }

        binding.register.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

    }
    private fun loginUser(email: String, password: String) {
        Void.loading(true,binding.progressBar,binding.loginBtn)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Connecté avec succès
                    getData()


                } else {
                    Void.loading(false,binding.progressBar,binding.loginBtn)
                    // Une erreur s'est produite lors de la connexion
                    Log.d("FAILED","Erreur de connexion : ${task.exception?.message}",)
                    Toast.makeText(
                        baseContext,
                        "Erreur de connexion : ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun getData() {
        val userId = DATA.id_user
        db.collection(DATA.user)
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val user = document.toObject(User::class.java)
                    saveUserDataLocally(user)
                }
            }
            .addOnFailureListener { exception ->
                // Gérer les erreurs de récupération des données depuis Firebase Firestore
                Void.toas(this,"${exception.message}")

            }
    }

    private fun saveUserDataLocally(user: User?) {
        val sharedPreferences = getSharedPreferences(DATA.PREF_NAME, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        if (user != null) {
            val editor = sharedPreferences.edit()
            editor.putString("nom", user.nom)
            editor.putString("post_nom", user.postNom)
            editor.putString("prenom", user.prenom)
            editor.apply()
        }
    }
}