package parkhomov.andrew.ltc.toast

import parkhomov.andrew.ltc.strings.Strings

sealed class ToastMessage(val resolve: (Strings) -> String) {

    data object AddCylinderForTransposition : ToastMessage({ it.thicknessAddCylinderForTransposition })
}
