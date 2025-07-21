package com.martmists.multiplatform.symbolic.expression

/**
 * Represents a function with custom LaTeX formatting, such as \lfloor{}\rfloor or \sqrt{}.
 */
class CustomFunction2(private val latex: SyntheticExpression.() -> String,
                      callback: (Double, Double) -> Double,
                      derivative: Function2.SyntheticExpression.(variable: String) -> Expression = { throw UnsupportedOperationException() },
                      antiderivative: Function2.SyntheticExpression.(variable: String) -> Expression = { throw UnsupportedOperationException() }
) : Function2("", "", callback, derivative, antiderivative) {
    override operator fun invoke(arg: Expression, arg2: Expression): Expression = SyntheticExpression(arg, arg2)

    inner class SyntheticExpression(arg: Expression, arg2: Expression) : Function2.SyntheticExpression(arg, arg2) {
        override fun latex(): String = this@CustomFunction2.latex.invoke(this)
    }
}
