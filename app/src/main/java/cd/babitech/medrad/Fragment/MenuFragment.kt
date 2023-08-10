package cd.babitech.medrad.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cd.babitech.medrad.Activity.AddSpecialityActivity
import cd.babitech.medrad.Auth.doctor.RegisterDoctActivity
import cd.babitech.medrad.R
import cd.babitech.medrad.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    lateinit var binding: FragmentMenuBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment

        binding.addDoctorNew.setOnClickListener {
            startActivity(Intent(requireActivity(),RegisterDoctActivity::class.java))
        }
        binding.addDomaine.setOnClickListener {
            startActivity(Intent(requireActivity(),AddSpecialityActivity::class.java))
        }

        return binding.root
    }
}