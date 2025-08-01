package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class PublicIDNode(
    val pubid: String,
    override val loc: Loc
) : NotationIDNode
