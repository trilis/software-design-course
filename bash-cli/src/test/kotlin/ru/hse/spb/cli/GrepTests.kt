package ru.hse.spb.cli

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ru.hse.spb.cli.TestUtils.assertListEquals
import ru.hse.spb.cli.TestUtils.resourcesDirectory
import ru.hse.spb.cli.TestUtils.runStringAsCommand

class GrepTests {
    @Test
    fun testInputOrFile() {
        assertListEquals(
            listOf(
                "aba",
                "caba",
                "daba"
            ),
            runStringAsCommand("grep aba ${resourcesDirectory}/multi_line.txt")
        )
        assertListEquals(
            listOf(
                "aba",
                "caba",
                "daba"
            ),
            runStringAsCommand("cat ${resourcesDirectory}/multi_line.txt | grep aba")
        )
        assertListEquals(
            listOf(),
            runStringAsCommand("grep . ${resourcesDirectory}/empty.txt")
        )
        assertListEquals(
            listOf(),
            runStringAsCommand("cat ${resourcesDirectory}/multi_line.txt |grep . ${resourcesDirectory}/empty.txt")
        )
    }

    @Test
    fun testWordBoundaries() {
        assertListEquals(
            listOf(
                "aba"
            ),
            runStringAsCommand("grep -w aba ${resourcesDirectory}/multi_line.txt")
        )
        assertListEquals(
            listOf(
                "    kotlin(\"jvm\") version \"1.3.61\"",
                "    implementation(kotlin(\"stdlib-jdk8\"))"
            ),
            runStringAsCommand("grep -w kotlin ${resourcesDirectory}/build_gradle.txt")
        )
    }

    @Test
    fun testIgnoreCase() {
        assertListEquals(
            listOf(
                "    mavenCentral()"
            ),
            runStringAsCommand(
                "grep -i mavencentral ${resourcesDirectory}/build_gradle.txt"
            )
        )
        assertListEquals(
            listOf(),
            runStringAsCommand(
                "grep mavencentral ${resourcesDirectory}/build_gradle.txt"
            )
        )
    }

    @Test
    fun testContext() {
        assertListEquals(
            listOf(
                "    kotlin(\"jvm\") version \"1.3.61\"",
                "    antlr",
                "    implementation(kotlin(\"stdlib-jdk8\"))",
                "    implementation(\"org.antlr:antlr4-runtime:4.7\")",
                "    compileKotlin {",
                "        kotlinOptions.jvmTarget = \"1.8\"",
                "        dependsOn(generateGrammarSource)",
                "    compileTestKotlin {",
                "        kotlinOptions.jvmTarget = \"1.8\"",
                "    }"
            ),
            runStringAsCommand("grep -i -A 1 kotlin ${resourcesDirectory}/build_gradle.txt")
        )
        assertListEquals(
            listOf(
                "    testImplementation(\"org.junit.jupiter:junit-jupiter:5.6.0\")",
                "    antlr(\"org.antlr:antlr4:4.7\")",
                "}",
                "",
                "        useJUnitPlatform()",
                "    }",
                "}"
            ),
            runStringAsCommand("grep -i -A 3 junit ${resourcesDirectory}/build_gradle.txt")
        )
        assertListEquals(
            listOf(
                "src/test/resources/build_gradle.txt:    outputDirectory = File(\"\$buildDir/generated-src/antlr/main/ru/hse/spb/cli/parser\")",
                "src/test/resources/build_gradle.txt-}",
                "src/test/resources/build_gradle.txt-",
                "src/test/resources/readme.txt:В функции `main` инструкции обрабатываются по циклу. Каждая инструкция от пользователя проходит через модуль парсинга, а затем исполняется, результат команды выводится. Все части проекта имеют доступ к общему контексту `Context`, в котором хранятся environment variables и exit flag.",
                "src/test/resources/readme.txt-",
                "src/test/resources/readme.txt-#### Парсинг",
                "src/test/resources/readme.txt:`ExitCommand` выставляет в true exit flag в `Context`. `main` после выполнения каждой инструкции проверяет, выставлен ли этот флаг, если да, то завершается.",
                "src/test/resources/readme.txt-",
                "src/test/resources/readme.txt-При вызове остальных команд взаимодействия с другими частями программы не происходит."
            ),
            runStringAsCommand("grep -A 2 main ${resourcesDirectory}/build_gradle.txt ${resourcesDirectory}/readme.txt")
        )
    }

    @Test
    fun testNonExistingFile() {
        assertThrows<InterpreterException> {
            runStringAsCommand("grep . unknown.txt")
        }
    }
}