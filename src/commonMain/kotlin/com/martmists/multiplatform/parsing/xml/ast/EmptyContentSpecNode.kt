package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class EmptyContentSpecNode(
    override val loc: Loc
) : ContentSpecNode
