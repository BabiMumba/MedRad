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
import com.google.firebase.firestore.FirebaseFirestore
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
        imageList.add(
            SlideModel(
                "https://i.postimg.cc/15H3Jgsx/ban1.png",
                ScaleTypes.FIT
            )
        )
        imageList.add(
            SlideModel(
                "https://i.postimg.cc/7LpHf0Mz/ban3.png",
                ScaleTypes.FIT
            )
        )
        getData()
        binding.imageSlider.setImageList(imageList)
        binding.falProfil.profilBtn.visibility = View.GONE
        binding.falProfil.contactBtn.setOnClickListener {
            call_app()
        }
        binding.profil1.callBtn.setOnClickListener {
            call_app()
        }
        binding.profil2.callBtn.setOnClickListener {
            call_app()
        }
        binding.profil3.callBtn.setOnClickListener {
            call_app()
        }
        binding.profil4.callBtn.setOnClickListener {
            call_app()
        }
        binding.profil5.callBtn.setOnClickListener {
            call_app()
        }
        binding.profil6.callBtn.setOnClickListener {
            call_app()
        }
        binding.myRelative.setOnClickListener {
            startActivity(Intent(requireActivity(), DoctorListActivity::class.java))
        }

        binding.profil2.nameDoctor.setText("Dr kanda Mbikayi")
        binding.profil3.nameDoctor.setText("Dr kasongo Mardocher")
        binding.profil3.fonction.setText("Pédiatre")
        binding.profil2.fonction.setText("Gynécologue")
        binding.profil4.fonction.setText("Urologue")
        binding.profil4.nameDoctor.setText("Dr Ruth Mutomb")
        binding.profil5.fonction.setText("Pneumologue")
        binding.profil5.nameDoctor.setText("Dr Malothy Mulimbi")
        binding.profil6.fonction.setText("Orthopédiste")
        binding.profil6.nameDoctor.setText("Dr Christelle Mwanza")

        val img1 = "https://img.freepik.com/free-photo/front-view-smiley-man-with-thumb-up_23-2149633839.jpg?size=626&ext=jpg&uid=R68904716&ga=GA1.2.2069144117.1673200892&semt=ais"
        val img2 = "https://img.freepik.com/free-photo/cheerful-ethnic-doctor-with-arms-crossed_23-2147767333.jpg?size=626&ext=jpg&uid=R68904716&ga=GA1.2.2069144117.1673200892&semt=ais"
        val img3 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQY_7p_DzGReXjLWefuP6fwSpEaO3Bvlhntbg&usqp=CAU"
        val img4 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWrc-0EvJkAEZgYeGZs0_cjVjO6TOb3BQzyQ&usqp=CAU"
        val img5 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRTVkSYqfdBOQf4aSK2JgJ5NUEsnQpdqRYMXQ&usqp=CAU"
        val img6 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRZS5KKp3cMPzebgw4NQWbr2UzAV2jezj34Yg&usqp=CAU"
        yglide(img1,binding.profil1.imageDoctor)
        yglide(img2,binding.profil2.imageDoctor)
        yglide(img3,binding.profil3.imageDoctor)
        yglide(img4,binding.profil4.imageDoctor)
        yglide(img5,binding.profil5.imageDoctor)
        yglide(img6,binding.profil6.imageDoctor)


        val circularProgressDrawable = CircularProgressDrawable(requireActivity())
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        binding.moreDoctor.setOnClickListener {
            startActivity(Intent(requireActivity(), DoctorListActivity::class.java))
        }


        return binding.root
    }

    fun getData() {
        val sharedPreferences =
            requireActivity().getSharedPreferences(DATA.PREF_NAME, Context.MODE_PRIVATE)
        val nom = sharedPreferences.getString(DATA.nom, "").toString()
        binding.nomprofil.setText(nom)
    }

    fun my_glide(lien_im: String, place: CircularProgressDrawable, imageView: ImageView) {

        Glide
            .with(requireActivity())
            .load(lien_im)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            //.apply(RequestOptions.overrideOf(300,600))
            .centerInside()
            .placeholder(place)
            .into(imageView)
    }

    fun getSpeciality() {
        val database = FirebaseFirestore.getInstance()
        database.collection(DATA.special)
            .get()
            .addOnSuccessListener {
                list!!.clear()
                for (document in it.documents){
                    val domaine = document.toObject(specialite::class.java)
                    list!!.add(domaine)
                    adapter!!.notifyDataSetChanged()
                }
                if (list!!.isNotEmpty()){
                    binding.circularLoader.visibility = View.GONE
                }
            }

    }

    fun call_app() {
        Dexter.withContext(
            requireActivity()
        )
            .withPermission(Manifest.permission.CALL_PHONE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
                    val nume = "tel:+243975937553"
                    val i = Intent(Intent.ACTION_CALL)
                    i.data = Uri.parse(nume)
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

    fun yglide(load:String,view: ImageView){
        Glide
            .with(requireActivity())
            .load(load)
            .into(view)
    }



}