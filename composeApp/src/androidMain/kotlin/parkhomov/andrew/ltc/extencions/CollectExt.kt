package parkhomov.andrew.ltc.extencions

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

fun <T> Flow<T>.collectData(owner: LifecycleCoroutineScope) {
    owner.launchWhenStarted { this@collectData.collect() }
}