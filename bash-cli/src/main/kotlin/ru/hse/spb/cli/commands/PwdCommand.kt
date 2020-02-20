package ru.hse.spb.cli.commands

import ru.hse.spb.cli.Context
import java.io.File

/**
 * This command simulates behaviour of 'pwd' bash command, printing current directory.
 */
class PwdCommand(private val context: Context) : Command {

    /**
     * Executes this command.
     * @param input is ignored.
     * @return absolute path of current directory.
     */
    override fun run(input: List<String>): List<String> = listOf(context.currentDirectory)
}