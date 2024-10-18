package com.martmists.multiplatform.graphql.parser.ast

import com.martmists.multiplatform.validation.lexer.Loc

data class NamedType(val name: String,
                     override val loc: Loc
) : Type
