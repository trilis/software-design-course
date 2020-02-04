package ru.hse.spb.cli.instructions

import ru.hse.spb.cli.commands.Command

/**
 * This instruction evaluates [commands] in given order.
 */
class PipelineInstruction(
    private val commands: List<Command>
) : TopLevelInstruction {

    /**
     * Executes this instruction.
     * @return output of last of [commands].
     */
    override fun run(): List<String> =
        commands.fold(listOf()) { accumulator, command ->
            command.run(accumulator)
        }
}