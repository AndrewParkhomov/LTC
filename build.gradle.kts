// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://maven.fabric.io/public") }
    }
    dependencies {
        classpath(Dependencies.BuildPlugins.gradlePluginAndroid)
        classpath(Dependencies.BuildPlugins.gradlePluginKotlin)
        classpath(Dependencies.BuildPlugins.gradleFabric)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

val clean by tasks.creating(Delete::class) {
    delete = setOf(rootProject.buildDir)
}
