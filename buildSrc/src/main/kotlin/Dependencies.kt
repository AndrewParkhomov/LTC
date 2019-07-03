const val versionKotlin = "1.3.40"
object Dependencies {

    object BuildPlugins {
        object Version {
            const val versionBuildTools = "3.4.1"
            const val versionFabric     = "1.29.0"
        }
        const val gradlePluginAndroid     = "com.android.tools.build:gradle:${Version.versionBuildTools}"
        const val gradlePluginKotlin      = "org.jetbrains.kotlin:kotlin-gradle-plugin:$versionKotlin"
        const val gradleFabric            = "io.fabric.tools:gradle:${Version.versionFabric}"
        const val androidApplication      = "com.android.application"
        const val kotlinAndroid           = "kotlin-android"
        const val kotlinAndroidKapt       = "kotlin-kapt"
        const val kotlinAndroidExtensions = "kotlin-android-extensions"
        const val androidLibrary          = "com.android.library"
    }

    object AndroidSdk {
        private object Versions {
            const val major = "3"
            const val minor = "1"
            const val patch = "0"
        }
        const val applicationId      = "parkhomov.andrew.lensthicknesscalculator"
        const val versionName        = "${Versions.major}.${Versions.minor}.${Versions.patch}"
        const val versionCode        = 10
        const val compileVersion     = 29
        const val minVersion         = 17
        const val targetVersion = compileVersion
    }

    object Libraries {
        private object Version {
            const val versionCicerone       = "5.0.0"
            const val versionAnnotation     = "1"
            const val versionConstraint     = "2.0.0-alpha3"
            const val versionAppcompat      = "1.0.2"
            const val versionSupport        = "1.0.0"
            const val versionKoin           = "2.0.1"
            const val versonBottomBar       = "2.1.0"
            const val versionFabric         = "2.10.1"
            const val versionCoroutines     = "1.0.1"
            const val versionGson           = "2.8.5"
            const val versionPreferences    = "1.1.0-beta01"
            const val versionRecyclerView   = "1.0.0"
        }

        const val kotlinStdLib         = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$versionKotlin"
        const val appCompat            = "androidx.appcompat:appcompat:${Version.versionAppcompat}"
        const val constraintLayout     = "androidx.constraintlayout:constraintlayout:${Version.versionConstraint}"
        const val koinAndroid          = "org.koin:koin-android:${Version.versionKoin}"
        const val koinAndroidViewmodel = "org.koin:koin-androidx-viewmodel:${Version.versionKoin}"
        const val cicerone             = "ru.terrakok.cicerone:cicerone:${Version.versionCicerone}"
        const val gson                 = "com.google.code.gson:gson:${Version.versionGson}"
        const val recyclerview         = "androidx.recyclerview:recyclerview:${Version.versionRecyclerView}"
        const val coroutines           = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.versionCoroutines}"
        const val inject               = "javax.inject:javax.inject:${Version.versionAnnotation}"
        const val bottomBar            = "com.ashokvarma.android:bottom-navigation-bar:${Version.versonBottomBar}"
        const val material             = "com.google.android.material:material:${Version.versionSupport}"
        const val fabric               = "com.crashlytics.sdk.android:crashlytics:${Version.versionFabric}"
        const val preference           = "androidx.preference:preference:${Version.versionPreferences}"
    }

    object TestLibraries {
        private object Versions {
            const val junit4        = "4.12"
            const val testRunner    = "1.1.0-alpha4"
            const val espresso      = "3.1.0-alpha4"
        }
        const val junit4     = "junit:junit:${Versions.junit4}"
        const val testRunner = "androidx.test:runner:${Versions.testRunner}"
        const val espresso   = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    }

    object Module {
        const val base          = ":base"
        const val language      = ":language"
        const val result        = ":result"
        const val glossary      = ":glossary"
        const val diameter      = ":diameter"
        const val transposition = ":transposition"
        const val thickness     = ":thickness"
        const val activity      = ":activity"
    }

}