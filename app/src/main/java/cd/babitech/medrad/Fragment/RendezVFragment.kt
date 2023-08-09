package cd.babitech.medrad.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cd.babitech.medrad.R
import cd.babitech.medrad.databinding.FragmentRendezVBinding
import cd.babitech.medrad.databinding.ItemRendezVousBinding

class RendezVFragment : Fragment() {

    lateinit var binding: FragmentRendezVBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRendezVBinding.inflate(layoutInflater)






        return binding.root
    }

}