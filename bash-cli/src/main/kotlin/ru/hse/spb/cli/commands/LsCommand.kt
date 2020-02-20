package ru.hse.spb.cli.commands

import ru.hse.spb.cli.Context
import ru.hse.spb.cli.InterpreterException
import java.io.File

class LsCommand(private val arguments: List<String>, private val context: Context) : Command {
    override fun run(input: List<String>): List<String> {
        return when {
            arguments.isEmpty() -> {
                listDirectoryContents("")
            }
            arguments.size == 1 -> {
                listDirectoryContents(arguments[0])
            }
            else -> {
                throw InterpreterException("ls: too many arguments")
            }
        }
    }

    private fun listDirectoryContents(directoryShortName: String): List<String> {
        val directory = File(context.toAbsoluteFileName(directoryShortName))
        if (!directory.exists()) {
            throw InterpreterException("ls: No such file or directory")
        }
        if (directory.isFile) {
            return listOf(directoryShortName)
        }
        return directory.list()!!.toList()
    }
}