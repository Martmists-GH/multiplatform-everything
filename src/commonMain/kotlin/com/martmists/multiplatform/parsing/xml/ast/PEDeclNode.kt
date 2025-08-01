package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class PEDeclNode(
    val name: String,
    val def: PEDefNode,
    override val loc: Loc
) : EntityDeclNode
