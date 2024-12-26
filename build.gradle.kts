plugins {
    this.kotlin("jvm") version "2.1.20-Beta1"
    this.id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "de.jqnn"
version = "1.0.0"

repositories {
    this.mavenCentral()
    this.maven("https://repo.papermc.io/repository/maven-public/")
    this.maven("https://oss.sonatype.org/content/groups/public/")
}

dependencies {
    this.compileOnly("io.papermc.paper:paper-api:1.21.3-R0.1-SNAPSHOT")
    this.implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    this.implementation("net.axay:kspigot:1.21.0")
}

kotlin {
    this.jvmToolchain(21)
}

tasks.build {
    this.dependsOn("shadowJar")
}