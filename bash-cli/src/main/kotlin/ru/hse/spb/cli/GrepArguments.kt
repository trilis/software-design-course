package ru.hse.spb.cli

import com.beust.jcommander.IValueValidator
import com.beust.jcommander.Parameter

/**
 * This class validates that context length is non-negative.
 */
internal class ContextLengthValidator : IValueValidator<Int> {

    /**
     * @throws ParserException if [value] is negative.
     */
    override fun validate(name: String?, value: Int?) {
        if (value != null && value < 0) {
            throw ParserException("invalid context length argument")
        }
    }
}

/**
 * This is container for grep arguments, based on JCommander.
 */
class GrepArguments {

    @Parameter(
        names = ["-i"],
        description = "Perform case insensitive matching"
    )
    private var ignoreCase = false

    @Parameter(
        names = ["-w"],
        description = "The expression is searched for as a word"
    )
    private var wordRegexp = false

    @Parameter(
        names = ["-A"],
        description = "Print given number lines of trailing context after each match",
        arity = 1,
        validateValueWith = [ContextLengthValidator::class]
    )
    private var afterContextLines: Int? = null

    @Parameter
    private var otherArguments: MutableList<String> = mutableListOf()

    /**
     * @return true if flag "-i" is passed to grep.
     */
    fun shouldIgnoreCase() = ignoreCase

    /**
     * @return true if flag "-w" is passed to grep.
     */
    fun shouldSearchWords() = wordRegexp

    /**
     * @return if option "-A n" is passed to grep then n, else null.
     */
    fun getAfterContextLines() = afterContextLines

    /**
     * @return pattern to be used in grep.
     */
    fun getPattern() = otherArguments[0]

    /**
     * @return file names of files to be analyzed in grep.
     */
    fun getFileNames() = otherArguments.drop(1)

}