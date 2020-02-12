package com.nxg.jetpackdemo01.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nxg.jetpackdemo01.R
import com.nxg.jetpackdemo01.utils.NCOV_2019_URL_SMZDM_YQ_BH
import com.nxg.jetpackdemo01.viewmodels.COVID19ViewModel
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_notifications.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationsFragment : Fragment() {

    private val mViewModelCOVID19: COVID19ViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        mViewModelCOVID19.nCov2019Url.observe(this, Observer {
            Logger.d(it)
            COVIDWebViewNotify.settings.javaScriptEnabled = true
            COVIDWebViewNotify.settings.domStorageEnabled = true
            COVIDWebViewNotify.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            COVIDWebViewNotify.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            COVIDWebViewNotify.loadUrl(it)
        })
        mViewModelCOVID19.setUrl(NCOV_2019_URL_SMZDM_YQ_BH)

        return root
    }
}