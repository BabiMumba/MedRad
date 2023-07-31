package cd.babitech.medrad.Unit

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

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
}