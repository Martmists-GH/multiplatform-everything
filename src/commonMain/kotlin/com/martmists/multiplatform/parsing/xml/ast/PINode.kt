package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class PINode(
    val target: String,
    val content: String?,
    override val loc: Loc
) : MiscNode, IntSubsetNode, ElementEntry
