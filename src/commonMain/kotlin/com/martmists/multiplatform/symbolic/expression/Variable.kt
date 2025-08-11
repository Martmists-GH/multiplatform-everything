package com.martmists.multiplatform.symbolic.expression

import com.martmists.multiplatform.symbolic.EvaluationContext
import com.martmists.multiplatform.symbolic.buildExpression

/**
 * Represents a named variable.
 */
data class Variable(private val latex: String) : Expression {
    override val variables: Set<Variable>
        get() = setOf(this)

    override fun evaluate(context: EvaluationContext): Expression = context.variables[latex] ?: this

    override fun latex(): String = latex

    override fun derivative(variable: String): Expression = if (latex == variable) Literal(1) else Literal(0)
    
    override fun antiderivative(variable: String) = buildExpression {
        if (latex == variable) {
            // ∫x dx = x^2/2
            this@Variable.pow(2) / 2
        } else {
            // ∫y dx = y·x
            this@Variable * Variable(variable)
        }
    }
}
