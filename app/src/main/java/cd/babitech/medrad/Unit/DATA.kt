package cd.babitech.medrad.Unit

import com.google.firebase.auth.FirebaseAuth

object DATA {

    val AUTH = FirebaseAuth.getInstance()
    val FIREBASE_USER = AUTH.currentUser
    val FirebaseUserUid = FIREBASE_USER!!.uid
    val id_user = FIREBASE_USER?.email?.substringBefore("@").toString()

}