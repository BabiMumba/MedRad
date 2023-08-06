package cd.babitech.medrad.Activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import cd.babitech.medrad.R
import cd.babitech.medrad.databinding.ActivityDetailDoctorBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DetailDoctorActivity : AppCompatActivity() {
    private var selectedDate: Calendar = Calendar.getInstance()
    lateinit var binding: ActivityDetailDoctorBinding
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val doctor_id = intent.getStringExtra("doctor_id")

        if (doctor_id!=""){
            Toast.makeText(this, "$doctor_id", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "le lien est vide", Toast.LENGTH_SHORT).show()
        }

        binding.rendezBtn.setOnClickListener {
            showDateTimeDialog()
        }
        binding.toolbar.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.location.setOnClickListener {
            launchMaps()
        }


    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showDateTimeDialog() {
        val dateTimeDialog = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_date_time, null)
        val datePicker = dialogView.findViewById<DatePicker>(R.id.datePicker)
        val timePicker = dialogView.findViewById<TimePicker>(R.id.timePicker)

        dateTimeDialog.setView(dialogView)
        dateTimeDialog.setPositiveButton("Ok") { _, _ ->
            val selectedDate = "${datePicker.year}-${datePicker.month + 1}-${datePicker.dayOfMonth}"
            val selectedTime = "${timePicker.hour}:${timePicker.minute}"
            val result = "Rendez-vous pris le $selectedDate à $selectedTime"
            binding.horaireTxt.text = result
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


}