package cd.babitech.medrad.Adapter

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import cd.babitech.medrad.Activity.DetailDoctorActivity
import cd.babitech.medrad.Model.doctormd
import cd.babitech.medrad.databinding.DoctorConsulBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class DoctorAdapter(context: Context) : RecyclerView.Adapter<DoctorAdapter.UserViewHolder>(),Filterable {
    lateinit var binding: DoctorConsulBinding
//var userList: ArrayList<doctormd?>
    var items:ArrayList<doctormd> = ArrayList()
        set(value) {
            field = value
            produictfilter = value
            notifyDataSetChanged()
        }

    // ViewHolder pour afficher chaque élément de la liste
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nom = binding.nameDoctor
        var langue = binding.langue
        var profil = binding.profilDoctor
        var experiance = binding.experiance
        var degree = binding.degree
        var specilter = binding.specialite
        val call_btn = binding.contactBtn



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        binding = DoctorConsulBinding.inflate(
            LayoutInflater.from(parent.context),parent, false)
        //val itemView = LayoutInflater.from(parent.context).inflate(R.layout.specialitse_item, parent, false)
        return UserViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val currentUser = produictfilter[position]

        val circularProgressDrawable = CircularProgressDrawable(holder.itemView.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        //holder.nameTextView.text = currentUser!!.domaine
        holder.nom.text = currentUser!!.nom
        holder.degree.text = "Degree: ${currentUser.degree}"
        holder.experiance.text = "${currentUser.experiance} ans d'exp"
        holder.langue.text = "Langue: ${currentUser.langue}"
        holder.specilter.text = "Specialité: ${currentUser.specialite}"

        // Utilisez votre bibliothèque de chargement d'images préférée pour charger l'image à partir du lien ici

        Glide
            .with(holder.itemView.context)
            .load(currentUser!!.image)
            .placeholder(circularProgressDrawable)
            .into(holder.profil)

        //si on clique sur l'item
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailDoctorActivity::class.java)
            intent.putExtra("doctor_id",currentUser.doctor_id)
            intent.putExtra("name",currentUser.nom)
            intent.putExtra("profil",currentUser.image)
            intent.putExtra("numero",currentUser.numero)
            intent.putExtra("categorie",currentUser.specialite)
            intent.putExtra("degree",currentUser.degree)
            intent.putExtra("experiance",currentUser.experiance)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            holder.itemView.context.startActivity(intent)

        }
        binding.contactBtn.setOnClickListener {
            Dexter.withContext(
                holder.itemView.context
            )
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
                        val nume = "tel:+243${currentUser.numero}"
                        val i = Intent(Intent.ACTION_CALL)
                        i.data= Uri.parse(nume)
                        holder.itemView.context.startActivity(i)
                    }

                    override fun onPermissionDenied(permissionDeniedResponse: PermissionDeniedResponse) {
                        Toast.makeText(
                            holder.itemView.context,
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
        binding.profilBtn.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailDoctorActivity::class.java)
            intent.putExtra("doctor_id",currentUser.doctor_id)
            intent.putExtra("name",currentUser.nom)
            intent.putExtra("profil",currentUser.image)
            intent.putExtra("numero",currentUser.numero)
            intent.putExtra("categorie",currentUser.specialite)
            intent.putExtra("degree",currentUser.degree)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            holder.itemView.context.startActivity(intent)
        }


    }

    override fun getItemCount()=produictfilter.size

    override fun getFilter(): Filter {
        return object:Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()){
                    produictfilter = items
                }else{
                    val resultlist = items.filter {
                        it.nom.lowercase().contains( charSearch.lowercase())
                    }
                    produictfilter = resultlist as ArrayList<doctormd>
                }
                val filterResults = FilterResults()
                filterResults.values = produictfilter
                return filterResults
            }
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                produictfilter = p1?.values as ArrayList<doctormd>
                notifyDataSetChanged()
            }

        }


    }
    private  var produictfilter:ArrayList<doctormd> = ArrayList()


}

