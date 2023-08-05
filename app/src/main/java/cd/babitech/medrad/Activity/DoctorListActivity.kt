package cd.babitech.medrad.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cd.babitech.medrad.Adapter.DoctorAdapter
import cd.babitech.medrad.Adapter.Speciality
import cd.babitech.medrad.Model.doctormd
import cd.babitech.medrad.Model.specialite
import cd.babitech.medrad.R
import cd.babitech.medrad.Unit.DATA
import cd.babitech.medrad.databinding.ActivityDoctorListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DoctorListActivity : AppCompatActivity() {
    lateinit var binding: ActivityDoctorListBinding
    var list: ArrayList<doctormd?>? = null
    var adapter: DoctorAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = ArrayList()
        adapter = DoctorAdapter(list!!)
        binding.doctorListeItem.adapter = adapter
        getdoctor()


    }

    fun getdoctor(){
        val database = FirebaseDatabase.getInstance()
        database.getReference(DATA.doctor)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list!!.clear()
                    for (data in snapshot.children){
                        val model = data.getValue(doctormd::class.java)
                        list!!.add(model)
                    }
                    adapter!!.notifyDataSetChanged()
                    // binding.progressBar.visibility = View.GONE

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            })

    }
}