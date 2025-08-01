package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class SNode(
    val content: String,
    override val loc: Loc
) : MiscNode
