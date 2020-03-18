package ru.hse.spb.cli.commands

import ru.hse.spb.cli.Context
import ru.hse.spb.cli.InterpreterException
import java.io.IOException
import java.io.InputStreamReader

/**
 * Class for every bash command or executable not provided with builtin implementation.
 *
 * @param name name of this command.
 * @param arguments arguments of this command.
 */
class UnknownCommand(
    private val name: String,
    private val arguments: List<String>,
    private val context: Context
) : Command {

    /**
     * This method executes command as a new [Process] on given [input].
     *
     * @return contents of input and error streams of this command, concatenated.
     *
     * @throws InterpreterException if command with [name] was not found,
     * or if this command did not expect provided [input].
     */
    override fun run(input: List<String>): List<String> {
        val processBuilder = ProcessBuilder(name, *arguments.toTypedArray())
        processBuilder.environment().putAll(context.getVariables())
        try {
            val process = processBuilder.start()
            process.outputStream.write(
                input.joinToString(separator = System.lineSeparator()).toByteArray()
            )
            process.outputStream.close()
            process.waitFor()
            return InputStreamReader(process.inputStream).useLines { lines ->
                lines.toList()
            } + InputStreamReader(process.errorStream).useLines { lines ->
                lines.toList()
            }
        } catch (e: IOException) {
            throw InterpreterException(e.message)
        }
    }

}