package cd.babitech.medrad.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import cd.babitech.medrad.Model.rende_vous
import cd.babitech.medrad.R
import cd.babitech.medrad.Unit.DATA
import cd.babitech.medrad.Unit.Void
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.firestore.FirebaseFirestore

class RendezAdapter(var userList: ArrayList<rende_vous>) : RecyclerView.Adapter<RendezAdapter.UserViewHolder>() {

    // ViewHolder pour afficher chaque élément de la liste
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date_rendev: TextView = itemView.findViewById(R.id.date_rendev)
        val imageView: ImageView = itemView.findViewById(R.id.profil_doctor)
        val domaine: TextView = itemView.findViewById(R.id.domaine_doctore)
        val nom: TextView = itemView.findViewById(R.id.name_doctor)
        val delete_btn: ImageView = itemView.findViewById(R.id.delete_btn)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_rendez_vous, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]

        val circularProgressDrawable = CircularProgressDrawable(holder.itemView.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        holder.nom.text = currentUser!!.nom_docteur
        holder.date_rendev.text = currentUser!!.date_rendev
        holder.domaine.text = currentUser!!.domaine

        Glide
            .with(holder.itemView.context)
            .load(currentUser.profil_docteur)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            //.apply(RequestOptions.overrideOf(300,600))
            .centerInside()
            .placeholder(circularProgressDrawable)
            .into(holder.imageView)

        holder.delete_btn.setOnClickListener {
            val dialogue = AlertDialog.Builder(holder.itemView.context)
            dialogue.setTitle("Suppression Rendez-Vous")
            dialogue.setMessage("Etes-vous sur de vouloir supprimer le Rendez-vous")
            dialogue.setPositiveButton("Supprimer"){ _, _ ->
                val firebase = FirebaseFirestore.getInstance()
                firebase.collection(DATA.rendeVous)
                    .document(DATA.id_user+currentUser.id_rens)
                    .delete()
                    .addOnSuccessListener {
                        Void.toas(holder.itemView.context,"Rendez-vous Supprimer\nRafraîchissez la liste en poussant vers le bas pour voir le changement.")
                    }
                    .addOnFailureListener {
                        Void.toas(holder.itemView.context,"Erreur:${it.message}")
                    }
            }
            dialogue.setNegativeButton("Annuler",null)
            dialogue.show()

        }


    }

    override fun getItemCount(): Int {
        return userList.size
    }
}

