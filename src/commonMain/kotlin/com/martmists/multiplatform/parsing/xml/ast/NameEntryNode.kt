package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class NameEntryNode(
    val name: String,
    override val loc: Loc
) : CpEntryNode
