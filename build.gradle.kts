import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
    kotlin("jvm") version "1.3.21"
    kotlin("kapt") version "1.3.21"
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform { }
}

val kotlinVersion = "1.3.21"
val arrowVersion = "0.10.2"
dependencies {
    implementation(kotlin("stdlib", kotlinVersion))
    implementation(kotlin("stdlib-common", kotlinVersion))
    implementation(kotlin("stdlib-jdk7", kotlinVersion))
    implementation(kotlin("stdlib-jdk8", kotlinVersion))
    implementation(kotlin("reflect", kotlinVersion))
    implementation("io.arrow-kt:arrow-core-data:$arrowVersion")
    implementation("io.arrow-kt:arrow-fx:$arrowVersion")
    implementation("io.arrow-kt:arrow-mtl:$arrowVersion")
    implementation("io.arrow-kt:arrow-syntax:$arrowVersion")
    implementation(
        "org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3"
    )
    implementation("io.github.microutils:kotlin-logging:1.7.8")
    implementation("org.awaitility:awaitility:4.0.2")
    runtimeOnly("org.slf4j:slf4j-simple:1.7.28")

    // need this at compile level for chapter 8
    implementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
    kapt("io.arrow-kt:arrow-meta:$arrowVersion")
}

repositories {
    mavenCentral()
    // Replacement for Bintray Kotlin repositories
    maven("https://repo.kotlin.link")
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.suppressWarnings = true
}

ktlint {
    verbose.set(true)
    disabledRules.set(
        setOf(
            "comment-spacing",
            "filename",
            "import-ordering",
            "no-line-break-before-assignment"
        )
    )
}

kapt {
    useBuildCache = false
}
