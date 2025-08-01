package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class PrologNode(
    val decl: DeclNode?,
    val misc: List<MiscNode>,
    val doctype: DocTypeNode?,
    val docTypeMisc: List<MiscNode>,
    override val loc: Loc
) : AstNode
