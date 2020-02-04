package ru.hse.spb.cli.instructions

import ru.hse.spb.cli.commands.Command

class PipelineInstruction(
    private val commands: List<Command>
) : TopLevelInstruction {
    override fun run(): List<String> =
        commands.fold(listOf()) { accumulator, command ->
            command.run(accumulator)
        }
}