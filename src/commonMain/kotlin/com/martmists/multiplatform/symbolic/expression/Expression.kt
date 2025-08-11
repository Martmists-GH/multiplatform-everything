package com.martmists.multiplatform.symbolic.expression

import com.martmists.multiplatform.symbolic.EvaluationContext
import kotlin.jvm.JvmName

interface Expression {
    /**
     * Evaluates the expression in the given context
     * This may return any Expression, not just the same type or Literals
     */
    fun evaluate(context: EvaluationContext): Expression

    /**
     * Returns the LaTeX for this expression
     */
    fun latex(): String

    /**
     * Recursively triggers simplifications in `evaluate` blocks to attempt to simplify the expression tree
     */
    fun simplify(): Expression = evaluate(EvaluationContext(emptyMap()))

    /**
     * Returns an expression representing the derivative of this expression relative to `variable`
     */
    fun derivative(variable: String): Expression

    /**
     * Returns an expression representing the Antiderivative of this expression relative to `variable`.
     *
     * Note: This does not include the constant C!
     *       This seemed too difficult to efficiently optimize out, so it's not implemented at all instead.
     * Note: This is not a complete implementation and may fail!
     *       Antiderivatives are not guaranteed to be closed-form, and some that are, are not easily expressed in a way the code can handle.
     */
    fun antiderivative(variable: String): Expression

    val variables: Set<Variable>

    /**
     * Evaluate the expression with the given values for variables.
     */
    operator fun invoke(args: Map<String, Expression>): Expression = evaluate(EvaluationContext(args))
    operator fun invoke(vararg args: Pair<String, Expression>): Expression = evaluate(EvaluationContext(args.toMap()))

    @Suppress("INAPPLICABLE_JVM_NAME", "USELESS_CAST")
    @JvmName("invokeNumber")
    operator fun invoke(args: Map<String, Number>): Expression = invoke(args.mapValues { (k, v) -> Literal(v) as Expression } as Map<String, Expression>)

    @Suppress("INAPPLICABLE_JVM_NAME")
    @JvmName("invokeNumber")
    operator fun invoke(vararg args: Pair<String, Number>): Expression = invoke(args.toMap())

    @Suppress("INAPPLICABLE_JVM_NAME", "USELESS_CAST")
    @JvmName("invokeAny")
    operator fun invoke(args: Map<String, Any>): Expression = invoke(args.mapValues { (k, v) -> when (v) {
        is Expression -> v
        is Number -> Literal(v) as Expression
        else -> error("Only numbers and expressions are allowed!")
    }} as Map<String, Expression>)

    @Suppress("INAPPLICABLE_JVM_NAME")
    @JvmName("invokeAny")
    operator fun invoke(vararg args: Pair<String, Any>): Expression = invoke(args.toMap())
}

// Custom expression for things that may need parentheses, should only be used internally
interface CompoundExpression : Expression
