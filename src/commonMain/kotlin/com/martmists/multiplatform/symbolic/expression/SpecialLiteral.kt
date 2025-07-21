package com.martmists.multiplatform.symbolic.expression

import com.martmists.multiplatform.symbolic.EvaluationContext

/**
 * Represents a [Literal] with specific LaTeX expression, such as `\pi`.
 */
class SpecialLiteral(value: Double, val latex: String) : Literal(value) {
    override fun evaluate(context: EvaluationContext) = this

    override fun latex() = latex

    override fun toString(): String = "SpecialLiteral(value=$value, latex=$latex)"
}
