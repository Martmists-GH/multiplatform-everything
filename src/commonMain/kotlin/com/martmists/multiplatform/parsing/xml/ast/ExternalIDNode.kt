package com.martmists.multiplatform.parsing.xml.ast

sealed interface ExternalIDNode : AstNode {
    val system: String
}
