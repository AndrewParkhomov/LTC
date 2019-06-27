package parkhomov.andrew.base.usecase

import androidx.lifecycle.LiveData

interface UseCase<T> {

    fun getLiveData(): LiveData<T>

    fun cleanUp()

}
