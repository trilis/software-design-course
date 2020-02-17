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

    private fun tokenize(text: String): String {
        val reader = CharStreams.fromString(text)
        val parser = BashParser(CommonTokenStream(BashLexer(reader)))
        return TokenVisitor().visit(parser.token())
    }

    @Test
    fun testTokens() {
        runStringAsCommand("x=abc")
        runStringAsCommand("y=def")

        assertEquals("qwerty", tokenize("qwerty"))
        assertEquals("qwerty", tokenize("'qwerty'"))
        assertEquals("qwerty", tokenize("\"qwerty\""))

        assertEquals("abcdef", tokenize("\$x\$y"))
        assertEquals("\$x\$y", tokenize("'\$x\$y'"))
        assertEquals("abc xxx def", tokenize("\"\$x xxx \$y\""))
    }

    @Test
    fun testMismatchedQuote() {
        assertThrows<ParserException> {
            InstructionParser.parseInstruction("echo '")
        }
        assertThrows<ParserException> {
            InstructionParser.parseInstruction("echo \"")
        }
        assertThrows<ParserException> {
            InstructionParser.parseInstruction("echo 'xx\"")
        }
    }

    @Test
    fun testEmptyString() {
        assertThrows<ParserException> {
            InstructionParser.parseInstruction("")
        }
    }

    @Test
    fun testVariableInEndingOfToken() {
        runStringAsCommand("x=1")

        assertEquals("aaa1", tokenize("\"aaa\$x\""))
        assertEquals("'1231'", tokenize("\"'123\$x'\""))
        assertEquals("1231'", tokenize("\"123\$x'\""))
    }
}