package ru.hse.spb.cli.parser

import ru.hse.spb.cli.instructions.AssignmentInstruction
import ru.hse.spb.cli.instructions.PipelineInstruction
import ru.hse.spb.cli.instructions.TopLevelInstruction

/**
 * This visitor is responsible for creating [TopLevelInstruction] instances.
 */
class InstructionVisitor : BashBaseVisitor<TopLevelInstruction>() {

    /**
     * Creates and returns [AssignmentInstruction] based on parser context.
     * This method uses [TokenVisitor] to transform tokens in it.
     */
    override fun visitAssignment(ctx: BashParser.AssignmentContext): TopLevelInstruction {
        return AssignmentInstruction(ctx.variable.text, TokenVisitor().visit(ctx.value))
    }

    /**
     * Creates and returns [PipelineInstruction] based on parser context.
     * This method uses [CommandVisitor] to visit parts of pipeline.
     */
    override fun visitPipeline(ctx: BashParser.PipelineContext): TopLevelInstruction {
        return PipelineInstruction(ctx.commands.map { CommandVisitor().visit(it) })
    }
}