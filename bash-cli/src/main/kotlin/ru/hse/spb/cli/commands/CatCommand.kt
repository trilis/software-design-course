package ru.hse.spb.cli.commands

import ru.hse.spb.cli.InterpreterException
import java.io.FileReader
import java.io.IOException

class CatCommand(private val fileNames: List<String>) : Command {
    override fun run(input: List<String>): List<String> {
        return if (fileNames.isEmpty()) {
            input
        } else {
            try {
                fileNames.flatMap { fileName ->
                    FileReader(fileName).useLines { lines ->
                        lines.toList()
                    }
                }
            } catch (e: IOException) {
                throw InterpreterException(e.message)
            }
        }
    }
}