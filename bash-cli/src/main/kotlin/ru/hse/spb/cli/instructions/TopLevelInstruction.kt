package ru.hse.spb.cli.instructions

/**
 * Each [TopLevelInstruction] corresponds to one line of bash code requested to be executed by user.
 */
interface TopLevelInstruction {
    /**
     * Execute this instruction.
     * @return result of this instruction, it should be outputted.
     */
    fun run(): List<String>
}