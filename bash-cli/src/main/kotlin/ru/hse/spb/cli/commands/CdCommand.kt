package ru.hse.spb.cli.commands

import ru.hse.spb.cli.Context
import ru.hse.spb.cli.InterpreterException
import java.io.File
import java.nio.file.Paths

class CdCommand(private val arguments: List<String>, private val context: Context) : Command {
    override fun run(input: List<String>): List<String> {
        when {
            arguments.isEmpty() -> {
                changeDirectoryTo(System.getProperty("user.home"))
            }
            arguments.size == 1 -> {
                changeDirectoryTo(context.toAbsoluteFileName(arguments[0]))
            }
            else -> {
                throw InterpreterException("cd: too many arguments")
            }
        }
        return emptyList()
    }

    private fun changeDirectoryTo(newDirectoryName: String) {
        val newDirectory = File(newDirectoryName)
        if (!newDirectory.exists()) {
            throw InterpreterException("cd: No such file or directory")
        }
        if (newDirectory.isFile) {
            throw InterpreterException("cd: Not a directory")
        }
        context.currentDirectory = Paths.get(newDirectory.absolutePath).normalize().toString()
    }
}
