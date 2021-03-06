plugins {
    kotlin("jvm") version "1.3.61"
    antlr
}

group = "ru.hse.spb"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.antlr:antlr4-runtime:4.7")
    implementation("com.beust:jcommander:1.78")
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.0")
    antlr("org.antlr:antlr4:4.7")
}

tasks.generateGrammarSource {
    maxHeapSize = "64m"
    arguments.addAll(listOf("-package", "ru.hse.spb.cli.parser", "-visitor"))
    outputDirectory = File("$buildDir/generated-src/antlr/main/ru/hse/spb/cli/parser")
}

task("launch", JavaExec::class)  {
    dependsOn(":build")
    main = "ru.hse.spb.cli.MainKt"
    classpath = sourceSets["main"].runtimeClasspath
    standardInput = System.`in`
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
        dependsOn(generateGrammarSource)
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    test {
        useJUnitPlatform()
    }
}