package parkhomov.andrew.lensthicknesscalculator.mvvm

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

abstract class CoroutinesViewModel : ViewModel() {

    private val asyncJobs: MutableList<Job> = mutableListOf()

    fun launchAsync(block: suspend CoroutineScope.() -> Unit): Job {
        val job: Job = GlobalScope.launch(Dispatchers.Main) { block() }
        asyncJobs.add(job)
        job.invokeOnCompletion { asyncJobs.remove(job) }
        return job
    }

    suspend fun <T> async(block: suspend CoroutineScope.() -> T): T =
            withContext(Dispatchers.Default) { block() }

    private fun cancelAllAsync() {
        val asyncJobsSize = asyncJobs.size

        if (asyncJobsSize > 0) {
            for (i in asyncJobsSize - 1 downTo 0) {
                asyncJobs[i].cancel()
            }
        }
    }

    suspend fun <T> asyncAwait(block: suspend CoroutineScope.() -> T): T = async(block)

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        cancelAllAsync()
    }
}