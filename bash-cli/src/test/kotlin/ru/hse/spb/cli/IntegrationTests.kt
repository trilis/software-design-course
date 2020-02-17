package ru.hse.spb.cli

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import ru.hse.spb.cli.TestUtils.assertListEquals
import ru.hse.spb.cli.TestUtils.resourcesDirectory
import ru.hse.spb.cli.TestUtils.runStringAsCommand

class IntegrationTests {
    @Test
    fun testSimple() {
        runStringAsCommand("FILE=example.txt")
        assertListEquals(
            listOf("example.txt"),
            runStringAsCommand("echo \$FILE")
        )
    }

    @Test
    fun testQuoting() {
        assertListEquals(
            listOf("\$FILE"),
            runStringAsCommand("echo '\$FILE'")
        )
        runStringAsCommand("FILE=example.txt")
        assertListEquals(
            listOf("example.txt"),
            runStringAsCommand("echo \"\$FILE\"")
        )
    }

    @Test
    fun testRewrite() {
        runStringAsCommand("FILE=example.txt")
        runStringAsCommand("FILE=example2.txt")
        assertListEquals(
            listOf("example2.txt"),
            runStringAsCommand("echo \$FILE")
        )
    }

    @Test
    fun testPartsOfOneToken() {
        runStringAsCommand("x=pw")
        runStringAsCommand("y=d")
        assertListEquals(
            listOf(System.getProperty("user.dir")),
            runStringAsCommand("\$x\$y")
        )
    }

    @Test
    fun testNonExistingVariable() {
        assertThrows<InterpreterException> {
            runStringAsCommand("echo \$xyz")
        }
    }

    @Test
    fun testPipeline() {
        assertListEquals(
            listOf("1 3 17"),
            runStringAsCommand(
                "cat $resourcesDirectory/single_line.txt | wc"
            )
        )
        assertListEquals(
            listOf("1 1 3"),
            runStringAsCommand("echo 123 | wc")
        )
    }

    @Test
    fun testPipelineWithUnknownCommands() {
        assertDoesNotThrow {
            runStringAsCommand("git tag AAA")
            runStringAsCommand("git tag bbb")
        }
        assertListEquals(
            listOf("2 2 6"),
            runStringAsCommand("git tag | wc")
        )
        assertDoesNotThrow {
            runStringAsCommand("git tag -d AAA")
            runStringAsCommand("git tag -d bbb")
        }
    }

}