package ru.hse.spb.cli.commands

import java.io.File

class PwdCommand : Command {
    override fun run(input: List<String>): List<String> =
        listOf(File("").absolutePath)
}