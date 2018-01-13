plugins {
    id("org.jetbrains.kotlin.plugin.spring") version "1.2.10"
}

dependencies {
    val springBootVersion: String = parent.properties["springBootVersion"] as String
    compile("org.springframework.boot:spring-boot-starter:$springBootVersion")
}