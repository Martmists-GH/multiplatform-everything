package com.martmists.multiplatform.graphql.parser.ast

import com.martmists.multiplatform.parsing.core.Loc

data class InlineFragment(
    val typeCond: NamedType?,
    val directives: List<Directive>,
    val selectionSet: List<Selection>,
    override val loc: Loc
) : Selection
