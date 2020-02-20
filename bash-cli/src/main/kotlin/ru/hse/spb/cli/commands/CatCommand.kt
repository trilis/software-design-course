package ru.hse.spb.cli.commands

import ru.hse.spb.cli.Context
import ru.hse.spb.cli.InterpreterException
import java.io.FileReader
import java.io.IOException

/**
 * This command simulates behaviour of 'cat' bash command, printing provided texts concatenated.
 *
 * @param fileNames names of files to be printed.
 */
class CatCommand(private val fileNames: List<String>, private val context: Context) : Command {

    /**
     * Executes this command.
     * If no [fileNames] were provided, this method will return [input] without changes.
     * If some [fileNames] were provided, [input] would be ignored and this method will return
     * content of provided files concatenated in the same order as in [fileNames].
     *
     * @throws InterpreterException if some of files cannot be opened or read from.
     */
    override fun run(input: List<String>): List<String> {
        return if (fileNames.isEmpty()) {
            input
        } else {
            try {
                fileNames.flatMap { fileName ->
                    FileReader(context.toAbsoluteFileName(fileName)).useLines { lines ->
                        lines.toList()
                    }
                }
            } catch (e: IOException) {
                throw InterpreterException(e.message)
            }
        }
    }
}