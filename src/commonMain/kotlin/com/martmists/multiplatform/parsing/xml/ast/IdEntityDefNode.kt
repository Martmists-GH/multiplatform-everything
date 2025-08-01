package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class IdEntityDefNode(
    val id: ExternalIDNode,
    val ndata: NDataDeclNode?,
    override val loc: Loc
) : EntityDefNode
