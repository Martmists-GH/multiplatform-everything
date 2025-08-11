package com.martmists.multiplatform.symbolic.expression

import com.martmists.multiplatform.symbolic.EvaluationContext
import com.martmists.multiplatform.symbolic.buildExpression

/**
 * Represents `num / denom`
 */
data class Fraction(private val num: Expression, private val denom: Expression): Expression {
    override val variables: Set<Variable>
        get() = num.variables + denom.variables

    override fun evaluate(context: EvaluationContext): Expression {
        val nn = num.evaluate(context)
        val nd = denom.evaluate(context)
        return if (nn is Literal && nn.value == 0.0) {
            Literal(0)
        } else if (nd is Literal && nd.value == 1.0) {
            nn
        } else if (nd is Literal && nd.value == 0.0) {
            SpecialLiteral(Double.POSITIVE_INFINITY, "\\inf")
        } else if (nn is Literal && nd is Literal) {
            Literal(nn.value / nd.value)
        } else if (nn == nd) {
            Literal(1)
        } else {
            Fraction(nn, nd)
        }
    }

    override fun latex(): String {
        return "\\frac{${num.latex()}}{${denom.latex()}}"
    }
    
    /**
     * d/dx(f(x)/g(x)) = (f'(x)*g(x) - f(x)*g'(x))/g(x)^2
     */
    override fun derivative(variable: String) = buildExpression {
        val numerator = num.derivative(variable) * denom - num * denom.derivative(variable)
        numerator / denom.pow(2)
    }
    
    /**
     * If the denominator is a constant c: ∫(f(x)/c) dx = (1/c) * ∫f(x) dx
     * 
     * If the fraction is of the form 1/x: ∫(1/x) dx = ln|x|
     * 
     * If the fraction is of the form 1/(ax+b): ∫(1/(ax+b)) dx = (1/a) * ln|ax+b|
     * 
     * For more complex fractions, techniques like partial fraction decomposition
     * or substitution would be needed.
     */
    override fun antiderivative(variable: String): Expression {
        if (denom is Literal) {
            return buildExpression { (1 / denom) * num.antiderivative(variable) }
        }

        // TODO: Simplifying would also allow this to be 1/(ax+b) if numerator != 1
        if (num is Literal && num.value == 1.0) {
            if (denom is Variable && denom.latex() == variable) {
                return buildExpression { ln(abs(denom)) }
            }

            throw NotImplementedError("Antiderivative would need detection of form ax+b which isn't implemented!")
        }

        throw NotImplementedError("Antiderivative would need partial fraction decomposition or substitution which isn't implemented!")
    }
}
