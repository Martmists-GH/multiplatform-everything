package com.martmists.multiplatform.graphql.parser.ast

import com.martmists.multiplatform.parsing.core.Loc

data class FragmentDefinition(
    val name: String,
    val typeCond: NamedType,
    val directives: List<Directive>,
    val selectionSet: List<Selection>,
    override val loc: Loc
) : ExecutableDefinition
