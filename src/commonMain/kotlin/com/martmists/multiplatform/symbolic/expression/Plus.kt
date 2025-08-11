package com.martmists.multiplatform.symbolic.expression

import com.martmists.multiplatform.symbolic.EvaluationContext
import com.martmists.multiplatform.symbolic.buildExpression

/**
 * Represents `lhs + rhs`
 */
data class Plus(private val lhs: Expression, private val rhs: Expression): CompoundExpression {
    override val variables: Set<Variable>
        get() = lhs.variables + rhs.variables

    override fun evaluate(context: EvaluationContext): Expression {
        val nl = lhs.evaluate(context)
        val nr = rhs.evaluate(context)
        return if (nl is Literal && nl.value == 0.0) {
            nr
        } else if (nr is Literal && nr.value == 0.0) {
            nl
        } else if (nl is Literal && nr is Literal) {
            Literal(nl.value + nr.value)
        } else if (nl == nr) {
            Times(Literal(2.0), nl)
        } else {
            Plus(nl, nr)
        }
    }

    override fun latex(): String {
        val rs = rhs.latex()
        if (rs.startsWith("-")) return lhs.latex() + rs
        return "${lhs.latex()}+$rs"
    }
    
    /**
     * d/dx(f(x) + g(x)) = d/dx(f(x)) + d/dx(g(x))
     */
    override fun derivative(variable: String) = buildExpression {
        lhs.derivative(variable) + rhs.derivative(variable)
    }
    
    /**
     * ∫(f(x) + g(x)) dx = ∫f(x) dx + ∫g(x) dx
     */
    override fun antiderivative(variable: String) = buildExpression {
        lhs.antiderivative(variable) + rhs.antiderivative(variable)
    }
}
