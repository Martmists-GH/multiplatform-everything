package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

sealed interface AstNode {
    val loc: Loc
}
