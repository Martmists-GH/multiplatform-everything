package com.martmists.multiplatform.graphql.parser.ast

import com.martmists.multiplatform.validation.lexer.Loc

data class ListType(val type: Type,
                    override val loc: Loc
) : Type
