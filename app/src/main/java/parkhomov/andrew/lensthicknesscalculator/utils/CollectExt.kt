package parkhomov.andrew.lensthicknesscalculator.utils

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

fun <T> Flow<T>.shortCollect(owner: LifecycleCoroutineScope) {
    owner.launchWhenStarted { this@shortCollect.collect() }
}