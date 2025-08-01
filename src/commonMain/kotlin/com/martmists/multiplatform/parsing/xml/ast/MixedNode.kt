package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class MixedNode(
    val names: List<String>,
    override val loc: Loc
) : ContentSpecNode
