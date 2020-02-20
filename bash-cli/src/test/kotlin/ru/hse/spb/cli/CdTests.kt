package ru.hse.spb.cli

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ru.hse.spb.cli.TestUtils.resourcesDirectory
import ru.hse.spb.cli.commands.CdCommand
import java.io.File
import java.nio.file.Paths

class CdTests {
    private lateinit var context: Context

    @BeforeEach
    fun createEmptyContext() {
        context = Context()
    }

    @Test
    fun testEmptyCd() {
        CdCommand(emptyList(), context).run(emptyList())
        assertEquals(System.getProperty("user.home"), context.currentDirectory)
    }

    @Test
    fun testCdToTestDirectory() {
        CdCommand(listOf(resourcesDirectory), context).run(emptyList())
        val expectedFileName = Paths.get(
            File(resourcesDirectory).absolutePath
        ).normalize().toString()
        assertEquals(expectedFileName, context.currentDirectory)
    }

    @Test
    fun testCdToPreviousDirectory() {
        CdCommand(listOf(resourcesDirectory), context).run(emptyList())
        CdCommand(listOf(".."), context).run(emptyList())
        val expectedFileName = Paths.get(
            File("$resourcesDirectory${File.separator}..").absolutePath
        ).normalize().toString()
        assertEquals(expectedFileName, context.currentDirectory)
    }

    @Test
    fun testManyArguments() {
        assertThrows<InterpreterException> {
            CdCommand(listOf("a", "b", "c"), context).run(emptyList())
        }
    }

    @Test
    fun testIncorrectDirectory() {
        assertThrows<InterpreterException> {
            CdCommand(listOf("ThisDirectoryDoesNotExist"), context).run(emptyList())
        }
    }

    @Test
    fun testCdToFile() {
        assertThrows<InterpreterException> {
            CdCommand(listOf("settings.gradle"), context).run(emptyList())
        }
    }
}
