package cd.babitech.medrad.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import cd.babitech.medrad.Activity.DetailDoctorActivity
import cd.babitech.medrad.Model.doctormd
import cd.babitech.medrad.databinding.DoctorConsulBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class DoctorAdapter(var userList: ArrayList<doctormd?>) : RecyclerView.Adapter<DoctorAdapter.UserViewHolder>() {
    lateinit var binding: DoctorConsulBinding

    // ViewHolder pour afficher chaque élément de la liste
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nom = binding.nameDoctor
        var langue = binding.langue
        var profil = binding.profilDoctor
        var experiance = binding.experiance
        var degree = binding.degree

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        binding = DoctorConsulBinding.inflate(
            LayoutInflater.from(parent.context),parent, false)
        //val itemView = LayoutInflater.from(parent.context).inflate(R.layout.specialitse_item, parent, false)
        return UserViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val currentUser = userList[position]

        val circularProgressDrawable = CircularProgressDrawable(holder.itemView.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        //holder.nameTextView.text = currentUser!!.domaine
        holder.nom.text = currentUser!!.nom
        holder.degree.text = currentUser!!.degree
        holder.experiance.text = "${currentUser.experiance} ans d'exp"
        holder.langue.text = currentUser!!.langue

        // Utilisez votre bibliothèque de chargement d'images préférée pour charger l'image à partir du lien ici

        Glide
            .with(holder.itemView.context)
            .load(currentUser!!.image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            //.apply(RequestOptions.overrideOf(300,600))
            .centerInside()
            .placeholder(circularProgressDrawable)
            .into(holder.profil)

        //si on clique sur l'item
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailDoctorActivity::class.java)
            intent.putExtra("doctor_id",currentUser.doctor_id)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            holder.itemView.context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }
}

