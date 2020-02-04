package ru.hse.spb.cli.instructions

import ru.hse.spb.cli.Context

class AssignmentInstruction(private val variable: String, private val value: String) :
    TopLevelInstruction {
    override fun run(): List<String> {
        Context.setVariable(variable, value)
        return listOf()
    }
}