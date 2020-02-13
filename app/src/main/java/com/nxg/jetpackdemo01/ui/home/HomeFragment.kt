package com.nxg.jetpackdemo01.ui.home

import android.os.Bundle
import android.view.*
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.Navigation
import com.gyf.immersionbar.ImmersionBar
import com.nxg.jetpackdemo01.COVID19Activity
import com.nxg.jetpackdemo01.R
import com.nxg.jetpackdemo01.utils.NCOV_2019_URL_BILIBILI
import com.nxg.jetpackdemo01.utils.NCOV_2019_URL_DXY
import com.nxg.jetpackdemo01.utils.NCOV_2019_URL_TENCENT
import com.nxg.jetpackdemo01.viewmodels.COVID19ViewModel
import com.nxg.jetpackdemo01.viewmodels.ThemeViewModel
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val mViewModelCOVID19: COVID19ViewModel by viewModel()

    private val themeViewModel: ThemeViewModel by viewModels({requireActivity()})

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val webViewClient = (object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view!!.loadUrl(url)
                return true
            }
        })


        mViewModelCOVID19.nCov2019Url.observe(this, Observer {
            Logger.d(it)
            COVID19WebViewHome.webViewClient = webViewClient
            COVID19WebViewHome.settings.javaScriptEnabled = true
            COVID19WebViewHome.settings.domStorageEnabled = true
            COVID19WebViewHome.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            COVID19WebViewHome.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            COVID19WebViewHome.loadUrl(it)
        })

        mViewModelCOVID19.getTimelineService()
            .observe(this, Observer {
                Logger.d(it.status.toString() + " " + it.message.toString() + "\n" + it.data.toString())
            })
        setHasOptionsMenu(true)
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when(item.itemId) {
            R.id.action_mask_monitor -> {
                Navigation.findNavController(COVID19WebViewHome).navigate(R.id.navigation_notifications)
                themeViewModel.themeColor.postValue(R.color.colorPrimarySMZDM)
                true
            }
            R.id.action_url_ncov_dxy ->{
                mViewModelCOVID19.setUrl(NCOV_2019_URL_DXY)
                themeViewModel.themeColor.postValue(R.color.colorPrimary)
                true
            }
            R.id.action_url_ncov_bilibili -> {
                mViewModelCOVID19.setUrl(NCOV_2019_URL_BILIBILI)
                themeViewModel.themeColor.postValue(R.color.colorPrimaryBilibili)
                true
            }
            R.id.action_url_ncov_tencent -> {
                mViewModelCOVID19.setUrl(NCOV_2019_URL_TENCENT)
                themeViewModel.themeColor.postValue(R.color.colorPrimaryTencent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}