package parkhomov.andrew.ltc.data

private const val TAB_THICKNESS: Int = 0
private const val TAB_DIAMETER: Int = 1

sealed class Tab(val index: Int){
    data object Thickness: Tab(TAB_THICKNESS)
    data object Diameter: Tab(TAB_DIAMETER)
}
