package com.martmists.multiplatform.symbolic.expression

import com.martmists.multiplatform.symbolic.EvaluationContext
import com.martmists.multiplatform.symbolic.buildExpression
import kotlin.math.PI

/**
 * Represents a number literal
 */
open class Literal(val value: Double) : Expression {
    override val variables: Set<Variable>
        get() = emptySet()

    constructor(value: Number) : this(value.toDouble())

    init {
        if (value == PI && this !is SpecialLiteral) { error("Not Special \\pi!") }
    }

    override fun evaluate(context: EvaluationContext) = this

    override fun latex(): String {
        return if (value % 1 == 0.0) {
            value.toInt().toString()
        } else {
            value.toString()
        }
    }

    override fun derivative(variable: String): Expression = Literal(0)
    
    // ∫c dx = c·x
    override fun antiderivative(variable: String) = buildExpression { value * variable(variable) }

    override fun equals(other: Any?): Boolean = other is Literal && other.value == value
    override fun toString() = "Literal(value=$value)"
    override fun hashCode(): Int {
        return value.hashCode()
    }
}
