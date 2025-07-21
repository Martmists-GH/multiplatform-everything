package com.martmists.multiplatform.symbolic

import com.martmists.multiplatform.symbolic.expression.Expression
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Provides a DSL to build an expression
 */
@OptIn(ExperimentalContracts::class)
fun buildExpression(block: ExpressionBuilderScope.() -> Expression): Expression {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return ExpressionBuilderScope().block().simplify()
}
