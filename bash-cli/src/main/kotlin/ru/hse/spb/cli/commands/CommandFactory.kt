package ru.hse.spb.cli.commands

/**
 * Factory for creating instances of [Command].
 */
object CommandFactory {

    /**
     * This function creates [Command].
     *
     * @param name name of command to be created.
     * @param arguments arguments of this command.
     * @return created command.
     */
    fun createCommand(name: String, arguments: List<String>): Command {
        return when (name) {
            "cat" -> CatCommand(arguments)
            "echo" -> EchoCommand(arguments)
            "exit" -> ExitCommand()
            "pwd" -> PwdCommand()
            "wc" -> WcCommand(arguments)
            else -> UnknownCommand(name, arguments)
        }
    }
}