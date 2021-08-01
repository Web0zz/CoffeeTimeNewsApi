val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val kodein_version: String by project
val koin_version: String by project

plugins {
    application
    kotlin("jvm") version "1.5.21"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.5.21"
}

group = "com.web0zz"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
    mavenCentral()
}

dependencies {
    // Ktor
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-auth-jwt:$ktor_version")
    implementation("io.ktor:ktor-serialization:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")

    // Logging
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // Kodein
    implementation("org.kodein.db:kodein-leveldb-jni-jvm-linux:$kodein_version")
    implementation("org.kodein.db:kodein-db:$kodein_version")
    implementation("org.kodein.db:kodein-db-serializer-kotlinx:$kodein_version")

    // Koin
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")

    // Testing
    testImplementation("io.insert-koin:koin-test:$koin_version")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
}