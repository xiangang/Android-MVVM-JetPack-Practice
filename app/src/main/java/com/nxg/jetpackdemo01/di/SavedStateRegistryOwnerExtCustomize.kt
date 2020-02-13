package com.nxg.jetpackdemo01.di

import android.content.ComponentCallbacks
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.koin.getViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import kotlin.reflect.KClass

/**
 * ================================================
 * Created by xiangang on 2020/2/13 12:03
 * <a href="mailto:xiangang12202@gmail.com">Contact me</a>
 * <a href="https://github.com/xiangang">Follow me</a>
 * ================================================
 */
inline fun <reified T : ViewModel> SavedStateRegistryOwner.viewModelExt(
    owner: SavedStateRegistryOwner,
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): Lazy<T> {
    return lazy { getViewModelExt(owner,T::class, qualifier, parameters) }
}

fun <T : ViewModel> SavedStateRegistryOwner.getViewModelExt(
    owner: SavedStateRegistryOwner,
    clazz: KClass<T>,
    qualifier: Qualifier? = null,
    parameters: ParametersDefinition? = null
): T {
    return getKoin().getViewModel(owner, clazz, qualifier, parameters)
}

private fun LifecycleOwner.getKoin() = (this as ComponentCallbacks).getKoin()
