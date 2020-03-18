package ru.hse.spb.cli.commands

/**
 * Interface for bash commands. Each [Command] can be used as a part of a pipeline,
 * or be called independently.
 */
interface Command {

    /**
     * This method executes command on given [input].
     *
     * @param input result of previous command in a pipeline.
     * If this command is the first one in a pipeline, [input] should be empty.
     *
     * @return result of this command. This result should be given as [input] to
     * the next command in a pipeline, or outputted if this is last command on a pipeline.
     */
    fun run(input: List<String>): List<String>
}