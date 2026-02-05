import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidKotlinMultiplatformLibrary) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.googleServices) apply false
    alias(libs.plugins.firebaseCrashlytics) apply false
    alias(libs.plugins.stability.analyzer) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.crashlyticslink) apply false
}

subprojects {
    plugins.withId("org.jlleitschuh.gradle.ktlint") {
        configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
            version.set("1.3.1")
            android.set(true)
            outputToConsole.set(true)
            ignoreFailures.set(false)

            filter {
                exclude { element ->
                    element.file.path.contains("/build/generated/") ||
                            element.file.path.contains("/build/buildkonfig/") ||
                            element.file.path.contains("/ksp/") ||
                            element.file.path.contains("/generated/") ||
                            element.file.path.contains("/commonMain/kotlin/")
                }
            }
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    tasks.withType<KotlinCompilationTask<*>>().configureEach {
        compilerOptions {
            freeCompilerArgs.add("-Xexpect-actual-classes")
        }
    }
}


tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}