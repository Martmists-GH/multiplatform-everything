package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class EmptyElementNode(
    override val tag: String,
    override val attributes: List<AttributeNode>,
    override val loc: Loc
) : ElementNode
