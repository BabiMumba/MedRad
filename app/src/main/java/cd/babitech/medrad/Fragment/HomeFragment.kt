package cd.babitech.medrad.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cd.babitech.medrad.Activity.DetailDoctorActivity
import cd.babitech.medrad.R
import cd.babitech.medrad.Unit.DATA
import cd.babitech.medrad.Unit.Void
import cd.babitech.medrad.databinding.FragmentHomeBinding
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)


        val imageList = ArrayList<SlideModel>() // Create image list
        imageList.add(SlideModel("https://firebasestorage.googleapis.com/v0/b/restaurant-2506c.appspot.com/o/lushi_3.png?alt=media&token=bb41e27f-9a31-47b5-b871-99517f97dfa5", ScaleTypes.FIT))
        imageList.add(
            SlideModel("https://firebasestorage.googleapis.com/v0/b/restaurant-2506c.appspot.com/o/lstore.png?alt=media&token=25a865b3-304b-47b6-a2cc-1d986d5c5013",
                ScaleTypes.FIT)
        )
        getData()
        binding.imageSlider.setImageList(imageList)
        binding.profil1.callBtn.setOnClickListener {
            Void.Intent_page(requireActivity(),DetailDoctorActivity::class.java)
        }

        return binding.root
    }
    fun getData(){
        val sharedPreferences = requireActivity().getSharedPreferences(DATA.PREF_NAME, Context.MODE_PRIVATE)
        val nom  = sharedPreferences.getString(DATA.nom,"").toString()
        binding.nomprofil.setText(nom)
    }

}