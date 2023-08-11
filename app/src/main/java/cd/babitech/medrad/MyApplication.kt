package cd.babitech.medrad

import android.app.Application
import android.text.format.DateFormat
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    companion object {
        fun formatTimestamp(timestamp: Long): String {
            val calendar = Calendar.getInstance(Locale.FRENCH)
            calendar.timeInMillis = timestamp
            return DateFormat.format("dd/MM/yyyy", calendar).toString()
        }
    }
}