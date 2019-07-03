plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("org.jetbrains.kotlin.android.extensions")
}

android {
    compileSdkVersion(extra["compile_version"] as Int)
    defaultConfig {
        applicationId = extra["applicationId"] as String
        minSdkVersion(extra["min_version"] as Int)
        targetSdkVersion(extra["compile_version"] as Int)
        versionName = extra["version_name"] as String
        versionCode = extra["version_code"] as Int
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

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${extra["kotlin_version"] as String}")
    implementation("androidx.preference:preference:${extra["pref_version"] as String}")
    implementation("org.koin:koin-android:${extra["koin_version"] as String}")
    implementation("ru.terrakok.cicerone:cicerone:${extra["cicerone_version"] as String}")
    implementation("com.crashlytics.sdk.android:crashlytics:${extra["fabric_version"] as String}")

    implementation(project(":base"))
    implementation(project(":language"))
    implementation(project(":result"))
    implementation(project(":glossary"))
    implementation(project(":diameter"))
    implementation(project(":transposition"))
    implementation(project(":thickness"))
    implementation(project(":activity"))
}
