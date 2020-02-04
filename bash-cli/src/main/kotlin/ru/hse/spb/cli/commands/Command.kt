package ru.hse.spb.cli.commands

interface Command {
    fun run(input: List<String>): List<String>
}