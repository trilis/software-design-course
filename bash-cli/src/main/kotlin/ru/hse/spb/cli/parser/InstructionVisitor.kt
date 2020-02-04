package ru.hse.spb.cli.parser

import ru.hse.spb.cli.instructions.AssignmentInstruction
import ru.hse.spb.cli.instructions.PipelineInstruction
import ru.hse.spb.cli.instructions.TopLevelInstruction

class InstructionVisitor : BashBaseVisitor<TopLevelInstruction>() {
    override fun visitAssignment(ctx: BashParser.AssignmentContext): TopLevelInstruction {
        return AssignmentInstruction(ctx.variable.text, TokenVisitor().visit(ctx.value))
    }

    override fun visitPipeline(ctx: BashParser.PipelineContext): TopLevelInstruction {
        return PipelineInstruction(ctx.commands.map { CommandVisitor().visit(it) })
    }
}