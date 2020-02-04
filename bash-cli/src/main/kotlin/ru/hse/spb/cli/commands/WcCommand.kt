package ru.hse.spb.cli.commands

import ru.hse.spb.cli.InterpreterException
import java.io.FileReader
import java.io.IOException
import java.nio.charset.Charset
import java.util.*

class WcCommand(private val fileNames: List<String>) : Command {

    private fun count(lines: List<String>): String {
        val lineCount = lines.size
        val wordCount = lines.map { StringTokenizer(it).countTokens() }.sum()
        val byteCount = lines.map {
            (it + System.lineSeparator()).toByteArray(Charset.defaultCharset()).size
        }.sum()
        return "$lineCount $wordCount $byteCount"
    }

    override fun run(input: List<String>): List<String> {
        return if (fileNames.isEmpty()) {
            listOf(count(input))
        } else {
            try {
                fileNames.map { fileName ->
                    FileReader(fileName).useLines { lines ->
                        "${count(lines.toList())} $fileName"
                    }
                }
            } catch (e: IOException) {
                throw InterpreterException(e.message)
            }
        }

    }

}