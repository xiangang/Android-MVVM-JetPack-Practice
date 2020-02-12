package com.nxg.jetpackdemo01

import android.os.Bundle
import android.view.Menu
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.nxg.jetpackdemo01.viewmodels.COVID19ViewModel
import com.nxg.jetpackdemo01.viewmodels.HomeViewModel
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mViewModel: HomeViewModel by viewModel()
    private val mViewModelCOVID19: COVID19ViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        mViewModelCOVID19.nCov2019Url.observe(this, Observer {
            Logger.d(it)

            webView.settings.javaScriptEnabled = true
            webView.settings.domStorageEnabled = true
            webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            webView.loadUrl(it)
        })

        mViewModel.requestHomeData()
            .observe(this, Observer {
            Logger.d(it.status.toString() + " " + it.message.toString() + "\n" + it.data.toString())
        })

        mViewModelCOVID19.getTimelineService()
            .observe(this, Observer {
                Logger.d(it.status.toString() + " " + it.message.toString() + "\n" + it.data.toString())
            })

        //mViewModel.requestHomeData2()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when(item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }*/
}
