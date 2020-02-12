package com.nxg.jetpackdemo01.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nxg.jetpackdemo01.R
import com.nxg.jetpackdemo01.utils.NCOV_2019_URL_QQ_FY_TOOLS
import com.nxg.jetpackdemo01.viewmodels.COVID19ViewModel
import com.orhanobut.logger.Logger
import com.tencent.smtt.sdk.WebSettings
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {

    private val mViewModelCOVID19: COVID19ViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        mViewModelCOVID19.nCov2019Url.observe(this, Observer {
            Logger.d(it)
            COVIDWebViewDashboard.settings.javaScriptEnabled = true
            COVIDWebViewDashboard.settings.domStorageEnabled = true
            COVIDWebViewDashboard.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            //COVIDWebViewDashboard.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            /*val webViewClient =  object : WebViewClient() {
                override fun shouldOverrideUrlLoading(p0: WebView?, p1: String?): Boolean {
                    p0?.loadUrl(it)
                    return true
                }
            }
            COVIDWebViewDashboard.webViewClient = webViewClient*/
            COVIDWebViewDashboard.loadUrl(it)
        })
        mViewModelCOVID19.setUrl(NCOV_2019_URL_QQ_FY_TOOLS)
        return root
    }
}