package parkhomov.andrew.ltc.data

import parkhomov.andrew.ltc.database.RefractiveIndexEntity

private const val THICKNESS_FACTOR: Double = 0.53

data class RefractiveIndexUiModel(
    val id: Long,
    val value: Double,
    val label: String,
    val isUserCreated: Boolean
) {
    val indexX: Double get() = THICKNESS_FACTOR / (value - 1)

    companion object {
        fun fromEntity(entity: RefractiveIndexEntity): RefractiveIndexUiModel {
            return RefractiveIndexUiModel(
                id = entity.id,
                value = entity.value,
                label = entity.label,
                isUserCreated = entity.isUserCreated
            )
        }

        fun getDefaultIndex(): RefractiveIndexUiModel {
            return RefractiveIndexUiModel(
                id = 1,
                value = 1.498,
                label = "1.498 CR-39",
                isUserCreated = false
            )
        }
    }
}
