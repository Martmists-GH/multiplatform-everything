package com.martmists.multiplatform.symbolic

import com.martmists.multiplatform.symbolic.expression.CustomFunction2
import com.martmists.multiplatform.symbolic.expression.Expression
import com.martmists.multiplatform.symbolic.expression.Fraction
import com.martmists.multiplatform.symbolic.expression.Function1
import com.martmists.multiplatform.symbolic.expression.Function2
import com.martmists.multiplatform.symbolic.expression.Literal
import com.martmists.multiplatform.symbolic.expression.Minus
import com.martmists.multiplatform.symbolic.expression.Plus
import com.martmists.multiplatform.symbolic.expression.Power
import com.martmists.multiplatform.symbolic.expression.Product
import com.martmists.multiplatform.symbolic.expression.SpecialLiteral
import com.martmists.multiplatform.symbolic.expression.Sum
import com.martmists.multiplatform.symbolic.expression.Times
import com.martmists.multiplatform.symbolic.expression.Variable
import kotlin.math.*

class ExpressionBuilderScope internal constructor() {
    // Common variables
    val pi: Expression = SpecialLiteral(PI, "\\pi")
    val tau: Expression = SpecialLiteral(2 * PI, "\\tau")
    val e: Expression = SpecialLiteral(E, "e")

    // Creates a variable
    fun variable(latex: String): Expression = Variable(latex)

    // Creates a user-defined function. May optionally include derivative/antiderivatives.
    fun function(name: String, callback: (Double) -> Double,
                 derivative: Function1.SyntheticExpression.(variable: String) -> Expression = { throw UnsupportedOperationException() },
                 antiderivative: Function1.SyntheticExpression.(variable: String) -> Expression = { throw UnsupportedOperationException() }): Function1 = Function1("$name(", ")", callback, derivative, antiderivative)
    fun function(name: String, callback: (Double, Double) -> Double,
                 derivative: Function2.SyntheticExpression.(variable: String) -> Expression = { throw UnsupportedOperationException() },
                 antiderivative: Function2.SyntheticExpression.(variable: String) -> Expression = { throw UnsupportedOperationException() }): Function2 = Function2("$name(", ")", callback, derivative, antiderivative)

    // Common math operators
    operator fun Expression.unaryMinus(): Expression = Times(Literal(-1), this)

    operator fun Number.plus(other: Expression): Expression = Plus(Literal(this), other)
    operator fun Expression.plus(other: Number): Expression = Plus(this, Literal(other))
    operator fun Expression.plus(other: Expression): Expression = Plus(this, other)

    operator fun Number.minus(other: Expression): Expression = Minus(Literal(this), other)
    operator fun Expression.minus(other: Number): Expression = Minus(this, Literal(other))
    operator fun Expression.minus(other: Expression): Expression = Minus(this, other)

    operator fun Number.times(other: Expression): Expression = Times(Literal(this), other)
    operator fun Expression.times(other: Number): Expression = Times(this, Literal(other))
    operator fun Expression.times(other: Expression): Expression = Times(this, other)

    operator fun Number.div(other: Expression): Expression = Fraction(Literal(this), other)
    operator fun Expression.div(other: Number): Expression = Fraction(this, Literal(other))
    operator fun Expression.div(other: Expression): Expression = Fraction(this, other)

    fun Number.pow(other: Expression): Expression = Power(Literal(this), other)
    fun Expression.pow(other: Number): Expression = Power(this, Literal(other))
    fun Expression.pow(other: Expression): Expression = Power(this, other)

    // Common math functions
    val sqrt: Function1 = Function1("\\sqrt{", "}", ::sqrt,
        { arg.derivative(it) / (2 * sqrt(arg)) },
        { (2/3.0) * arg.pow(1.5) / arg.derivative(it) }
    )
    
    val floor: Function1 = Function1("\\lfloor{", "}\\rfloor", ::floor)
    val ceil: Function1 = Function1("\\lceil{", "}\\rceil", ::ceil)
    val abs: Function1 = Function1("|", "|", ::abs)

    val sin: Function1 = Function1("\\sin(", ")", ::sin,
        { cos(arg) * arg.derivative(it) },
        { -cos(arg) / arg.derivative(it) }
    )
    val cos: Function1 = Function1("\\cos(", ")", ::cos,
        { -sin(arg) * arg.derivative(it) },
        { sin(arg) / arg.derivative(it) }
    )
    val tan: Function1 = Function1("\\tan(", ")", ::tan,
        { arg.derivative(it) / cos(arg).pow(2) },
        { -ln(abs(cos(arg))) / arg.derivative(it) }
    )
    val sinh: Function1 = Function1("\\sinh(", ")", ::sinh,
        { cosh(arg) * arg.derivative(it) },
        { cosh(arg) }
    )
    val cosh: Function1 = Function1("\\cosh(", ")", ::cosh,
        { sinh(arg) * arg.derivative(it) },
        { sinh(arg) }
    )
    val tanh: Function1 = Function1("\\tanh(", ")", ::tanh,
        { arg.derivative(it) / cosh(arg).pow(2) },
        { ln(cosh(arg)) }
    )

    val log: Function1 = Function1("\\log(", ")", ::log10,
        { arg.derivative(it) / (ln(10) * arg) },
        { (arg * log(arg) - arg / ln(10)) / arg.derivative(it) }
    )
    val ln: Function1 = Function1("\\ln(", ")", ::ln,
        { arg.derivative(it) / arg },
        { (arg * ln(arg) - arg) / arg.derivative(it) }
    )
    val logBase: Function2 = CustomFunction2(
        {"log_{${arg2.latex()}}(${arg.latex()})"},
        ::log,
        { arg.derivative(it) / (ln(arg2) * arg) },
        { (arg * logBase(arg, arg2) - arg / ln(arg2)) / arg.derivative(it) }
    )
    val exp: Function1 = Function1("\\exp(", ")", ::exp,
        { exp(arg) * arg.derivative(it) },
        { exp(arg) / arg.derivative(it) }
    )

    // Procedures
    fun sum(loopVar: String, from: Number, to: Number, expression: (loopVar: Expression) -> Expression): Expression = sum(loopVar, Literal(from), Literal(to), expression)
    fun sum(loopVar: String, from: Number, to: Expression, expression: (loopVar: Expression) -> Expression): Expression = sum(loopVar, Literal(from), to, expression)
    fun sum(loopVar: String, from: Expression, to: Number, expression: (loopVar: Expression) -> Expression): Expression = sum(loopVar, from, Literal(to), expression)
    fun sum(loopVar: String, from: Expression, to: Expression, expression: (loopVar: Expression) -> Expression): Expression = Sum(loopVar, from, to, expression.invoke(Variable(loopVar)))

    fun product(loopVar: String, from: Number, to: Number, expression: (loopVar: Expression) -> Expression): Expression = product(loopVar, Literal(from), Literal(to), expression)
    fun product(loopVar: String, from: Number, to: Expression, expression: (loopVar: Expression) -> Expression): Expression = product(loopVar, Literal(from), to, expression)
    fun product(loopVar: String, from: Expression, to: Number, expression: (loopVar: Expression) -> Expression): Expression = product(loopVar, from, Literal(to), expression)
    fun product(loopVar: String, from: Expression, to: Expression, expression: (loopVar: Expression) -> Expression): Expression = Product(loopVar, from, to, expression.invoke(Variable(loopVar)))
}
