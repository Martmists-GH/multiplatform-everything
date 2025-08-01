package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class CharDataNode(
    val contents: String,
    override val loc: Loc
) : ElementEntry
