package ru.hse.spb.cli

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ru.hse.spb.cli.TestUtils.runStringAsCommand
import ru.hse.spb.cli.parser.BashLexer
import ru.hse.spb.cli.parser.BashParser
import ru.hse.spb.cli.parser.InstructionParser
import ru.hse.spb.cli.parser.TokenVisitor

class ParserTests {

    private fun tokenize(text: String, context: Context = Context()): String {
        val reader = CharStreams.fromString(text)
        val parser = BashParser(CommonTokenStream(BashLexer(reader)))
        return TokenVisitor(context).visit(parser.token())
    }

    @Test
    fun testTokens() {
        val context = Context()

        runStringAsCommand("x=abc", context)
        runStringAsCommand("y=def", context)

        assertEquals("qwerty", tokenize("qwerty", context))
        assertEquals("qwerty", tokenize("'qwerty'", context))
        assertEquals("qwerty", tokenize("\"qwerty\"", context))

        assertEquals("abcdef", tokenize("\$x\$y", context))
        assertEquals("\$x\$y", tokenize("'\$x\$y'", context))
        assertEquals("abc xxx def", tokenize("\"\$x xxx \$y\"", context))
    }

    @Test
    fun testMismatchedQuote() {
        assertThrows<ParserException> {
            InstructionParser.parseInstruction("echo '", Context())
        }
        assertThrows<ParserException> {
            InstructionParser.parseInstruction("echo \"", Context())
        }
        assertThrows<ParserException> {
            InstructionParser.parseInstruction("echo 'xx\"", Context())
        }
    }

    @Test
    fun testEmptyString() {
        assertThrows<ParserException> {
            InstructionParser.parseInstruction("", Context())
        }
    }

    @Test
    fun testVariableInEndingOfToken() {
        val context = Context()

        runStringAsCommand("x=1", context)

        assertEquals("aaa1", tokenize("\"aaa\$x\"", context))
        assertEquals("'1231'", tokenize("\"'123\$x'\"", context))
        assertEquals("1231'", tokenize("\"123\$x'\"", context))
    }
}