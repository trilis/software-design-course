package ru.hse.spb.cli.commands

import ru.hse.spb.cli.Context
import ru.hse.spb.cli.GrepArguments
import ru.hse.spb.cli.InterpreterException
import java.io.File

/**
 * This command simulates behaviour of 'ls' bash command, showing files in current directory.
 *
 * @param arguments directory to show files in. If empty, current directory will be used.
 * @param context context that has information about current directory.
 */
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
