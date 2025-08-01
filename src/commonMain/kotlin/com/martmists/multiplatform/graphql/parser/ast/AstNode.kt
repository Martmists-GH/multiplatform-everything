package com.martmists.multiplatform.graphql.parser.ast

import com.martmists.multiplatform.parsing.core.Loc

sealed interface AstNode {
    val loc: Loc
}
