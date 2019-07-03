plugins {
    id(Dependencies.BuildPlugins.androidApplication)
    id(Dependencies.BuildPlugins.kotlinAndroid)
    id(Dependencies.BuildPlugins.kotlinAndroidExtensions)
}

android {
    compileSdkVersion(Dependencies.AndroidSdk.compileVersion)
    defaultConfig {
        applicationId = Dependencies.AndroidSdk.applicationId
        minSdkVersion(Dependencies.AndroidSdk.minVersion)
        targetSdkVersion(Dependencies.AndroidSdk.targetVersion)
        versionName = Dependencies.AndroidSdk.versionName
        versionCode = Dependencies.AndroidSdk.versionCode
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                    getDefaultProguardFile("proguard-android.txt"),
                    file("proguard-rules.pro")
            )

        }
    }
    androidExtensions {
        isExperimental = true
    }
}

dependencies {

    implementation(Dependencies.Libraries.kotlinStdLib)
    implementation(Dependencies.Libraries.preference)
    implementation(Dependencies.Libraries.koinAndroid)
    implementation(Dependencies.Libraries.cicerone)
    implementation(Dependencies.Libraries.fabric)

    implementation(project(Dependencies.Module.activity))
    implementation(project(Dependencies.Module.base))
    implementation(project(Dependencies.Module.language))
    implementation(project(Dependencies.Module.result))
    implementation(project(Dependencies.Module.glossary))
    implementation(project(Dependencies.Module.diameter))
    implementation(project(Dependencies.Module.transposition))
    implementation(project(Dependencies.Module.thickness))
}
