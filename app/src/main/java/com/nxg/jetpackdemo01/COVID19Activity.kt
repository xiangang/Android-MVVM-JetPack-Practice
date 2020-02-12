package com.nxg.jetpackdemo01

import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.gyf.immersionbar.ImmersionBar
import com.nxg.jetpackdemo01.manager.QDSkinManager
import com.nxg.jetpackdemo01.manager.QDSkinManager.SKIN_BLUE
import com.nxg.jetpackdemo01.manager.QDSkinManager.SKIN_DARK
import com.nxg.jetpackdemo01.manager.QDSkinManager.SKIN_WHITE
import kotlinx.android.synthetic.main.activity_covid19.*

class COVID19Activity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_covid19)
        setSupportActionBar(toolbar)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            ), drawer_layout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.navigation_home ->{
                    QDSkinManager.changeSkin(SKIN_BLUE)
                    changeBarColor(R.color.colorPrimaryTencent)
                    //toolbar.setBackgroundColor(getColor(R.color.colorPrimaryTencent))
                }
                R.id.navigation_dashboard ->{
                    QDSkinManager.changeSkin(SKIN_DARK)
                    changeBarColor(R.color.colorPrimaryTencentHealth)
                    //toolbar.setBackgroundColor(getColor(R.color.colorPrimaryTencentHealth))
                }
                R.id.navigation_notifications ->{
                    QDSkinManager.changeSkin(SKIN_WHITE)
                    changeBarColor(R.color.colorPrimarySMZDM)
                    //toolbar.setBackgroundColor(getColor(R.color.colorPrimarySMZDM))
                }
                else -> {}
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun changeBarColor(@ColorRes barColor: Int){
        ImmersionBar.with(this)
            .barColor(barColor)
            .fitsSystemWindows(true)
            .init()
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }*/
}
