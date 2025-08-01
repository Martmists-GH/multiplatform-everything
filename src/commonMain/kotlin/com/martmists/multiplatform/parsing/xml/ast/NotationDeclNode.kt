package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class NotationDeclNode(
    val name: String,
    val id: NotationIDNode,
    override val loc: Loc
) : IntSubsetNode
