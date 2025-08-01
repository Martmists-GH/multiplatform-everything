package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class AttListDeclNode(
    val name: String,
    val attdefs: List<AttDefNode>,
    override val loc: Loc
) : IntSubsetNode
