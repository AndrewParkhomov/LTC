package parkhomov.andrew.ltc.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TestRefractiveIndexRepository : RefractiveIndexRepository {
    private val indices =
        MutableStateFlow(
            listOf(
                RefractiveIndexEntity(id = 1, value = 1.498, label = "1.498 CR-39", isUserCreated = false),
                RefractiveIndexEntity(id = 2, value = 1.535, label = "1.560", isUserCreated = false),
                RefractiveIndexEntity(id = 3, value = 1.53, label = "1.530 Trivex", isUserCreated = false),
                RefractiveIndexEntity(id = 4, value = 1.586, label = "1.590 Poly", isUserCreated = false),
                RefractiveIndexEntity(id = 5, value = 1.59, label = "1.610", isUserCreated = false),
                RefractiveIndexEntity(id = 6, value = 1.66, label = "1.670", isUserCreated = false),
                RefractiveIndexEntity(id = 7, value = 1.727, label = "1.740", isUserCreated = false),
            ),
        )

    override fun getAllIndices(): Flow<List<RefractiveIndexEntity>> = indices.asStateFlow()

    override suspend fun getAllIndicesOnce(): List<RefractiveIndexEntity> = indices.value

    override suspend fun getById(id: Long): RefractiveIndexEntity? = indices.value.find { it.id == id }

    override suspend fun getByValue(value: Double): RefractiveIndexEntity? = indices.value.find { it.value == value }

    override suspend fun insert(index: RefractiveIndexEntity): Long {
        val newList = indices.value.toMutableList()
        newList.add(index)
        indices.value = newList
        return index.id
    }

    override suspend fun delete(id: Long) {
        indices.value = indices.value.filter { it.id != id }
    }
}
