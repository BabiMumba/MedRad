package cd.babitech.medrad.Fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.window.SplashScreen
import cd.babitech.medrad.Activity.AboutDeveloppeur
import cd.babitech.medrad.Activity.AddSpecialityActivity
import cd.babitech.medrad.Activity.SplashActivity
import cd.babitech.medrad.Activity.ThankActivity
import cd.babitech.medrad.Auth.doctor.RegisterDoctActivity
import cd.babitech.medrad.R
import cd.babitech.medrad.Unit.DATA
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
        binding.logOut.setOnClickListener {
            //logout user
            //affiche une boite de dialogue pour confirmer la deconnexion
            val dialogue = AlertDialog.Builder(requireActivity())
            dialogue.setTitle("Deconnexion")
            dialogue.setMessage("Voulez-vous vraiment vous deconnecter ?")
            dialogue.setPositiveButton("Oui") { dialog, which ->
                //deconnexion
                val editor = requireActivity().getSharedPreferences(DATA.PREF_NAME, Context.MODE_PRIVATE).edit()
                editor.clear()
                editor.apply()
                DATA.AUTH.signOut()
                startActivity(Intent(requireActivity(),SplashActivity::class.java)
                )



            }
            dialogue.setNegativeButton("Non") { dialog, which ->
                //ne rien faire
                dialog.dismiss()
            }
            dialogue.show()
        }
        binding.addDomaine.setOnClickListener {
            startActivity(Intent(requireActivity(),AddSpecialityActivity::class.java))
        }
        binding.aboutMe.setOnClickListener {
            startActivity(Intent(requireActivity(),AboutDeveloppeur::class.java))
        }
        binding.aboutApp.setOnClickListener {
            startActivity(Intent(requireActivity(),ThankActivity::class.java).also {
                val message = resources.getString(R.string.aboute_app)
                val titre = "A propos de l'application"
                it.putExtra("message",message)
                it.putExtra("title",titre)
            })
        }
        binding.thankBtn.setOnClickListener {
            startActivity(Intent(requireActivity(),ThankActivity::class.java).also {
                //recuperer les texte depuis le ressource string
                val message = resources.getString(R.string.thank)
                val titre = "Remerciement"
                it.putExtra("message",message)
                it.putExtra("title",titre)
            })
        }

        return binding.root
    }
}