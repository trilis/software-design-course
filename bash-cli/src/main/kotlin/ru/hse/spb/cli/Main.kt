package ru.hse.spb.cli

import ru.hse.spb.cli.parser.InstructionParser
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    while (!Context.shouldExit) {
        val instruction = InstructionParser.parseInstruction(scanner.nextLine())
        val result = instruction.run().joinToString(separator = System.lineSeparator())
        if (result.isNotEmpty()) {
            println(result)
        }
    }
}