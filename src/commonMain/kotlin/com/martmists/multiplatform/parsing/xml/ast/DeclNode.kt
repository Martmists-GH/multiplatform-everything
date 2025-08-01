package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class DeclNode(
    val version: String,
    val encoding: String?,
    val sd: Boolean?,
    override val loc: Loc
) : AstNode
