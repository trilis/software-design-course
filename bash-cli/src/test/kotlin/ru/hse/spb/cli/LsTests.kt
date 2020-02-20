package ru.hse.spb.cli

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ru.hse.spb.cli.TestUtils.assertListEquals
import ru.hse.spb.cli.TestUtils.resourcesDirectory
import ru.hse.spb.cli.commands.LsCommand
import java.io.File

class LsTests {
    private lateinit var context: Context

    @BeforeEach
    fun createEmptyContext() {
        context = Context()
    }

    @Test
    fun testEmptyLs() {
        val expectedList = mutableListOf(
            "dir",
            "build_gradle.txt",
            "empty.txt",
            "multi_line.txt",
            "readme.txt",
            "single_line.txt"
        )
        expectedList.sort()
        context.currentDirectory = File("").absolutePath + File.separator + resourcesDirectory
        val resultList = LsCommand(emptyList(), context).run(emptyList()).toMutableList()
        resultList.sort()
        assertListEquals(expectedList, resultList)
    }

    @Test
    fun testLsWithPath() {
        val expectedList = mutableListOf(
            "dir",
            "build_gradle.txt",
            "empty.txt",
            "multi_line.txt",
            "readme.txt",
            "single_line.txt"
        )
        expectedList.sort()
        val resultList = LsCommand(listOf(resourcesDirectory), context).run(emptyList()).toMutableList()
        resultList.sort()
        assertListEquals(expectedList, resultList)
    }

    @Test
    fun testManyArguments() {
        assertThrows<InterpreterException> {
            LsCommand(listOf("a", "b"), context).run(emptyList())
        }
    }

    @Test
    fun testOnFile() {
        context.currentDirectory = File("").absolutePath + File.separator + resourcesDirectory
        assertListEquals(
            listOf("empty.txt"),
            LsCommand(listOf("empty.txt"), context).run(emptyList())
        )
    }

    @Test
    fun testOnInvalidDirectory() {
        context.currentDirectory = File("").absolutePath + File.separator + resourcesDirectory
        assertThrows<InterpreterException> {
            LsCommand(listOf("ThisDirectoryDoesNotExist"), context).run(emptyList())
        }
    }
}
