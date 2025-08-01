package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class AnyContentSpecNode(
    override val loc: Loc
) : ContentSpecNode
