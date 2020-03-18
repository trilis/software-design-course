package ru.hse.spb.cli.parser

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import ru.hse.spb.cli.Context
import ru.hse.spb.cli.InterpreterException
import ru.hse.spb.cli.ParserException
import ru.hse.spb.cli.instructions.TopLevelInstruction
import java.lang.IllegalStateException

/**
 * Entry-point of parsing module.
 */
object InstructionParser {

    /**
     * This function parses line of bash code as [TopLevelInstruction].
     * @param text line to be parsed.
     * @throws ParserException if parser failed as [text] is not valid bash instruction.
     * @throws InterpreterException if some referenced variables don't exist.
     */
    fun parseInstruction(text: String, context: Context): TopLevelInstruction {
        try {
            val reader = CharStreams.fromString(text)
            val parser = BashParser(CommonTokenStream(BashLexer(reader)))
            return InstructionVisitor(context).visit(parser.instruction())
        } catch (e: IllegalStateException) {
            throw ParserException(e.message)
        }
    }
}