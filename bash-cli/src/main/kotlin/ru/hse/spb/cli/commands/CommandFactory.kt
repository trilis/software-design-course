package ru.hse.spb.cli.commands

object CommandFactory {
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