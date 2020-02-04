package ru.hse.spb.cli

import ru.hse.spb.cli.parser.InstructionParser
import java.util.*

/**
 * Entry point of whole project. Executes instructions entered by user via standard input
 * in loop, until user submits instruction with "exit" in it for execution,
 * then stops after executing this instruction.
 *
 * All expected exceptions (children of [BashCLIException]) would be caught here and their
 * message would be outputted.
 */
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