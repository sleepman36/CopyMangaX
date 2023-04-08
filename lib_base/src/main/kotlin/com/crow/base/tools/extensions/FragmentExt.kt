package com.crow.base.tools.extensions

import android.R.anim
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/*************************
 * @Machine: RedmiBook Pro 15 Win11
 * @Path: lib_base/src/main/java/com/barry/base/extensions
 * @Time: 2022/11/29 10:30
 * @Author: CrowForKotlin
 * @Description: FragmentExt
 * @formatter:on
 **************************/
fun interface LifecycleCallBack {
    suspend fun onLifeCycle(scope: CoroutineScope)
}

fun Fragment.repeatOnLifecycle(
    state: Lifecycle.State = Lifecycle.State.STARTED,
    lifecycleCallBack: LifecycleCallBack,
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(state) {
            lifecycleCallBack.onLifeCycle(this)
        }
    }
}

inline fun Fragment.doAfterDelay(
    delayMs: Long,
    crossinline block: suspend CoroutineScope.(Fragment) -> Unit,
) {
    lifecycleScope.launch {
        delay(delayMs)
        block(this@doAfterDelay)
    }
}

suspend inline fun <T> T.doAfterDelay(delayMs: Long, crossinline block: suspend T.() -> Unit) {
    delay(delayMs)
    block()
}

fun Fragment.navigate(
    @IdRes idRes: Int, bundle: Bundle? = null,
    navOptions: NavOptions = NavOptions.Builder()
        .setEnterAnim(anim.fade_in)
        .setExitAnim(anim.fade_out)
        .setPopEnterAnim(anim.fade_in)
        .setPopExitAnim(anim.fade_out)
        .build(),
) {
    findNavController().navigate(idRes, bundle, navOptions)
}

fun Fragment.navigateUp() = findNavController().navigateUp()

fun FragmentTransaction.withFadeAnimation() =
    setCustomAnimations(anim.fade_in, anim.fade_out, anim.fade_in, anim.fade_out)

fun FragmentTransaction.withSlideAnimation() = setCustomAnimations(
    anim.slide_in_left,
    anim.slide_out_right,
    anim.slide_in_left,
    anim.slide_out_right
)

inline fun FragmentManager.hide(
    fragment: Fragment,
    backStackName: String?,
    crossinline transaction: (FragmentTransaction) -> FragmentTransaction = { it.withFadeAnimation() }
) = transaction(beginTransaction()).addToBackStack(backStackName).hide(fragment).commit()

inline fun FragmentManager.remove(
    fragment: Fragment,
    crossinline  transaction: (FragmentTransaction) -> FragmentTransaction = { it.withFadeAnimation() }
) = transaction(beginTransaction()).remove(fragment).commit()

inline fun FragmentManager.show(
    fragment: Fragment,
    crossinline  transaction: (FragmentTransaction) -> FragmentTransaction = { it.withFadeAnimation() }
) = transaction(beginTransaction()).show(fragment).commit()


inline fun FragmentManager.navigateByAddWithBackStack(
    @IdRes id: Int,
    fragment: Fragment,
    backStackName: String? = null,
    crossinline transaction: (FragmentTransaction) -> FragmentTransaction = { it.withFadeAnimation() },
) {
    transaction(beginTransaction())
        .addToBackStack(backStackName)
        .add(id, fragment)
        .commit()
}

fun FragmentManager.popSyncWithClear(vararg backStackName: String?, flags: Int = FragmentManager.POP_BACK_STACK_INCLUSIVE) {
    backStackName.forEach {
        popBackStackImmediate(it, flags)
        clearBackStack(it ?: return)
    }
}

fun FragmentManager.popAsyncWithClear(vararg backStackName: String?, flags: Int = FragmentManager.POP_BACK_STACK_INCLUSIVE) {
    backStackName.forEach {
        popBackStack(it, flags)
        clearBackStack(it ?: return)
    }
}