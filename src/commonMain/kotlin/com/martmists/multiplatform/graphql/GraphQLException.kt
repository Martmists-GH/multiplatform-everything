package com.martmists.multiplatform.graphql

import com.martmists.multiplatform.parsing.core.Loc

class GraphQLException(val errors: List<GraphQLError>) : Exception() {
    constructor(error: GraphQLError) : this(listOf(error))
    constructor(message: String, loc: Loc? = null, path: List<Any> = emptyList()) : this(GraphQLError(message, loc, path))
    constructor(message: String, loc: Loc? = null, path: String) : this(message, loc, listOf(path))
    constructor(message: String, loc: Loc? = null, path: Int) : this(message, loc, listOf(path))

    data class GraphQLError(val message: String, val loc: Loc? = null, val path: List<Any> = emptyList()) {
        fun withParent(prefix: String) = copy(path = listOf(prefix) + path)
        fun withParent(prefix: Int) = copy(path = listOf(prefix) + path)
    }
}
