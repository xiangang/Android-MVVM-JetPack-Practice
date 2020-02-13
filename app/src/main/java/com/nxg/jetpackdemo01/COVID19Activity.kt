package com.nxg.jetpackdemo01

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.gyf.immersionbar.ImmersionBar
import com.nxg.jetpackdemo01.manager.QDSkinManager
import com.nxg.jetpackdemo01.ui.dashboard.DashboardViewModel
import com.nxg.jetpackdemo01.viewmodels.COVID19ViewModel
import com.nxg.jetpackdemo01.viewmodels.ThemeViewModel
import kotlinx.android.synthetic.main.activity_covid19.*

class COVID19Activity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navHeaderView: View
    private lateinit var navHeaderLayout: LinearLayout

    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_covid19)
        val navView: NavigationView = findViewById(R.id.nav_view)
        //navHeaderView = navView.inflateHeaderView(R.layout.nav_header_main)
        navHeaderView = navView.getHeaderView(0)
        navHeaderLayout = navHeaderView.findViewById(R.id.nav_header_layout)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            ),drawer_layout
        )
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.navigation_home ->{
                    QDSkinManager.changeSkin(QDSkinManager.SKIN_BLUE)
                    themeViewModel.themeColor.postValue(R.color.colorPrimaryTencent)
                }
                R.id.navigation_dashboard ->{
                    QDSkinManager.changeSkin(QDSkinManager.SKIN_DARK)
                    themeViewModel.themeColor.postValue(R.color.colorPrimaryTencentHealth)
                }
                R.id.navigation_notifications ->{
                    QDSkinManager.changeSkin(QDSkinManager.SKIN_WHITE)
                    themeViewModel.themeColor.postValue(R.color.colorPrimarySMZDM)
                }
                else -> {}
            }
        }

        themeViewModel.themeColor.observe(this, Observer {
            changeThemeColor(it)
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun changeThemeColor(@ColorRes themColor: Int){
        ImmersionBar.with(this)
            .barColor(themColor)
            .init()
        toolbar.setBackgroundColor(getColor(themColor))
        navHeaderLayout.setBackgroundColor(getColor(themColor))
    }


}
