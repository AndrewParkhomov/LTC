package parkhomov.andrew.ltc.database

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

interface RefractiveIndexRepository {
    fun getAllIndices(): Flow<List<RefractiveIndexEntity>>
    suspend fun getAllIndicesOnce(): List<RefractiveIndexEntity>
    suspend fun getById(id: Long): RefractiveIndexEntity?
    suspend fun getByValue(value: Double): RefractiveIndexEntity?
    suspend fun insert(index: RefractiveIndexEntity): Long
    suspend fun delete(id: Long)
}

class DefaultRefractiveIndexRepository(
    private val dao: RefractiveIndexDao
) : RefractiveIndexRepository {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        scope.launch {
            if (dao.getCount() == 0) {
                dao.insertAll(defaultIndices)
            }
        }
    }

    override fun getAllIndices(): Flow<List<RefractiveIndexEntity>> {
        return dao.getAllIndices()
    }

    override suspend fun getAllIndicesOnce(): List<RefractiveIndexEntity> {
        return dao.getAllIndicesOnce()
    }

    override suspend fun getById(id: Long): RefractiveIndexEntity? {
        return dao.getById(id)
    }

    override suspend fun getByValue(value: Double): RefractiveIndexEntity? {
        return dao.getByValue(value)
    }

    override suspend fun insert(index: RefractiveIndexEntity): Long {
        return dao.insert(index)
    }

    override suspend fun delete(id: Long) {
        dao.deleteById(id)
    }

    companion object {
        private val defaultIndices = listOf(
            RefractiveIndexEntity(
                id = 1,
                value = 1.498,
                label = "1.498 CR-39",
                isUserCreated = false
            ),
            RefractiveIndexEntity(
                id = 2,
                value = 1.535,
                label = "1.560",
                isUserCreated = false
            ),
            RefractiveIndexEntity(
                id = 3,
                value = 1.53,
                label = "1.530 Trivex",
                isUserCreated = false
            ),
            RefractiveIndexEntity(
                id = 4,
                value = 1.586,
                label = "1.590 Poly",
                isUserCreated = false
            ),
            RefractiveIndexEntity(
                id = 5,
                value = 1.59,
                label = "1.610",
                isUserCreated = false
            ),
            RefractiveIndexEntity(
                id = 6,
                value = 1.66,
                label = "1.670",
                isUserCreated = false
            ),
            RefractiveIndexEntity(
                id = 7,
                value = 1.727,
                label = "1.740",
                isUserCreated = false
            )
        )
    }
}
