package cd.babitech.medrad.Model

data class rende_vous(
    val id_rens:String= "",
    val id_user:String= "",
    val id_docteur:String= "",
    val date_rendev:String= "",
    val profil_docteur:String= "",
    val nom_docteur:String= "",
    val domaine:String= "",
    val date_env:String= "",
    val etat_rend:Boolean= false,

)