package ru.hse.spb.cli

import org.junit.jupiter.api.Assertions.assertEquals
import ru.hse.spb.cli.parser.InstructionParser

internal object TestUtils {

    const val resourcesDirectory = "src/test/resources"

    fun assertListEquals(expected: List<String>, actual: List<String>) {
        assertEquals(expected.size, actual.size)
        expected.zip(actual).forEach { (expectedItem, actualItem) ->
            assertEquals(expectedItem, actualItem)
        }
    }

    fun runStringAsCommand(command: String, context: Context = Context()): List<String> {
        return InstructionParser.parseInstruction(command, context).run()
    }
}