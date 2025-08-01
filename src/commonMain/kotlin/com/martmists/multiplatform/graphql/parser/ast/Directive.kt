package com.martmists.multiplatform.graphql.parser.ast

import com.martmists.multiplatform.parsing.core.Loc

data class Directive(
    val name: String,
    val arguments: List<Argument>,
    override val loc: Loc
) : AstNode
