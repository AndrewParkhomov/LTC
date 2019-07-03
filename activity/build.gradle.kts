plugins {
    id("com.android.library")
    kotlin("android")
    id("org.jetbrains.kotlin.android.extensions")
}

android {
    compileSdkVersion(extra["compile_version"] as Int)
    defaultConfig {
        minSdkVersion(extra["min_version"] as Int)
        targetSdkVersion(extra["compile_version"] as Int)
        versionName = extra["version_name"] as String
        versionCode = extra["version_code"] as Int
        vectorDrawables.useSupportLibrary = true
    }
    androidExtensions {
        isExperimental = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:${extra["appcompat_version"] as String}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${extra["kotlin_version"] as String}")
    implementation("com.google.android.material:material:${extra["support_version"] as String}")
    implementation("org.koin:koin-android:${extra["koin_version"] as String}")
    implementation("org.koin:koin-androidx-viewmodel:${extra["koin_version"] as String}")
    implementation("ru.terrakok.cicerone:cicerone:${extra["cicerone_version"] as String}")
    implementation("com.ashokvarma.android:bottom-navigation-bar:${extra["bottom_bar"] as String}")

    implementation(project(":base"))
    implementation(project(":language"))
    implementation(project(":glossary"))
    implementation(project(":diameter"))
    implementation(project(":transposition"))
    implementation(project(":thickness"))
}
