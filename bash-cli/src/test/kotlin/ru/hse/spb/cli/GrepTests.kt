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
                "src/test/resources/readme.txt:`main`",
                "src/test/resources/readme.txt-",
                "src/test/resources/readme.txt-####",
                "src/test/resources/readme.txt:`ExitCommand``Context``main`",
                "src/test/resources/readme.txt-",
                "src/test/resources/readme.txt-."
            ),
            runStringAsCommand("grep -A 2 main ${resourcesDirectory}/build_gradle.txt ${resourcesDirectory}/readme.txt")
        )
    }

    @Test
    fun testInvalid() {
        assertThrows<ParserException> {
            runStringAsCommand("grep -A -5 main ${resourcesDirectory}/build_gradle.txt")
        }
        assertThrows<ParserException> {
            runStringAsCommand("grep -A 5")
        }
        assertThrows<ParserException> {
            runStringAsCommand("grep")
        }
        assertThrows<InterpreterException> {
            runStringAsCommand("grep . unknown.txt")
        }
    }
}