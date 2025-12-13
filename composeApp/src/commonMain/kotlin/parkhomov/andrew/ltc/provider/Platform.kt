package parkhomov.andrew.ltc.provider

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

fun isIos() = getPlatform().name.contains("iOS")
