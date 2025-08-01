package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class SystemExternalIDNode(
    override val system: String,
    override val loc: Loc
) : ExternalIDNode
