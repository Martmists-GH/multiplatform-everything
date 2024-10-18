package com.martmists.multiplatform.graphql.parser.ast

import com.martmists.multiplatform.validation.lexer.Loc

data class Argument(
    val name: String,
    val value: Value,
    override val loc: Loc
) : AstNode
