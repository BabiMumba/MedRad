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
import cd.babitech.medrad.Unit.DATA
import cd.babitech.medrad.databinding.FragmentRendezVBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RendezVFragment : Fragment() {
    private val contactInfo = arrayListOf<rende_vous>()
    private lateinit var fstore : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var rvContact : RecyclerView
    private lateinit var rendeAdapter : RendezAdapter

    lateinit var binding: FragmentRendezVBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRendezVBinding.inflate(layoutInflater)

        getdata()



        return binding.root
    }

    fun getdata(){
        fstore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        fstore.collection(DATA.rendeVous)
            .whereEqualTo("id_user",DATA.id_user)
            .orderBy("date_env")
            .get()
            .addOnSuccessListener {
            if (!it.isEmpty){
                val liste_rendev = it.documents
                for(i in liste_rendev) {
                    val model = i.toObject(rende_vous::class.java)!!
                    contactInfo.add(model)
                    rendeAdapter = RendezAdapter(contactInfo)
                    binding.mayRendevz.adapter = rendeAdapter
                    binding.mayRendevz.layoutManager = LinearLayoutManager(requireActivity())
                }
            }
        }
    }

}