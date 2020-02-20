package ru.hse.spb.cli.commands

import ru.hse.spb.cli.Context
import ru.hse.spb.cli.InterpreterException
import java.io.File

class CdCommand(private val arguments: List<String>, private val context: Context) : Command {
    override fun run(input: List<String>): List<String> {
        when {
            arguments.isEmpty() -> {
                changeDirectoryTo(System.getProperty("user.home"))
            }
            arguments.size == 1 -> {
                changeDirectoryTo(
                    "${File("").absolutePath}${File.pathSeparator}${arguments[0]}"
                )
            }
            else -> {
                throw InterpreterException("cd: too many arguments")
            }
        }
        return emptyList()
    }

    private fun changeDirectoryTo(newDirectoryName: String) {
        TODO()
    }


}