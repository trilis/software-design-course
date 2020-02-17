package ru.hse.spb.cli.parser

import ru.hse.spb.cli.Context
import ru.hse.spb.cli.InterpreterException

/**
 * This visitor is responsible for transforming tokens replacing variable references with their values.
 */
class TokenVisitor(private val context: Context) : BashBaseVisitor<String>() {

    private fun replaceVariables(text: String): String {
        return text.replace(Regex("\\$([^$\\s']+)")) {
            val variable = it.groupValues[1]
            context.getVariable(variable)
        }
    }

    /**
     * This method is called for tokens in single quotes, it returns token without changes.
     */
    override fun visitSingle(ctx: BashParser.SingleContext): String {
        return ctx.singleQuoted.joinToString(separator = "") { it.text }
    }

    /**
     * This method is called for tokens in double quotes, it returns token with replaced variable references.
     *
     * @throws InterpreterException if some referenced variables don't exist.
     */
    override fun visitDouble(ctx: BashParser.DoubleContext): String {
        return replaceVariables(ctx.doubleQuoted.joinToString(separator = "") { it.text })
    }

    /**
     * This method is called for tokens not enclosed in quotes,
     * it returns token with replaced variable references.
     *
     * @throws InterpreterException if some referenced variables don't exist.
     */
    override fun visitSimple(ctx: BashParser.SimpleContext): String {
        return replaceVariables(ctx.simpleToken.text)
    }

}