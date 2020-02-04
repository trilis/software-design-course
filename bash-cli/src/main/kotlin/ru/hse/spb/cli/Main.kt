package ru.hse.spb.cli

import ru.hse.spb.cli.parser.InstructionParser
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    while (!Context.shouldExit()) {
        try {
            val instruction = InstructionParser.parseInstruction(scanner.nextLine())
            val resultLines = instruction.run()
            if (resultLines.isNotEmpty()) {
                println(resultLines.joinToString(separator = System.lineSeparator()))
            }
        } catch (e: BashCLIException) {
            println(e.message)
        }
    }
}