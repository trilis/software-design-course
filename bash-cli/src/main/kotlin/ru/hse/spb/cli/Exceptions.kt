package ru.hse.spb.cli

/**
 * Parent exception for all exceptions expected to be thrown in project.
 * If thrown, this exception would be caught in [main], where its [message] will be outputted.
 */
sealed class BashCLIException(message: String?) : RuntimeException(message)

/**
 * Class for exceptions thrown in interpreter part of project.
 */
class InterpreterException(message: String?) : BashCLIException(message)

/**
 * Class for exceptions thrown in parser part of project.
 */
class ParserException(message: String?) : BashCLIException(message)