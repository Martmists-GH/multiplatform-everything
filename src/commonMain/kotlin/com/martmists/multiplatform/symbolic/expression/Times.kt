package com.martmists.multiplatform.symbolic.expression

import com.martmists.multiplatform.symbolic.EvaluationContext
import com.martmists.multiplatform.symbolic.buildExpression

/**
 * Represents `lhs * rhs`
 */
data class Times(private val lhs: Expression, private val rhs: Expression): CompoundExpression {
    override val variables: Set<Variable>
        get() = lhs.variables + rhs.variables

    override fun evaluate(context: EvaluationContext): Expression {
        val nl = lhs.evaluate(context)
        val nr = rhs.evaluate(context)
        return if ((nl is Literal && nl.value == 0.0) || (nr is Literal && nr.value == 0.0)) {
            Literal(0.0)
        } else if (nl is Literal && nl.value == 1.0) {
            nr
        } else if (nr is Literal && nr.value == 1.0) {
            nl
        } else if (nl is Literal && nr is Literal) {
            Literal(nl.value * nr.value)
        } else if (nl == nr) {
            buildExpression { nl.pow(2) }
        } else {
            Times(nl, nr)
        }
    }

    override fun latex(): String {
        val rs = if (rhs is CompoundExpression && rhs !is Times) {
            "(${rhs.latex()})"
        } else rhs.latex()
        val ls = if (lhs is CompoundExpression && lhs !is Times) {
            "(${lhs.latex()})"
        } else lhs.latex()
        if (lhs is Literal && lhs.value == -1.0) return "-$rs"
        if (rhs is Literal && rhs.value == -1.0) return "-$ls"

        return "$ls \\times $rs"
    }
    
    /**
     * d/dx(f(x) * g(x)) = f'(x) * g(x) + f(x) * g'(x)
     */
    override fun derivative(variable: String) = buildExpression {
        lhs.derivative(variable) * rhs + lhs * rhs.derivative(variable)
    }
    
    /**
     * If f(x) is a constant c: ∫(c * g(x)) dx = c * ∫g(x) dx
     * If g(x) is a constant c: ∫(f(x) * c) dx = c * ∫f(x) dx
     * For general case, integration by parts: ∫u(x)v'(x)dx = u(x)v(x) - ∫u'(x)v(x)dx
     */
    override fun antiderivative(variable: String) = buildExpression {
        when {
            lhs is Literal -> lhs * rhs.antiderivative(variable)
            rhs is Literal -> rhs * lhs.antiderivative(variable)
            else -> {
                if (lhs.derivative(variable) == Literal(0)) {
                    lhs * rhs.antiderivative(variable)
                } else if (rhs.derivative(variable) == Literal(0)) {
                    rhs * lhs.antiderivative(variable)
                } else {
                    try {
                        val ra = rhs.antiderivative(variable)
                        lhs * ra - (lhs.derivative(variable) * ra).antiderivative(variable)
                    } catch (e: NotImplementedError) {
                        // Attempt the other way around
                        val la = lhs.antiderivative(variable)
                        rhs * la - (rhs.derivative(variable) * la).antiderivative(variable)
                    }
                }
            }
        }
    }
}
