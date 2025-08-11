package com.martmists.multiplatform.symbolic.expression

import com.martmists.multiplatform.symbolic.EvaluationContext
import com.martmists.multiplatform.symbolic.buildExpression
import kotlin.math.pow

/**
 * Represents `value ^ exponent`
 */
data class Power(private val value: Expression, private val exponent: Expression): Expression {
    override val variables: Set<Variable>
        get() = value.variables + exponent.variables

    override fun evaluate(context: EvaluationContext): Expression {
        val nv = value.evaluate(context)
        val ne = exponent.evaluate(context)
        return if (nv is Literal && ne is Literal) {
            Literal(nv.value.pow(ne.value))
        } else if (nv is Literal && (nv.value == 0.0 || nv.value == 1.0)) {
            nv
        } else if (ne is Literal && ne.value == 0.0) {
            Literal(1)
        } else if (ne is Literal && ne.value == 1.0) {
            nv
        } else if (ne is Literal && ne.value < 0) {
            buildExpression {
                1 / nv.pow(abs(ne.value))
            }
        } else if (nv is Power) {
            Power(nv.value, Times(nv.exponent, ne)).simplify()
        } else {
            Power(nv, ne)
        }
    }

    override fun latex(): String {
        val vs = if (value is CompoundExpression || value is Fraction) {
            "(${value.latex()})"
        } else value.latex()
        return "$vs^{${exponent.latex()}}"
    }
    
    /**
     * If the exponent is constant:
     * d/dx(f(x)^n) = n * f(x)^(n-1) * f'(x)
     * 
     * If the base is constant:
     * d/dx(a^f(x)) = a^f(x) * ln(a) * f'(x)
     * 
     * For the general case:
     * d/dx(f(x)^g(x)) = f(x)^g(x) * (g'(x) * ln(f(x)) + g(x) * f'(x) / f(x))
     */
    override fun derivative(variable: String) = buildExpression {
        val dv = value.derivative(variable)
        val de = exponent.derivative(variable)

        if (de is Literal && de.value == 0.0) {
            return@buildExpression exponent * value.pow(exponent - 1) * dv
        }
        
        if (dv is Literal && dv.value == 0.0) {
            return@buildExpression this@Power * ln(value) * de
        }

        this@Power * (de * ln(value) + exponent * dv / value)
    }
    
    /**
     * If the base is the variable and the exponent is constant (n):
     *    ∫x^n dx = x^(n+1)/(n+1) for n ≠ -1
     * 
     * If the base is the variable and the exponent is -1:
     *    ∫x^(-1) dx = ∫(1/x) dx = ln|x|
     * 
     * If the base is constant (a) and the exponent is linear in the variable:
     *    ∫a^x dx = a^x/ln(a)
     * 
     * For other cases, there's no general formula? Error.
     */
    override fun antiderivative(variable: String) = buildExpression {
        if (value is Variable && value.latex() == variable) {
            if (exponent is Literal) {
                return@buildExpression if (exponent.value == -1.0) {
                    ln(abs(value))
                } else {
                    value.pow(exponent + 1) / (exponent + 1)
                }
            }

            throw NotImplementedError("Antiderivative of x^f(x) is not implemented!")
        }

        if (value is Literal && exponent.derivative(variable) != Literal(0)) {
            if (exponent is Variable && exponent.latex() == variable) {
                return@buildExpression this@Power / ln(value)
            }
            
            throw NotImplementedError("Antiderivative of a^f(x) is not implemented!")
        }
        
        throw NotImplementedError("A closed-form antiderivative does not exist! (if this is incorrect, let me know)")
    }
}
