package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class DocumentNode(
    val prolog: PrologNode,
    val element: ElementNode,
    val misc: List<MiscNode>,
    override val loc: Loc
) : AstNode

