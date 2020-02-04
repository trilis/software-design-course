package ru.hse.spb.cli.parser

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import ru.hse.spb.cli.ParserException
import ru.hse.spb.cli.instructions.TopLevelInstruction
import java.lang.IllegalStateException

object InstructionParser {
    fun parseInstruction(text: String): TopLevelInstruction {
        try {
            val reader = CharStreams.fromString(text)
            val parser = BashParser(CommonTokenStream(BashLexer(reader)))
            return InstructionVisitor().visit(parser.instruction())
        } catch (e: IllegalStateException) {
            throw ParserException(e.message)
        }
    }
}