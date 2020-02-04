package ru.hse.spb.cli

object Context {
    private val variables: MutableMap<String, String> = mutableMapOf()
    private var exitFlag: Boolean = false

    fun setVariable(variable: String, value: String) {
        variables[variable] = value
    }

    fun getVariable(variable: String): String {
        return variables[variable] ?: throw InterpreterException("Variable $variable not found")
    }

    fun getVariables(): Map<String, String> {
        return variables.toMap()
    }

    fun exit() {
        exitFlag = true
    }

    fun shouldExit(): Boolean {
        return exitFlag
    }
}