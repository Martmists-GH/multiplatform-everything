package com.martmists.multiplatform.graphql.parser.ast

import com.martmists.multiplatform.parsing.core.Loc

data class OperationDefinition(
    val type: OperationType,
    val name: String?,
    val variableDefinitions: List<VariableDefinition>,
    val directives: List<Directive>,
    val selectionSet: List<Selection>,
    override val loc: Loc
) : ExecutableDefinition
