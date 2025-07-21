package com.martmists.multiplatform.symbolic

import com.martmists.multiplatform.symbolic.expression.Expression
import com.martmists.multiplatform.symbolic.expression.Literal
import kotlin.jvm.JvmName

/**
 * Contains context for evaluation. At present only contains variables, may contain settings in the future.
 */
class EvaluationContext internal constructor(
    val variables: Map<String, Expression>
) {
    fun with(args: Map<String, Expression>): EvaluationContext = EvaluationContext(variables + args)
    fun with(vararg args: Pair<String, Expression>): EvaluationContext = with(args.toMap())

    @JvmName("withNumber")
    fun with(args: Map<String, Number>): EvaluationContext = with(variables + args.mapValues { (k, v) -> Literal(v) as Expression })
    @JvmName("withNumber")
    fun with(vararg args: Pair<String, Number>): EvaluationContext = with(args.toMap())
}
