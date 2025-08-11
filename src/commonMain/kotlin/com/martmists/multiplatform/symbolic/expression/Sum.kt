package com.martmists.multiplatform.symbolic.expression

import com.martmists.multiplatform.symbolic.EvaluationContext
import com.martmists.multiplatform.symbolic.buildExpression

/**
 * Represents a \sum.
 */
data class Sum(private val loopVar: String, private val from: Expression, private val to: Expression, private val expression: Expression) : CompoundExpression {
    override val variables: Set<Variable>
        get() = from.variables + to.variables + expression.variables.filter { it.latex() != loopVar }

    override fun evaluate(context: EvaluationContext): Expression {
        val nf = from.evaluate(context)
        val nt = to.evaluate(context)
        val ne = expression.evaluate(context)

        if (nf is Literal && nt is Literal) {
            val steps = nt.value - nf.value
            if ((steps) % 1 != 0.0) {
                return Sum(loopVar, nf, nt, ne)
            }
            // attempt to evaluate once
            val start = ne.evaluate(context.with(loopVar to nf))
            if (start is Literal) {
                // We can evaluate!
                // TODO: Maybe custom procedures/functions can break this? Needs more testing
                var sum = start.value
                repeat(steps.toInt() - 1) {
                    val loopValue = nf.value + it + 1
                    sum += (ne.evaluate(context.with(loopVar to loopValue)) as Literal).value
                }
                return Literal(sum)
            }
        }

        return Sum(loopVar, nf, nt, ne)
    }

    override fun latex() = "\\sum_{$loopVar=${from.latex()}}^{${to.latex()}}{${expression.latex()}}"

    // d/dx ∑_{a = f(x)}^{g(x)} h(a, x)
    // = ∑_{a = f(x)}^{g(x)} ∂h/∂x(a, x)
    //   + h(g(x), x) * g'(x)
    //   - h(f(x) - 1, x) * f'(x)
    override fun derivative(variable: String) = buildExpression {
        Sum(loopVar, from, to, expression.antiderivative(variable)) +
            expression(mapOf(loopVar to to)) * to.derivative(variable) -
            expression(mapOf(loopVar to (from - 1))) * from.derivative(variable)
    }

    override fun antiderivative(variable: String): Expression {
        if (from is Literal && to is Literal) {
            val steps = to.value - from.value
            if ((steps) % 1 == 0.0) {
                // Evaluate on each step
                return Sum(loopVar, from, to, expression.antiderivative(variable)).simplify()
            }
        }

        throw NotImplementedError("Sums don't have a closed-form solution for antiderivatives if the bounds are not constants!")
    }
}
