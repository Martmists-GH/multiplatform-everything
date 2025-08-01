package com.martmists.multiplatform.parsing.xml.ast

sealed interface ExternalIDNode : NotationIDNode, PEDefNode {
    val system: String
}
