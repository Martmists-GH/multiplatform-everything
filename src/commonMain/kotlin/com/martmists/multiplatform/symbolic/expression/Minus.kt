package com.martmists.multiplatform.symbolic.expression

import com.martmists.multiplatform.symbolic.EvaluationContext
import com.martmists.multiplatform.symbolic.buildExpression

/**
 * Represents `lhs - rhs`
 */
data class Minus(private val lhs: Expression, private val rhs: Expression): CompoundExpression {
    override val variables: Set<Variable>
        get() = lhs.variables + rhs.variables

    override fun evaluate(context: EvaluationContext): Expression {
        val nl = lhs.evaluate(context)
        val nr = rhs.evaluate(context)
        return if (nl is Literal && nl.value == 0.0) {
            buildExpression { -nr }
        } else if (nr is Literal && nr.value == 0.0) {
            nl
        } else if (nl is Literal && nr is Literal) {
            Literal(nl.value - nr.value)
        } else if (nl == nr) {
            Literal(0.0)
        } else {
            Minus(nl, nr)
        }
    }

    override fun latex(): String {
        val rs = if (rhs is CompoundExpression && rhs !is Times) {
            "(${rhs.latex()})"
        } else rhs.latex()
        if (rs.startsWith("-")) return "${lhs.latex()}+${rs.substring(1)}"
        return "${lhs.latex()}-$rs"
    }
    
    /**
     * d/dx(f(x) - g(x)) = d/dx(f(x)) - d/dx(g(x))
     */
    override fun derivative(variable: String) = buildExpression {
        lhs.derivative(variable) - rhs.derivative(variable)
    }
    
    /**
     * ∫(f(x) - g(x)) dx = ∫f(x) dx - ∫g(x) dx
     */
    override fun antiderivative(variable: String) = buildExpression {
        lhs.antiderivative(variable) - rhs.antiderivative(variable)
    }
}
