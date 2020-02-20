package ru.hse.spb.cli.commands

import com.beust.jcommander.JCommander
import com.beust.jcommander.ParameterException
import ru.hse.spb.cli.Context
import ru.hse.spb.cli.GrepArguments
import ru.hse.spb.cli.InterpreterException
import ru.hse.spb.cli.ParserException
import java.io.FileReader
import java.io.IOException

/**
 * This command simulates behaviour of 'grep' bash command, finding lines that match pattern.
 *
 * @param rawArgumentTokens arguments of this command, provided as tokens. When this class is initialized,
 * these arguments would be parsed using JCommander, see [GrepArguments] for details.
 * @param context context that has information about current directory.
 */
class GrepCommand(rawArgumentTokens: List<String>, private val context: Context) : Command {

    private var parsedArguments = GrepArguments()

    init {
        try {
            JCommander.newBuilder().addObject(parsedArguments).build().parse(*rawArgumentTokens.toTypedArray())
        } catch (e: ParameterException) {
            throw ParserException("pattern is required")
        }
    }

    private fun checkIfMatches(line: String): Boolean {
        val regex = Regex(
            if (parsedArguments.shouldSearchWords()) {
                "\\b${parsedArguments.getPattern()}\\b"
            } else {
                parsedArguments.getPattern()
            },
            options = if (parsedArguments.shouldIgnoreCase()) {
                setOf(RegexOption.IGNORE_CASE)
            } else {
                setOf()
            }
        )
        return line.contains(regex)
    }

    private fun findMatches(text: List<String>, prefix: String? = null): List<String> {
        val result = mutableListOf<String>()
        var lastMatch: Int? = null

        text.forEachIndexed { index, line ->
            if (checkIfMatches(line)) {
                lastMatch = index
                result.add(if (prefix != null) "$prefix:$line" else line)
            } else if (parsedArguments.getAfterContextLines() != null) {
                if (lastMatch != null && index - lastMatch!! <= parsedArguments.getAfterContextLines()!!) {
                    result.add(if (prefix != null) "$prefix-$line" else line)
                }
            }
        }

        return result
    }

    private fun findMatchesInFiles(fileNames: List<String>): List<String> {
        try {
            return fileNames.flatMap { fileName ->
                FileReader(context.toAbsoluteFileName(fileName)).useLines { lines ->
                    if (fileNames.size == 1) {
                        findMatches(lines.toList())
                    } else {
                        findMatches(lines.toList(), prefix = fileName)
                    }
                }
            }
        } catch (e: IOException) {
            throw InterpreterException(e.message)
        }
    }

    /**
     * Executes this command.
     *
     * If no file names were provided in arguments, this method will analyze [input].
     * If some file names were provided, [input] would be ignored and this method will
     * analyze files with given file names.
     *
     * @throws InterpreterException if some of files cannot be opened or read from.
     */
    override fun run(input: List<String>): List<String> {
        val fileNames = parsedArguments.getFileNames()

        return if (fileNames.isEmpty()) {
            findMatches(input)
        } else {
            findMatchesInFiles(fileNames)
        }
    }
}