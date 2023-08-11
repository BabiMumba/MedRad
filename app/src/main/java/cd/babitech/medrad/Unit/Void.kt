package cd.babitech.medrad.Unit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.github.dhaval2404.imagepicker.ImagePicker

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
}