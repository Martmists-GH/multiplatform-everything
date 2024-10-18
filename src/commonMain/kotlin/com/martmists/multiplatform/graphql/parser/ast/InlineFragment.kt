package com.martmists.multiplatform.graphql.parser.ast

import com.martmists.multiplatform.validation.lexer.Loc

data class InlineFragment(
    val typeCond: Type?,
    val directives: List<Directive>,
    val selectionSet: List<Selection>,
    override val loc: Loc
) : Selection
