plugins {
    kotlin("jvm") version "1.9.23"  // Replace with your Kotlin version
    id("java-library")
    id("application")

}

group = "com.fiospace"  // Replace with your group/package name
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.json:json:20240303")
    // Add other dependencies as needed
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

application {
    mainClass.set("com.fiospace.crypto_price_fetcher.CryptoPriceFetcher")
}

tasks.register<JavaExec>("runCryptoPriceFetcher") {
    group = "application"
    description = "Runs the CryptoPriceFetcher main method"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("com.fiospace.crypto_price_fetcher.CryptoPriceFetcher")
}