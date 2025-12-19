import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "core-storage"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.datastore.preferences)
            implementation(libs.koin.core)
        }
    }
}

android {
    namespace = "parkhomov.andrew.ltc.storage"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildTypes {
        debug {}
        release {}
        create("stageDebug") {
            initWith(buildTypes.getByName("debug"))
        }
        create("stage") {
            initWith(buildTypes.getByName("debug"))
        }
    }
}
