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

dependencies {
    compile(kotlin("stdlib"))
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")

}

repositories {
    jcenter()
}
