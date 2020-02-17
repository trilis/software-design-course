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
        val context = Context()

        runStringAsCommand("FILE=example.txt", context)
        assertListEquals(
            listOf("example.txt"),
            runStringAsCommand("echo \$FILE", context)
        )
    }

    @Test
    fun testQuoting() {
        val context = Context()

        assertListEquals(
            listOf("\$FILE"),
            runStringAsCommand("echo '\$FILE'", context)
        )
        runStringAsCommand("FILE=example.txt", context)
        assertListEquals(
            listOf("example.txt"),
            runStringAsCommand("echo \"\$FILE\"", context)
        )
    }

    @Test
    fun testRewrite() {
        val context = Context()

        runStringAsCommand("FILE=example.txt", context)
        runStringAsCommand("FILE=example2.txt", context)
        assertListEquals(
            listOf("example2.txt"),
            runStringAsCommand("echo \$FILE", context)
        )
    }

    @Test
    fun testPartsOfOneToken() {
        val context = Context()

        runStringAsCommand("x=pw", context)
        runStringAsCommand("y=d", context)
        assertListEquals(
            listOf(System.getProperty("user.dir")),
            runStringAsCommand("\$x\$y", context)
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