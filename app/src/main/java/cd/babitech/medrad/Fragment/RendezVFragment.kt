package cd.babitech.medrad.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cd.babitech.medrad.Adapter.RendezAdapter
import cd.babitech.medrad.Model.rende_vous
import cd.babitech.medrad.R
import cd.babitech.medrad.Unit.DATA
import cd.babitech.medrad.Unit.Void
import cd.babitech.medrad.databinding.FragmentRendezVBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RendezVFragment : Fragment() {
    private lateinit var fstore : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var rendeAdapter : RendezAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    lateinit var binding: FragmentRendezVBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRendezVBinding.inflate(layoutInflater)


        binding.mayRendevz.apply {
            linearLayoutManager = LinearLayoutManager(requireActivity())
            linearLayoutManager.reverseLayout = true
            linearLayoutManager.onSaveInstanceState()
            linearLayoutManager.stackFromEnd = true
            layoutManager = linearLayoutManager
        }

        getdata()
        binding.suipe.setOnRefreshListener {
            getdata()
            binding.suipe.isRefreshing = false
        }

        return binding.root
    }

    fun getdata(){
        val liste_rendev_v = arrayListOf<rende_vous>()
        binding.progressItem.loaderFrameLayout.visibility = View.VISIBLE
        fstore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        fstore.collection(DATA.rendeVous)
            .get()
            .addOnSuccessListener {
            if (!it.isEmpty){
                liste_rendev_v.clear()
                val liste_rendev = it.documents
                for(i in liste_rendev) {
                    val model = i.toObject(rende_vous::class.java)!!
                    if (model.id_user==DATA.id_user){
                        liste_rendev_v.add(model)
                    }
                    rendeAdapter = RendezAdapter(liste_rendev_v)
                    rendeAdapter.notifyDataSetChanged()

                }
                binding.mayRendevz.adapter = rendeAdapter

               // Void.toas(requireActivity(),"${liste_rendev_v.size}")
                if (liste_rendev_v.size<=0){
                    Glide.with(this).asGif().load(R.raw.vide).into(binding.emptyListe)
                    binding.progressItem.loaderFrameLayout.visibility = View.GONE
                    binding.stateListe.visibility = View.VISIBLE
                }else{
                    binding.progressItem.loaderFrameLayout.visibility = View.GONE
                }

               // binding.mayRendevz.layoutManager = LinearLayoutManager(requireActivity())
               // rendeAdapter.notifyDataSetChanged()
            }
        }
    }

}