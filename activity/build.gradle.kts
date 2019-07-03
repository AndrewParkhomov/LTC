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
    implementation(Dependencies.Libraries.material)
    implementation(Dependencies.Libraries.koinAndroid)
    implementation(Dependencies.Libraries.koinAndroidViewmodel)
    implementation(Dependencies.Libraries.cicerone)
    implementation(Dependencies.Libraries.bottomBar)

    implementation(project(Dependencies.Module.base))
    implementation(project(Dependencies.Module.language))
    implementation(project(Dependencies.Module.glossary))
    implementation(project(Dependencies.Module.diameter))
    implementation(project(Dependencies.Module.transposition))
    implementation(project(Dependencies.Module.thickness))
}
