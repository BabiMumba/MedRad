package cd.babitech.medrad.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cd.babitech.medrad.Adapter.DoctorAdapter
import cd.babitech.medrad.Model.doctormd
import cd.babitech.medrad.R
import cd.babitech.medrad.Unit.DATA
import cd.babitech.medrad.databinding.ActivityFiltreBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FiltreActivity : AppCompatActivity() {
    var adapter: DoctorAdapter? = null
    lateinit var binding: ActivityFiltreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFiltreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = intent.getStringExtra("type")
        adapter = DoctorAdapter(this)
        binding.doctorListeItem.adapter = adapter
        getdoctor(type!!)

    binding.toolbar.ivBack.setOnClickListener {
            onBackPressed()
        }


    }

    fun getdoctor(type: String){
        val list = ArrayList<doctormd>()
        val database = FirebaseDatabase.getInstance()
        binding.progressItem.loaderFrameLayout.visibility = View.VISIBLE
        database.getReference(DATA.doctor)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list!!.clear()
                    for (data in snapshot.children){
                        val model = data.getValue(doctormd::class.java)
                        if (model!!.specialite == type){
                            list!!.add(model!!)
                        }
                    }
                    adapter!!.items= list
                    adapter!!.notifyDataSetChanged()
                    // binding.progressBar.visibility = View.GONE
                    binding.progressItem.loaderFrameLayout.visibility = View.GONE


                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            })

    }
}