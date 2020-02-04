package ru.hse.spb.cli.commands

import ru.hse.spb.cli.Context

class ExitCommand : Command {
    override fun run(input: List<String>): List<String> {
        Context.exit()
        return listOf()
    }
}