package ru.hse.spb.cli.commands

import ru.hse.spb.cli.Context
import ru.hse.spb.cli.InterpreterException
import java.io.IOException
import java.io.InputStreamReader

class UnknownCommand(private val name: String, private val arguments: List<String>) : Command {
    override fun run(input: List<String>): List<String> {
        val processBuilder = ProcessBuilder(name, *arguments.toTypedArray())
        processBuilder.environment().clear()
        processBuilder.environment().putAll(Context.getVariables())

        val process = processBuilder.start()
        process.waitFor()

        try {
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