package com.martmists.multiplatform.graphql

import com.martmists.multiplatform.validation.lexer.Loc

class GraphQLException(val errors: List<GraphQLError>) : Exception() {
    constructor(error: GraphQLError) : this(listOf(error))
    constructor(message: String, loc: Loc? = null) : this(GraphQLError(message, loc))

    class GraphQLError(val message: String, val loc: Loc? = null)
}
