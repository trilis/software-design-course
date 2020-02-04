package ru.hse.spb.cli.commands

class EchoCommand(private val arguments: List<String>): Command {
    override fun run(input: List<String>): List<String> {
        return listOf(arguments.joinToString(separator = " "))
    }
}