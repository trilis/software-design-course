package ru.hse.spb.cli.instructions

interface TopLevelInstruction {
    fun run(): List<String>
}