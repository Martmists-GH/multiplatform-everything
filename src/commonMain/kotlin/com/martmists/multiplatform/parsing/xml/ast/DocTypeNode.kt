package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class DocTypeNode(
    val name: String,
    val externalId: ExternalIDNode?,
    val subset: List<IntSubsetNode>,
    override val loc: Loc
) : AstNode
