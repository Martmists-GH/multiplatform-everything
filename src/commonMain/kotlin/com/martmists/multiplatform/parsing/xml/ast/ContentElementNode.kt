package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class ContentElementNode(
    override val tag: String,
    override val attributes: List<AttributeNode>,
    val elementEntries: List<ElementEntry>,
    override val loc: Loc
) : ElementNode
