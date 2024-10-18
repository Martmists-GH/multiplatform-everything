package com.martmists.multiplatform.graphql.parser.ast

import com.martmists.multiplatform.graphql.parser.Loc

data class FragmentSpread(
    val name: String,
    val directives: List<Directive>,
    override val loc: Loc
) : Selection
