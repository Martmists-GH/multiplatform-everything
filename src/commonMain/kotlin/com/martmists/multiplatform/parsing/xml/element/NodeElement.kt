package com.martmists.multiplatform.parsing.xml.element

data class NodeElement(
    val tag: String,
    val attributes: Map<String, String>,
    val children: List<Element>
) : Element
