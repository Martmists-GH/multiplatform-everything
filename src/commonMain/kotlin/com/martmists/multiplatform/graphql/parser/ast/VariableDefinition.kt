package com.martmists.multiplatform.graphql.parser.ast

import com.martmists.multiplatform.parsing.core.Loc

data class VariableDefinition(
    val variable: Variable,
    val type: Type,
    val defaultValue: Value?,
    val directives: List<Directive>,
    override val loc: Loc
) : AstNode
