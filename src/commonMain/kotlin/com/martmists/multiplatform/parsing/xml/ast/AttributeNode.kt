package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class AttributeNode(
    val name: String,
    val value: String,
    override val loc: Loc
) : AstNode
