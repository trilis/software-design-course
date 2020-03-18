package ru.hse.spb.cli.instructions

import ru.hse.spb.cli.Context

/**
 * This instruction creates new environment variable with name [variable] if there wasn't one already
 * and initializes it with [value].
 */
class AssignmentInstruction(
    private val variable: String,
    private val value: String,
    private val context: Context
) :
    TopLevelInstruction {

    /**
     * Executes this instruction.
     * @return empty list.
     */
    override fun run(): List<String> {
        context.setVariable(variable, value)
        return listOf()
    }
}