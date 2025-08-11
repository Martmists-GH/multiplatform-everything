package com.martmists.multiplatform.symbolic.expression

import com.martmists.multiplatform.symbolic.EvaluationContext

/**
 * Represents a function with two arguments. See also [Function1]
 */
open class Function2(private val prefix: String, private val suffix: String,
                     private val callback: (Double, Double) -> Double,
                     private val derivative: SyntheticExpression.(variable: String) -> Expression = { throw UnsupportedOperationException() },
                     private val antiderivative: SyntheticExpression.(variable: String) -> Expression = { throw UnsupportedOperationException() }
) {
    operator fun invoke(arg: Number, arg2: Number): Expression = invoke(Literal(arg), Literal(arg2))
    operator fun invoke(arg: Number, arg2: Expression): Expression = invoke(Literal(arg), arg2)
    operator fun invoke(arg: Expression, arg2: Number): Expression = invoke(arg, Literal(arg2))
    open operator fun invoke(arg: Expression, arg2: Expression): Expression = SyntheticExpression(arg, arg2)

    open inner class SyntheticExpression(val arg: Expression, val arg2: Expression) : Expression {
        override val variables: Set<Variable>
            get() = arg.variables + arg2.variables

        override fun evaluate(context: EvaluationContext): Expression {
            val na1 = arg.evaluate(context)
            val na2 = arg2.evaluate(context)
            return if (na1 is Literal && na2 is Literal) {
                Literal(callback(na1.value, na2.value))
            } else SyntheticExpression(na1, na2)
        }

        override fun latex(): String = "$prefix${arg.latex()},${arg2.latex()}$suffix"

        override fun derivative(variable: String): Expression = derivative.invoke(this, variable)
        override fun antiderivative(variable: String): Expression = antiderivative.invoke(this, variable)
    }
}
