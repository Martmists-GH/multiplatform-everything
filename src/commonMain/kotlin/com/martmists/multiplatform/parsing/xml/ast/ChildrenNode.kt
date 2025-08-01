package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class ChildrenNode(
    val node: ChildrenEntryNode,
    val modifier: Char?,
    override val loc: Loc
) : ContentSpecNode
