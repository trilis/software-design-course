package ru.hse.spb.cli.parser

import ru.hse.spb.cli.commands.Command
import ru.hse.spb.cli.commands.CommandFactory

/**
 * This visitor is responsible for creating [Command] instances.
 */
class CommandVisitor : BashBaseVisitor<Command>() {

    /**
     * Creates and returns [Command] based on parser context.
     * This method uses [TokenVisitor] to transform tokens in it.
     */
    override fun visitCommand(ctx: BashParser.CommandContext): Command {
        val name = TokenVisitor().visit(ctx.name)
        val arguments = ctx.arguments.map {
            TokenVisitor().visit(it)
        }
        return CommandFactory.createCommand(name, arguments)
    }
}