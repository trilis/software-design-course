package ru.hse.spb.cli.commands

import java.io.File

/**
 * This command simulates behaviour of 'pwd' bash command, printing current directory.
 */
class PwdCommand : Command {

    /**
     * Executes this command.
     * @param input is ignored.
     * @return absolute path of current directory.
     */
    override fun run(input: List<String>): List<String> =
        listOf(File("").absolutePath)
}