package ru.hse.spb.cli

import org.junit.jupiter.api.Test
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
            listOf("1 3 18"),
            runStringAsCommand(
                "cat $resourcesDirectory/single_line.txt | wc"
            )
        )
        assertListEquals(
            listOf("1 1 4"),
            runStringAsCommand("echo 123 | wc")
        )
        assertListEquals(
            listOf("aba", "caba"),
            runStringAsCommand(
                "cat $resourcesDirectory/multi_line.txt | head -n 2"
            )
        )
    }
}