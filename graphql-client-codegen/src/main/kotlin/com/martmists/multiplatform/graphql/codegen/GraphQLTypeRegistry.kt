package com.martmists.multiplatform.graphql.codegen

class GraphQLTypeRegistry {
    val types = mutableMapOf<String, GQLType>()

    init {
        addScalar("Int")
        addScalar("Long")
        addScalar("Float")
        addScalar("Double")
        addScalar("Boolean")
        addScalar("String")
        addScalar("ID")
    }

    fun addScalar(name: String) {
        types[name] = GQLType(TypeKind.SCALAR, name)
    }

    fun addEnum(name: String, values: List<String>) {
        types[name] = GQLType(TypeKind.ENUM, name, enumValues = values)
    }

    fun add(type: GQLType) {
        require(type.name.isNotBlank())
        types[type.name] = type
    }

    fun resolve(ref: GQLTypeRef): GQLType {
        return when {
            ref.isList -> GQLType(
                kind = TypeKind.LIST,
                name = "",
                ofType = ref.ofType?.let(::resolve)
            )
            ref.nullable -> GQLType(
                kind = TypeKind.NULLABLE,
                name = "",
                ofType = ref.ofType?.let(::resolve)
            )
            else -> types[ref.name]!!
        }
    }

    fun implementors(typeName: String): List<GQLType> {
        if (types[typeName]!!.kind != TypeKind.INTERFACE) return emptyList()
        return types.values.filter { typ -> typ.interfaces.any { itf -> itf.name == typeName } }
    }

    fun resolveInner(type: GQLType): GQLType {
        var t = type
        while (t.ofType != null) {
            t = t.ofType!!
        }
        return t
    }
}
