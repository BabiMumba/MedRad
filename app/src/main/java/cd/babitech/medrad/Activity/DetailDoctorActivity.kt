package cd.babitech.medrad.Activity

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import cd.babitech.medrad.Model.rende_vous
import cd.babitech.medrad.R
import cd.babitech.medrad.Unit.DATA
import cd.babitech.medrad.Unit.Void
import cd.babitech.medrad.databinding.ActivityDetailDoctorBinding
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class DetailDoctorActivity : AppCompatActivity() {
    private var selectedDate: Calendar = Calendar.getInstance()
    lateinit var binding: ActivityDetailDoctorBinding
    lateinit var daterende:String
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val doctor_id = intent.getStringExtra("doctor_id").toString()
        val nom_doctor = intent.getStringExtra("name").toString()
        val numero = intent.getStringExtra("numero")
        val image_profil = intent.getStringExtra("profil").toString()
        val categorie = intent.getStringExtra("categorie").toString()
        val degree = intent.getStringExtra("degree")
        val exeperiance = intent.getStringExtra("experiance")

        if (doctor_id!=null || doctor_id!=""){
           // Toast.makeText(this, "$doctor_id", Toast.LENGTH_SHORT).show()
            binding.nameDoctor.text = "$nom_doctor"
            binding.toolbar.titreTopBar.text = "$nom_doctor"
            Glide
                .with(this)
                .load(image_profil)
                .into(binding.profilDoctor)
            binding.categorie.text = "Categorie: $categorie"
            binding.experianceDoc.text = "Experiance: $exeperiance ans"


        }

        binding.callBtn.setOnClickListener {
            call_app()
        }
        binding.rendezBtn.setOnClickListener {
            val time_date = binding.timeDatesele.text.toString()
            showDateTimeDialog(doctor_id,image_profil,nom_doctor,categorie,time_date)
        }
        binding.toolbar.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.location.setOnClickListener {
            launchMaps()
        }

    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun showDateTimeDialog(id_doctor:String,profil:String,nom:String,domaine:String,date:String) {
        val dateTimeDialog = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_date_time, null)
        val datePicker = dialogView.findViewById<DatePicker>(R.id.datePicker)
        val timePicker = dialogView.findViewById<TimePicker>(R.id.timePicker)
        dateTimeDialog.setView(dialogView)
        dateTimeDialog.setPositiveButton("Ok") { _, _ ->
            val selectedDate = "${datePicker.dayOfMonth}-${datePicker.month + 1}-${datePicker.year}"
            val selectedTime = "${timePicker.hour}:${timePicker.minute}"
            val result = "$selectedDate à $selectedTime"
            daterende= result
            binding.timeDatesele.text = result
            val dialogue =AlertDialog.Builder(this)
            dialogue.setTitle("Rendez-Vous")
            dialogue.setMessage("Confirmer la transaction")
            dialogue.setPositiveButton("Ok") { a, b ->
                sendData(id_doctor,profil,nom,domaine,daterende)
                a.dismiss()
            }
            dialogue.setNegativeButton("Annuler", null)
            dialogue.show()
        }
        dateTimeDialog.setNegativeButton("Annuler", null)

        dateTimeDialog.show()
    }

    fun launchMaps() {
       // //((-11.687933, 27.489543), 27.489543)
        val latitude = -11.687933 // Votre latitude
        val longitude = 27.489543 // Votre longitude
        val uri = Uri.parse("geo:$latitude,$longitude")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }
    fun call_app() {
        Dexter.withContext(
            this
        )
            .withPermission(Manifest.permission.CALL_PHONE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
                    val nume = "tel:+243975937553"
                    val i = Intent(Intent.ACTION_CALL)
                    i.data = Uri.parse(nume)
                    startActivity(i)
                }

                override fun onPermissionDenied(permissionDeniedResponse: PermissionDeniedResponse) {
                    Toast.makeText(
                        this@DetailDoctorActivity,
                        "vous devez accepter pour continuer",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissionRequest: PermissionRequest,
                    permissionToken: PermissionToken
                ) {
                    permissionToken.continuePermissionRequest()
                }
            }).check()
    }
    fun sendData(id_doctor:String,profil:String,nom:String,domaine:String,date:String){
        Void.loading(true,binding.progressBar,binding.rendezBtn)
        val sdf = SimpleDateFormat("dd/M/yyyy HH:M")
        val date_id = SimpleDateFormat("dd-M-yyyy-HH-M")
        val date_dins = sdf.format(Date()).toString()
        val firestore = FirebaseFirestore.getInstance()
        val id = System.currentTimeMillis().toString()

        val mRef = firestore.collection(DATA.rendeVous).document(DATA.id_user+id)
        val rendezvous = rende_vous(id,DATA.id_user,id_doctor,date,profil,nom,domaine,date_dins,false)
        mRef.set(rendezvous)
            .addOnSuccessListener {
                Void.loading(false,binding.progressBar,binding.rendezBtn)
                val file = R.raw.confirm_calendar
               // Void.toas(this,"Rendez-vous ajouter")
                val message= "Rendez-vous ajouter"
                Void.succes_app(this,file,message)
            }
            .addOnFailureListener {
                Void.loading(false,binding.progressBar,binding.rendezBtn)
                // Erreur lors de l'enregistrement
                Log.d("FAILED","Erreur de connexion : ${it.message}")
            }

    }
}