package ru.hse.spb.cli

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import ru.hse.spb.cli.TestUtils.assertListEquals
import ru.hse.spb.cli.TestUtils.resourcesDirectory
import ru.hse.spb.cli.commands.CatCommand
import ru.hse.spb.cli.commands.EchoCommand
import ru.hse.spb.cli.commands.ExitCommand
import ru.hse.spb.cli.commands.PwdCommand
import ru.hse.spb.cli.commands.UnknownCommand
import ru.hse.spb.cli.commands.WcCommand

class CommandTests {

    @Test
    fun testExit() {
        assertFalse(Context.shouldExit())
        ExitCommand().run(listOf())
        assertTrue(Context.shouldExit())
    }

    @Test
    fun testPwd() {
        println(System.getProperty("user.dir"))
        assertListEquals(
            listOf(System.getProperty("user.dir")),
            PwdCommand().run(listOf())
        )
    }

    @Test
    fun testEcho() {
        assertListEquals(
            listOf("a b"),
            EchoCommand(listOf("a", "b")).run(listOf())
        )
        assertListEquals(
            listOf("c"),
            EchoCommand(listOf("c")).run(listOf())
        )
        assertListEquals(
            listOf("  a"),
            EchoCommand(listOf(" ", "a")).run(listOf())
        )
    }

    @Test
    fun testCat() {
        assertListEquals(
            listOf(),
            CatCommand(listOf("$resourcesDirectory/empty.txt")).run(listOf())
        )
        assertListEquals(
            listOf("some example text"),
            CatCommand(listOf("$resourcesDirectory/single_line.txt")).run(listOf())
        )
        assertListEquals(
            listOf("aba", "caba", "daba"),
            CatCommand(listOf("$resourcesDirectory/multi_line.txt")).run(listOf())
        )

        assertListEquals(
            listOf("aba", "caba", "daba", "some example text"),
            CatCommand(
                listOf(
                    "$resourcesDirectory/multi_line.txt", "$resourcesDirectory/single_line.txt"
                )
            ).run(listOf())
        )

        assertListEquals(
            listOf("123", "456"),
            CatCommand(listOf()).run(listOf("123", "456"))
        )
        assertListEquals(
            listOf(),
            CatCommand(listOf("$resourcesDirectory/empty.txt")).run(listOf("123", "456"))
        )
    }

    @Test
    fun testWc() {
        assertListEquals(
            listOf("0 0 0 $resourcesDirectory/empty.txt"),
            WcCommand(listOf("$resourcesDirectory/empty.txt")).run(listOf())
        )
        assertListEquals(
            listOf("1 3 17 $resourcesDirectory/single_line.txt"),
            WcCommand(listOf("$resourcesDirectory/single_line.txt")).run(listOf())
        )
        assertListEquals(
            listOf("3 3 11 $resourcesDirectory/multi_line.txt"),
            WcCommand(listOf("$resourcesDirectory/multi_line.txt")).run(listOf())
        )
        assertListEquals(
            listOf("1 1 3"),
            WcCommand(listOf()).run(listOf("123"))
        )
        assertListEquals(
            listOf("0 0 0 $resourcesDirectory/empty.txt"),
            WcCommand(listOf("$resourcesDirectory/empty.txt")).run(listOf("123"))
        )
    }

    @Test
    fun testUnknownCommand() {
        assertDoesNotThrow {
            UnknownCommand("git", listOf()).run(listOf())
        }
        assertDoesNotThrow {
            UnknownCommand("gradle", listOf()).run(listOf())
        }
    }

    @Test
    fun testExceptions() {
        assertThrows<InterpreterException> {
            CatCommand(listOf("unknown.txt")).run(listOf())
        }
        assertThrows<InterpreterException> {
            WcCommand(listOf("unknown.txt")).run(listOf())
        }
        assertThrows<InterpreterException> {
            UnknownCommand("unknown", listOf()).run(listOf())
        }
    }

}