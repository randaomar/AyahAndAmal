package jetpack.randa.com.ayahandamal.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import android.view.WindowManager
import jetpack.randa.com.ayahandamal.R
import jetpack.randa.com.ayahandamal.fragment.HomeFragment
import jetpack.randa.com.ayahandamal.fragment.SettingsFragment
import jetpack.randa.com.ayahandamal.fragment.SurahListFragment
import jetpack.randa.com.ayahandamal.fragment.TopicsListFragment
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*

class HomeActivity : AppCompatActivity() {

    private var isOnCreate: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        super.onCreate(savedInstanceState)
        channgeLang()
        setContentView(R.layout.activity_home)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setNavigationListener()
        navigation_view.selectedItemId = R.id.home
        navigation_view.menu.findItem(R.id.home).isChecked = true
        isOnCreate = true
        Log.d("HomeActivity", "ON CREATE")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("HomeActivity", "ON RESTORE")
    }

    private fun channgeLang() {

            val configuration = resources.configuration
            configuration.setLayoutDirection(Locale("ar"))
            resources.updateConfiguration(configuration, resources.displayMetrics)

    }

    override fun onResume() {
        super.onResume()
        if(isOnCreate && navigation_view.selectedItemId != R.id.home){
            isOnCreate = false
            navigation_view.selectedItemId = navigation_view.selectedItemId
        }
    }

    private fun setNavigationListener() {
        val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    supportActionBar?.title  = getString(R.string.app_name)
                    launchFragment(HomeFragment(), false, true)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.quran -> {
                    supportActionBar?.title  = getString(R.string.read_quran)
                    launchFragment(SurahListFragment(), false, true)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.filter -> {
                    supportActionBar?.title  = getString(R.string.topics)
                    launchFragment(TopicsListFragment(), false, true)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.settings -> {
                    supportActionBar?.title = getString(R.string.settings)
                    launchFragment(SettingsFragment(), false, true)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
        navigation_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun launchFragment(fragment: Fragment, addToBackStack: Boolean, animate: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        if(animate)
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
        if(addToBackStack) {
            transaction.replace(R.id.container, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()
        } else {
            transaction.replace(R.id.container, fragment).commitAllowingStateLoss()
        }
    }

}
