package cd.babitech.medrad.Auth.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import cd.babitech.medrad.MainActivity
import cd.babitech.medrad.R
import cd.babitech.medrad.Unit.Void
import cd.babitech.medrad.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
                    Void.toas(this,"Compte creer")
                    Void.Intent_page(this, MainActivity::class.java)

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
}