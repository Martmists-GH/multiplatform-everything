package com.martmists.multiplatform.graphql.parser.ast

import com.martmists.multiplatform.graphql.parser.Loc

sealed interface AstNode {
    val loc: Loc
}
