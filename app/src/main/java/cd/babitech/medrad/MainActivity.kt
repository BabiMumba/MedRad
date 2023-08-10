package cd.babitech.medrad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import cd.babitech.medrad.Activity.AddApointActivity
import cd.babitech.medrad.Activity.AddSpecialityActivity
import cd.babitech.medrad.Activity.DoctorListActivity
import cd.babitech.medrad.Activity.NotificationActivity
import cd.babitech.medrad.Auth.doctor.RegisterDoctActivity
import cd.babitech.medrad.Fragment.HomeFragment
import cd.babitech.medrad.Fragment.MenuFragment
import cd.babitech.medrad.Fragment.ProfilFragment
import cd.babitech.medrad.Fragment.RendezVFragment
import cd.babitech.medrad.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        bottomNavigationView.selectedItemId = R.id.homeMenu
        binding.myToolbar.notifIc.setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
        }
        findViewById<FloatingActionButton>(R.id.floating_button).setOnClickListener {
            startActivity(Intent(this, DoctorListActivity::class.java))

        }

        loadFragment(HomeFragment())


    }


    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val fragment: Fragment
            when (item.itemId) {
                R.id.homeMenu -> {
                    fragment = HomeFragment()
                    binding.myToolbar.idTitre.setText("Accueil")
                    loadFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.apoint -> {
                    binding.myToolbar.idTitre.setText("Rendez-Vous")
                    fragment = RendezVFragment()
                    loadFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.profileMenu -> {
                    fragment = ProfilFragment()
                    loadFragment(fragment)
                    binding.myToolbar.idTitre.setText("Profile")
                    return@OnNavigationItemSelectedListener true
                }
                R.id.more -> {
                    binding.myToolbar.idTitre.setText("Plus")
                    fragment = MenuFragment()
                    loadFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        finish()
        //super.onBackPressed()
    }

}