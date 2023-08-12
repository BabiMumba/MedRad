package cd.babitech.medrad.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import cd.babitech.medrad.Auth.patient.LoginActivity
import cd.babitech.medrad.R
import cd.babitech.medrad.Unit.Void
import cd.babitech.medrad.databinding.ActivityAuthBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPatient.setOnClickListener {
            //startActivity(Intent(this,LoginActivity::class.java))
            sign_in()
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)



    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 120){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            Void.loading(false, binding.progressBar, binding.loginBtn)
            if (task.isSuccessful){
                try {
                    Void.loading(false, binding.progressBar, binding.loginBtn)
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account.idToken!!)
                }catch (e:Exception){
                    Void.loading(false, binding.progressBar, binding.loginBtn)
                    Toast.makeText(this, "$e", Toast.LENGTH_SHORT).show()
                }
            }else{
                Void.loading(false, binding.progressBar, binding.loginBtn)
                Toast.makeText(this, "erreur: ${task.exception}", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        Void.loading(true, binding.progressBar, binding.loginBtn)
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) {task ->
                if (task.isSuccessful){
                    startActivity(Intent(this, LoginActivity::class.java))
                }else{
                    Toast.makeText(this, "erreur:${task.exception}", Toast.LENGTH_SHORT).show()
                    Void.loading(false, binding.progressBar, binding.loginBtn)
                }
            }

    }
    private fun sign_in() {
        Void.loading(true, binding.progressBar, binding.loginBtn)
        val signiIntent = googleSignInClient.signInIntent
        startActivityForResult(signiIntent, 120)
    }
}