package ru.hse.spb.cli.commands

import ru.hse.spb.cli.InterpreterException
import java.io.FileReader
import java.io.IOException
import java.nio.charset.Charset
import java.util.*

/**
 * This command simulates behaviour of 'wc' bash command,
 * counting lines, words and bytes of provided texts.
 *
 * @param fileNames names of files to be examined.
 */
class WcCommand(private val fileNames: List<String>) : Command {

    private fun count(lines: List<String>): String {
        val lineCount = lines.size
        val wordCount = lines.map { StringTokenizer(it).countTokens() }.sum()
        val byteCount = lines.map {
            (it + System.lineSeparator()).toByteArray(Charset.defaultCharset()).size
        }.sum()
        return "$lineCount $wordCount $byteCount"
    }

    /**
     * Executes this command.
     * If no [fileNames] were provided, this method will count lines, words and bytes
     * of [input] and return them formatted as "l w b".
     * If some [fileNames] were provided, [input] would be ignored, method will count
     * lines, words and bytes for each file and return list of results in the same order
     * as in [fileNames], formatted as "l w b filename"
     *
     * @throws InterpreterException if some of files cannot be opened or read from.
     */
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