package cd.babitech.medrad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
import cd.babitech.medrad.Unit.DATA
import cd.babitech.medrad.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.grpc.Context

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getdata()
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
                    binding.myToolbar.toolbar.visibility = View.VISIBLE
                    fragment = HomeFragment()
                    binding.myToolbar.idTitre.setText("Accueil")
                    loadFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.apoint -> {
                    binding.myToolbar.toolbar.visibility = View.VISIBLE
                    binding.myToolbar.idTitre.setText("Rendez-Vous")
                    fragment = RendezVFragment()
                    loadFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.profileMenu -> {
                    binding.myToolbar.toolbar.visibility = View.GONE
                    fragment = ProfilFragment()
                    loadFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.more -> {
                    binding.myToolbar.toolbar.visibility = View.VISIBLE
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
    fun getdata(){
        val sharedPreferences = getSharedPreferences(DATA.PREF_NAME, MODE_PRIVATE)
         val profil = sharedPreferences.getString(DATA.profil,null)
        if (profil!=null){
            Glide
                .with(this)
                .load(profil)
                .centerInside()
                .into(binding.myToolbar.myProfil)


        }

    }

}