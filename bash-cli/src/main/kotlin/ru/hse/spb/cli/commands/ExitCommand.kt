package ru.hse.spb.cli.commands

import ru.hse.spb.cli.Context

/**
 * This command breaks loop of command execution.
 */
class ExitCommand : Command {

    /**
     * After executing this command and all commands that follow in current pipeline, application would stop.
     * @param input is ignored.
     * @return empty list.
     */
    override fun run(input: List<String>): List<String> {
        Context.exit()
        return listOf()
    }
}