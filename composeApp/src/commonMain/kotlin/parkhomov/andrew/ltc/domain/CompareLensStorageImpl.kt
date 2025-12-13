package parkhomov.andrew.ltc.domain

class CompareLensStorageImpl2 : CompareLensStorage2 {

//    private val _compareList = MutableStateFlow<Set<CalculatedData>>(mutableSetOf())
//    override val compareList: StateFlow<Set<CalculatedData>> = _compareList.asStateFlow()
//
//    override fun addItem(itemToAdd: CalculatedData) {
//        _compareList.update { it + itemToAdd }
//    }
//
//    override fun removeItem(itemToRemove: CalculatedData) {
//        _compareList.update {
//            it.mapNotNull { cachedItem: CalculatedData ->
//                if (cachedItem == itemToRemove) {
//                    null
//                } else {
//                    cachedItem
//                }
//            }.toSet()
//        }
//    }
//
//    override fun clearCompareList() {
//        _compareList.update { setOf() }
//    }
}