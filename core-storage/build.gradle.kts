plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.ktlint)
}

val bundleIdPrefix: String? = libs.versions.bundleIdPrefix.get()

kotlin {
    androidLibrary {
        namespace = "${libs.versions.namespace.get()}.storage"
        compileSdk =
            libs.versions.compileSdk
                .get()
                .toInt()
        minSdk =
            libs.versions.minSdk
                .get()
                .toInt()
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "core-storage"
            isStatic = true
            binaryOption("bundleId", "$bundleIdPrefix.storage.framework")
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.datastore.preferences)
            implementation(libs.koin.core)
            implementation(libs.compose.runtime)
        }
    }

    jvmToolchain(17)
}
