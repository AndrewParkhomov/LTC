repositories {
    google()
    jcenter()
    maven { url = uri("https://maven.fabric.io/public") }
}
plugins {
    `kotlin-dsl`
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}