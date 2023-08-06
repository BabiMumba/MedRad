package cd.babitech.medrad.Activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
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

        binding.rendezBtn.setOnClickListener {
            showDateTimeDialog()

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


}