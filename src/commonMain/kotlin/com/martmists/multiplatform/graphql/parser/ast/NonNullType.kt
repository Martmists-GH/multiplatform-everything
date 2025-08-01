package com.martmists.multiplatform.graphql.parser.ast

import com.martmists.multiplatform.parsing.core.Loc

data class NonNullType(val type: Type,
                       override val loc: Loc
) : Type
