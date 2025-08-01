package com.martmists.multiplatform.graphql.parser.ast

import com.martmists.multiplatform.parsing.core.Loc

data class Field(
    val alias: String?,
    val name: String,
    val arguments: List<Argument>,
    val directives: List<Directive>,
    val selectionSet: List<Selection>,
    override val loc: Loc
) : Selection
