package com.martmists.multiplatform.graphql.parser.ast

import com.martmists.multiplatform.validation.lexer.Loc

sealed interface AstNode {
    val loc: Loc
}
