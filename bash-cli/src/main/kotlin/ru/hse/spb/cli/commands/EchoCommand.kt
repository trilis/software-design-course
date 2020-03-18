package ru.hse.spb.cli.commands

/**
 * This command simulates behaviour of 'echo' bash command, printing its arguments.
 * @param arguments strings to be printed.
 */
class EchoCommand(private val arguments: List<String>) : Command {

    /**
     * Executes this command.
     * @param input is ignored.
     * @return [arguments] concatenated in one line with single space as separator, preserving provided order.
     */
    override fun run(input: List<String>): List<String> {
        return listOf(arguments.joinToString(separator = " "))
    }
}