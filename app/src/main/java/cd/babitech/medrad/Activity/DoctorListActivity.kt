package cd.babitech.medrad.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
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
    var adapter: DoctorAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = DoctorAdapter(this)
        binding.doctorListeItem.adapter = adapter
        getdoctor()

        binding.homeTopAppBar.homeSearchEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                adapter!!.filter.filter(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


    }

    fun getdoctor(){
        val list = ArrayList<doctormd>()
        val database = FirebaseDatabase.getInstance()
        binding.progressItem.loaderFrameLayout.visibility = View.VISIBLE
        database.getReference(DATA.doctor)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list!!.clear()
                    for (data in snapshot.children){
                        val model = data.getValue(doctormd::class.java)
                        list!!.add(model!!)
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