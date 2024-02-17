plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
    id("org.pkl-lang") version ("0.25.1")
    idea
    application
}

repositories {
    mavenCentral()
}
dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("com.github.ajalt.clikt:clikt:4.2.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    implementation("org.pkl-lang:pkl-config-kotlin:0.25.2")
    implementation("co.touchlab:kermit:2.0.1")
}
//pkl {
//    kotlinCodeGenerators {
//        register("genKotlin") {
//            sourceModules.addAll(files("DxConfig.pkl"))
//            generateKdoc.set(true)
//            outputDir = layout.projectDirectory.dir("src/main/")
//        }
//    }
//}