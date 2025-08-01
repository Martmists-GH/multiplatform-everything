package com.martmists.multiplatform.parsing.xml.ast

sealed interface ElementNode : AstNode, ElementEntry {
    val tag: String
    val attributes: List<AttributeNode>
}
