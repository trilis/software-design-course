package ru.hse.spb.cli

sealed class BashCLIException(message: String?) : RuntimeException(message)

class InterpreterException(message: String?) : BashCLIException(message)

class ParserException(message: String?) : BashCLIException(message)