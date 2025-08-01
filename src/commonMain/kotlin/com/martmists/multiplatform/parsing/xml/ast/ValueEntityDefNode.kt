package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class ValueEntityDefNode(
    val value: EntityValueNode,
    override val loc: Loc
) : EntityDefNode
