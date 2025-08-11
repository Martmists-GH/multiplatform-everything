package com.martmists.multiplatform.symbolic.expression

import com.martmists.multiplatform.symbolic.EvaluationContext

/**
 * Represents a function with one argument (such as sin, log, etc).
 * A derivative or antiderivative may be specified, but is not required.
 */
data class Function1(private val prefix: String, private val suffix: String,
                     private val callback: (Double) -> Double,
                     private val derivative: SyntheticExpression.(variable: String) -> Expression = { throw UnsupportedOperationException() },
                     private val antiderivative: SyntheticExpression.(variable: String) -> Expression = { throw UnsupportedOperationException() },
) {
    operator fun invoke(arg: Number): Expression = invoke(Literal(arg))
    operator fun invoke(arg: Expression): Expression = SyntheticExpression(arg)

    inner class SyntheticExpression(val arg: Expression) : Expression {
        override val variables: Set<Variable>
            get() = arg.variables

        override fun evaluate(context: EvaluationContext): Expression {
            val na = arg.evaluate(context)
            return if (na is Literal) {
                Literal(callback(na.value))
            } else SyntheticExpression(na)
        }

        override fun latex(): String = "$prefix${arg.latex()}$suffix"

        override fun derivative(variable: String): Expression = derivative.invoke(this, variable)
        override fun antiderivative(variable: String): Expression = antiderivative.invoke(this, variable)
    }
}
