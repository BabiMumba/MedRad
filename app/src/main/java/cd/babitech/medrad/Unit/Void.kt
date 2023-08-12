package cd.babitech.medrad.Unit

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import cd.babitech.medrad.R
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.button.MaterialButton

object Void {

    fun toas(contex:Context,message:String){
        Toast.makeText(contex, message, Toast.LENGTH_SHORT).show()
    }
    fun Intent_page(context: Context, c: Class<*>?) {
        val intent = Intent(context, c)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
    fun loading(isLoading: Boolean, progressBar: ProgressBar, btn: View) {
        if (isLoading) {
            btn.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE

        } else {
            btn.visibility = View.VISIBLE
            progressBar.visibility = View.INVISIBLE
        }
    }

    fun pick_image(context: Activity, hauteur:Int, largeur:Int, taille:Int) {
        ImagePicker.Companion.with(context)
            .crop() //Crop image(Optional), Check Customization for more option
            .compress(taille) //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                largeur,
                hauteur
            ) //Final image resolution will be less than 1080 x 1080(Optional)
            .start(101)
    }
/*
    fun DialogSucce(context: Context){
        val dateTimeDialog = AlertDialog.Builder(context)
        val dialogView = layoutInflater.inflate(R.layout.dialog_date_time, null)
        val datePicker = dialogView.findViewById<DatePicker>(R.id.datePicker)
        val timePicker = dialogView.findViewById<TimePicker>(R.id.timePicker)
        dateTimeDialog.setView(dialogView)
    }
*/

    fun succes_app(context: Context?, file: Int,message: String) {
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.succes_dialogue)
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val lp: WindowManager.LayoutParams = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        //Glide.with(this).asGif().load(R.raw.empty_cal).into(binding.emptyListe)
        val image = dialog.findViewById<ImageView>(R.id.succe_image)
        Glide.with(context).asGif().load(file).into(image)
        dialog.findViewById<TextView>(R.id.txt_ope).setText(message)
        dialog.findViewById<MaterialButton>(R.id.exit_btn).setOnClickListener {
            dialog.dismiss()
        }
        //dialog.findViewById<View>(R.id.no).setOnClickListener { dialog.cancel() }
        dialog.show()
        dialog.window!!.attributes = lp
    }
}