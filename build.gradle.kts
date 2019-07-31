plugins {
    application
    kotlin("jvm") version "1.3.21"
}

application {
    mainClassName = "samples.HelloWorldKt"
}

dependencies {
    compile(kotlin("stdlib"))
    implementation("org.assertj:assertj-core:3.11.1")
}

repositories {
    jcenter()
}
