plugins {
    application
    kotlin("jvm") version "1.3.21"
}

application {
    mainClassName = "samples.HelloWorldKt"
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform { }
}

val arrow_version = "0.9.0"
dependencies {
    compile(kotlin("stdlib"))
    compile("io.arrow-kt:arrow-core-data:$arrow_version")
    compile("io.arrow-kt:arrow-effects-io-extensions:$arrow_version")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
}

repositories {
    jcenter()
}
