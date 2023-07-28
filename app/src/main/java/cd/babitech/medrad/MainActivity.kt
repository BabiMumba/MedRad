package cd.babitech.medrad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import cd.babitech.medrad.Activity.AddApointActivity
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

        findViewById<FloatingActionButton>(R.id.floating_button).setOnClickListener {
            startActivity(Intent(this, AddApointActivity::class.java))



        }

        loadFragment(HomeFragment())


    }


    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val fragment: Fragment
            when (item.itemId) {
                R.id.homeMenu -> {
                    fragment = HomeFragment()
                    loadFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.apoint -> {
                    fragment = RendezVFragment()
                    loadFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.profileMenu -> {
                    fragment = ProfilFragment()
                    loadFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.more -> {
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

}