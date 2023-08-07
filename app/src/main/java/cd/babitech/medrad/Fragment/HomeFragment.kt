package cd.babitech.medrad.Fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import cd.babitech.medrad.Activity.DetailDoctorActivity
import cd.babitech.medrad.Activity.DoctorListActivity
import cd.babitech.medrad.Adapter.Speciality
import cd.babitech.medrad.Model.specialite
import cd.babitech.medrad.Unit.DATA
import cd.babitech.medrad.Unit.Void
import cd.babitech.medrad.databinding.FragmentHomeBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class HomeFragment : Fragment() {
    var list: ArrayList<specialite?>? = null
    var adapter: Speciality? = null
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        list = ArrayList()
        adapter = Speciality(list!!)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val recyclerView = binding.specialId
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        //recuperer les donnee
        getSpeciality()

        val imageList = ArrayList<SlideModel>() // Create image list
        imageList.add(SlideModel("https://firebasestorage.googleapis.com/v0/b/restaurant-2506c.appspot.com/o/lushi_3.png?alt=media&token=bb41e27f-9a31-47b5-b871-99517f97dfa5", ScaleTypes.FIT))
        imageList.add(
            SlideModel("https://firebasestorage.googleapis.com/v0/b/restaurant-2506c.appspot.com/o/lstore.png?alt=media&token=25a865b3-304b-47b6-a2cc-1d986d5c5013",
                ScaleTypes.FIT)
        )
        getData()
        binding.imageSlider.setImageList(imageList)
        binding.falProfil.profilBtn.visibility = View.GONE
        binding.falProfil.contactBtn.setOnClickListener {
            Dexter.withContext(
                requireActivity()
            )
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
                        val nume = "tel:+243975937553"
                        val i = Intent(Intent.ACTION_CALL)
                        i.data= Uri.parse(nume)
                        startActivity(i)
                    }

                    override fun onPermissionDenied(permissionDeniedResponse: PermissionDeniedResponse) {
                        Toast.makeText(
                            requireActivity(),
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
        binding.profil1.callBtn.setOnClickListener {
            startActivity(Intent(requireActivity(),DetailDoctorActivity::class.java))
        }
        binding.profil2.callBtn.setOnClickListener {
            startActivity(Intent(requireActivity(),DetailDoctorActivity::class.java))
        }
        binding.profil3.callBtn.setOnClickListener {
            startActivity(Intent(requireActivity(),DetailDoctorActivity::class.java))
        }
        binding.profil2.nameDoctor.setText("Dr kanda Mbikayi")
        binding.profil3.nameDoctor.setText("Dr kasongo Mardocher")

        val circularProgressDrawable = CircularProgressDrawable(requireActivity())
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        binding.moreDoctor.setOnClickListener {
            startActivity(Intent(requireActivity(),DoctorListActivity::class.java))
        }


        return binding.root
    }
    fun getData(){
        val sharedPreferences = requireActivity().getSharedPreferences(DATA.PREF_NAME, Context.MODE_PRIVATE)
        val nom  = sharedPreferences.getString(DATA.nom,"").toString()
        binding.nomprofil.setText(nom)
    }
    fun my_glide(lien_im:String, place: CircularProgressDrawable, imageView: ImageView){

        Glide
            .with(requireActivity())
            .load(lien_im)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            //.apply(RequestOptions.overrideOf(300,600))
            .centerInside()
            .placeholder(place)
            .into(imageView)
    }
    fun getSpeciality(){
        val database = FirebaseDatabase.getInstance()
        database.getReference(DATA.special)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    list!!.clear()
                    for (data in snapshot.children){
                        val model = data.getValue(specialite::class.java)
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