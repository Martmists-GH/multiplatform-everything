package com.martmists.multiplatform.graphql.parser.ast

import com.martmists.multiplatform.parsing.core.Loc

data class ExecutableDocument(val definitions: List<ExecutableDefinition>,
                              override val loc: Loc
) : AstNode
