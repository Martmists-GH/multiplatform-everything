package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class GEDeclNode(
    val name: String,
    val def: EntityDefNode,
    override val loc: Loc
) : EntityDeclNode
