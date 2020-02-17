package it.pabich.yourturn.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import it.pabich.yourturn.R
import it.pabich.yourturn.fragments.mainPages.GroupFragment
import it.pabich.yourturn.fragments.mainPages.HomeFragment
import it.pabich.yourturn.fragments.mainPages.ManageFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var content: FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(activity_main_toolbar)
        activity_main_navigator.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        activity_main_navigator.selectedItemId = R.id.main_nav_home
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        //if (activity_main_navigator.selectedItemId != item.itemId) {
            when (item.itemId) {
                R.id.main_nav_home -> {
                    val fragment = HomeFragment()
                    addFragment(fragment)
                    activity_main_toolbar.setTitle(R.string.menu_home)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.main_nav_groups -> {
                    val fragment = GroupFragment()
                    addFragment(fragment)
                    activity_main_toolbar.setTitle(R.string.menu_groups)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.main_nav_profile -> {
                    val fragment = GroupFragment()
                    addFragment(fragment)
                    activity_main_toolbar.setTitle(R.string.menu_profile)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.main_nav_manage -> {
                    val fragment = ManageFragment()
                    addFragment(fragment)
                    activity_main_toolbar.setTitle(R.string.menu_manage)
                    return@OnNavigationItemSelectedListener true
                }
            }
        //}

        false
    }

    @SuppressLint("PrivateResource")
    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.activity_main_content, fragment, fragment.javaClass.simpleName)
            .commit()
    }
}
