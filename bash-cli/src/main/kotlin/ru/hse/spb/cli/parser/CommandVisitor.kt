package ru.hse.spb.cli.parser

import ru.hse.spb.cli.commands.Command
import ru.hse.spb.cli.commands.CommandFactory

class CommandVisitor : BashBaseVisitor<Command>() {
    override fun visitCommand(ctx: BashParser.CommandContext): Command {
        val name = TokenVisitor().visit(ctx.name)
        val arguments = ctx.arguments.map {
            TokenVisitor().visit(it)
        }
        return CommandFactory.createCommand(name, arguments)
    }
}