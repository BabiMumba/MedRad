package cd.babitech.medrad.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import cd.babitech.medrad.Activity.FiltreActivity
import cd.babitech.medrad.Model.specialite
import cd.babitech.medrad.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class Speciality(var userList: ArrayList<specialite?>) : RecyclerView.Adapter<Speciality.UserViewHolder>() {

    // ViewHolder pour afficher chaque élément de la liste
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nom_domaine)
        val imageView: ImageView = itemView.findViewById(R.id.image_sp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.specialitse_item, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]

        val circularProgressDrawable = CircularProgressDrawable(holder.itemView.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        holder.nameTextView.text = currentUser!!.domaine
        holder.itemView.setOnClickListener {
            //passer les données à l'activité suivante
            val intent = Intent(holder.itemView.context, FiltreActivity::class.java)
            intent.putExtra("type", currentUser.domaine)
            holder.itemView.context.startActivity(intent)
        }

        Glide
            .with(holder.itemView.context)
            .load(currentUser.image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            //.apply(RequestOptions.overrideOf(300,600))
            .centerInside()
            .placeholder(circularProgressDrawable)
            .into(holder.imageView)



    }

    override fun getItemCount(): Int {
        return userList.size
    }
}

