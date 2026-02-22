import com.codingfeline.buildkonfig.compiler.FieldSpec.Type
import com.google.devtools.ksp.gradle.KspAATask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.stability.analyzer)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.ksp)
    alias(libs.plugins.buildkonfig)
}

val bundleIdPrefix: String? = libs.versions.bundleIdPrefix.get()

kotlin {
    androidLibrary {
        namespace = libs.versions.namespace.get()
        compileSdk =
            libs.versions.compileSdk
                .get()
                .toInt()
        minSdk =
            libs.versions.minSdk
                .get()
                .toInt()

        // Enable Android resource processing for Compose Multiplatform resources
        androidResources {
            enable = true
        }

        // Enable host-side (unit) tests
        withHostTestBuilder {}.configure {}
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            binaryOption("bundleId", "$bundleIdPrefix.composeapp.framework")
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.play.review.ktx)
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.analytics)
        }

        iosMain.dependencies {
            implementation(libs.crashkios.crashlytics)
        }

        commonMain.dependencies {
            api(projects.coreStorage)
            api(projects.coreDatabase)
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.ui.tooling.preview)
            api(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.jetbrains.navigation3.ui)
            implementation(libs.jetbrains.lifecycle.viewmodel.nav3)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.material.icons.extended)
            implementation(libs.collections)
            implementation(libs.lyricist)
        }
    }

    jvmToolchain(17)
}

kotlin.sourceSets.getByName("androidHostTest") {
    dependencies {
        implementation(libs.bundles.testing)
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.lyricist.processor)
}

ksp {
    arg("lyricist.internalVisibility", "true")
    arg("lyricist.generateStringsProperty", "true")
}

tasks.withType<KotlinCompilationTask<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

tasks.withType<KspAATask>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

kotlin.sourceSets.commonMain {
    kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
}

compose.resources {
    publicResClass = true
    packageOfResClass = "ltc.composeapp.generated.resources"
}

buildkonfig {
    packageName = libs.versions.namespace.get()

    defaultConfigs {
        buildConfigField(Type.STRING, "VERSION_NAME", libs.versions.versionName.get())
    }
}

// Make ktlint tasks depend on KSP code generation
tasks.matching { it.name.contains("ktlint", ignoreCase = true) }.configureEach {
    dependsOn("kspCommonMainKotlinMetadata")
}
