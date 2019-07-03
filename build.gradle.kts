// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://maven.fabric.io/public") }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.4.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.40")
        classpath("io.fabric.tools:gradle:1.29.0")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
    val major_version = "3"
    val minor_version = "1"
    val patch_version = "0"

    extra["version_name"] = "$major_version.$minor_version.$patch_version"
    extra["applicationId"] = "parkhomov.andrew.lensthicknesscalculator"
    extra["version_code"] = 10
    extra["compile_version"] = 29
    extra["min_version"] = 17

    extra["kotlin_version"] = "1.3.40"
    extra["junit_version"] = "4.12"
    extra["cicerone_version"] = "5.0.0"
    extra["annotaion_version"] = "1"
    extra["constraint_version"] = "2.0.0-alpha3"
    extra["appcompat_version"] = "1.0.2"
    extra["support_version"] = "1.0.0"
    extra["koin_version"] = "2.0.1"
    extra["bottom_bar"] = "2.1.0"
    extra["fabric_version"] = "2.10.1"
    extra["coroutines_version"] = "1.0.1"
    extra["expandable_view"] = "1.0.3"
    extra["gson_version"] = "2.8.5"
    extra["architecture"] = "1.1.1"
    extra["pref_version"] = "1.1.0-beta01"
    extra["recycler_view"] = "1.0.0"
}

val clean by tasks.creating(Delete::class) {
    delete = setOf(rootProject.buildDir)
}
