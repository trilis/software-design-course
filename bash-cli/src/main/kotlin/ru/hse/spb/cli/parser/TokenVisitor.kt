package ru.hse.spb.cli.parser

import ru.hse.spb.cli.Context

class TokenVisitor : BashBaseVisitor<String>() {

    private fun replaceVariables(text: String): String {
        return text.replace(Regex("\\$([^$\\s]+)")) {
            val variable = it.groupValues[1]
            Context.getVariable(variable)
        }
    }

    override fun visitSingle(ctx: BashParser.SingleContext): String {
        return ctx.singleQuoted.joinToString(separator = "") { it.text }
    }

    override fun visitDouble(ctx: BashParser.DoubleContext): String {
        return replaceVariables(ctx.doubleQuoted.joinToString(separator = "") { it.text })
    }

    override fun visitSimple(ctx: BashParser.SimpleContext): String {
        return replaceVariables(ctx.simpleToken.text)
    }

}