package com.martmists.multiplatform.parsing.xml.ast

import com.martmists.multiplatform.parsing.core.Loc

data class PublicExternalIDNode(
    val public: String,
    override val system: String,
    override val loc: Loc
) : ExternalIDNode
