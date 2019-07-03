plugins {
    id(Dependencies.BuildPlugins.androidLibrary)
    id(Dependencies.BuildPlugins.kotlinAndroid)
    id(Dependencies.BuildPlugins.kotlinAndroidExtensions)
}

android {
    compileSdkVersion(Dependencies.AndroidSdk.compileVersion)
    defaultConfig {
        minSdkVersion(Dependencies.AndroidSdk.minVersion)
        targetSdkVersion(Dependencies.AndroidSdk.targetVersion)
        versionName = Dependencies.AndroidSdk.versionName
        versionCode = Dependencies.AndroidSdk.versionCode
        vectorDrawables.useSupportLibrary = true
    }
    androidExtensions {
        isExperimental = true
    }
}

dependencies {

    implementation(Dependencies.Libraries.kotlinStdLib)
    implementation(Dependencies.Libraries.appCompat)
    implementation(Dependencies.Libraries.koinAndroid)
    implementation(Dependencies.Libraries.koinAndroidViewmodel)
    implementation(Dependencies.Libraries.cicerone)
    implementation(Dependencies.Libraries.gson)
    implementation(Dependencies.Libraries.recyclerview)
    implementation(Dependencies.Libraries.coroutines)
    implementation(Dependencies.Libraries.inject)
    implementation(Dependencies.Libraries.constraintLayout)
}
