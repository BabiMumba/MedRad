package cd.babitech.medrad.Unit

import com.google.firebase.auth.FirebaseAuth

object DATA {

    val PREF_NAME="DataUsers"
    val user = "users"
    val AUTH = FirebaseAuth.getInstance()
    val FIREBASE_USER = AUTH.currentUser
    val FirebaseUserUid = FIREBASE_USER!!.uid
    val id_user = FIREBASE_USER?.email?.substringBefore("@").toString()
    val nom = "nom"
    val mail = "mail"
    val numero = "numero"

}