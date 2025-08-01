package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class SeqEntryNode(
    val items: List<CpNode>,
    override val loc: Loc
) : CpEntryNode, ChildrenEntryNode
