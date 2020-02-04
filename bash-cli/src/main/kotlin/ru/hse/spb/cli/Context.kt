package ru.hse.spb.cli

/**
 * Data shared between instructions.
 */
object Context {
    private val variables: MutableMap<String, String> = mutableMapOf()
    private var exitFlag: Boolean = false

    /**
     * Creates new environment variable with name [variable] if there wasn't one already
     * and initializes it with [value].
     */
    fun setVariable(variable: String, value: String) {
        variables[variable] = value
    }

    /**
     * Returns value of variable with name [variable].
     * @throws InterpreterException if there is no variable with name [variable].
     */
    fun getVariable(variable: String): String {
        return variables[variable] ?: throw InterpreterException("Variable $variable not found")
    }

    /**
     * Returns immutable copy of all variables, this is used to put environment to created subprocess.
     */
    fun getVariables(): Map<String, String> {
        return variables.toMap()
    }

    /**
     * No more instructions could be executed after this function is called.
     */
    fun exit() {
        exitFlag = true
    }

    /**
     * Check if instruction execution loop should end.
     * @return true if "exit" command was executed earlier.
     */
    fun shouldExit(): Boolean {
        return exitFlag
    }
}