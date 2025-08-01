package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class AttDefNode(
    val name: String,
    val type: String,
    val default: String?,
    override val loc: Loc
) : AstNode
