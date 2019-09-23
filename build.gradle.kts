plugins {
    kotlin("jvm") version "1.3.21"
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform { }
}

val arrow_version = "0.9.0"
dependencies {
    compile(kotlin("stdlib"))
    compile("io.arrow-kt:arrow-core-data:$arrow_version")
    compile("io.arrow-kt:arrow-effects-io-extensions:$arrow_version")
    compile("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.2")
    runtime("org.slf4j:slf4j-simple:1.7.28")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
}

repositories {
    jcenter()
    maven("https://dl.bintray.com/kotlin/kotlinx")
}
