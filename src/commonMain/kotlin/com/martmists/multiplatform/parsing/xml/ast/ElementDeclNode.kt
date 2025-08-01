package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class ElementDeclNode(
    val name: String,
    val content: ContentSpecNode,
    override val loc: Loc
) : IntSubsetNode
