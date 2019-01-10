package parkhomov.andrew.base.domain

import androidx.lifecycle.LiveData

interface UseCase<T> {

    fun getLiveData(): LiveData<T>

    fun cleanUp()
}
