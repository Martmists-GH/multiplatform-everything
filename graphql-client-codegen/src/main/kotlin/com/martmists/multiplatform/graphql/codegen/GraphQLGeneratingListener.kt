package com.martmists.multiplatform.graphql.codegen

import java.io.File

class GraphQLGeneratingListener(outputDir: File,
                                packageName: String,
                                @Suppress("unused") private val ruleNames: Array<String> = emptyArray()) : GraphQLBaseListener() {
    private val typeRegistry = GraphQLTypeRegistry()
    private val generator = GraphQLCodeGenerator(outputDir.resolve(packageName.replace('.', '/')), packageName, typeRegistry)

//    override fun enterEveryRule(ctx: ParserRuleContext) {
//        println("${ruleNames[ctx.ruleIndex]}: ${ctx.text}")
//    }

    private fun parseType(type: GraphQLParser.TypeSpecContext): GQLTypeRef {
        val ofType = type.listType()?.typeSpec()?.let(::parseType)
        val name = type.typeName()?.text
        var ref = GQLTypeRef(
            name = name ?: "",
            nullable = false,
            isList = ofType != null,
            ofType = ofType
        )
        if (type.required() == null) {
            ref = GQLTypeRef(
                name = "",
                nullable = true,
                isList = false,
                ofType = ref
            )
        }
        return ref
    }

    override fun exitTypeDef(ctx: GraphQLParser.TypeDefContext) {
        val typeName = ctx.anyName().text
        val fields = ctx.fieldDefs()!!.fieldDef()
        val type = GQLType(
            kind = TypeKind.OBJECT,
            name = typeName,
            properties = fields.filter { it.argList()?.isEmpty != false }.associate { it.anyName().nameTokens()!!.text to parseType(it.typeSpec()) },
            methods = fields.filter { it.argList()?.isEmpty == false }.associate {
                it.anyName().nameTokens()!!.text to GQLMethod(it.anyName().nameTokens()!!.text, parseType(it.typeSpec()), it.argList()!!.argument().associate { arg -> arg.anyName().nameTokens()!!.text to parseType(arg.typeSpec()) })
            },
            interfaces = ctx.implementationDef()?.Name()?.map { GQLTypeRef(it.text) } ?: emptyList(),
        )
        typeRegistry.add(type)
    }

    override fun exitEnumDef(ctx: GraphQLParser.EnumDefContext) {
        val enumName = ctx.anyName().text
        val enumEntries = ctx.enumValueDefs().enumValueDef().map { it.nameTokens().text }
        typeRegistry.addEnum(enumName, enumEntries)
    }

    override fun exitInterfaceDef(ctx: GraphQLParser.InterfaceDefContext) {
        val itfName = ctx.anyName().nameTokens()!!.Name()!!.text
        val fields = ctx.fieldDefs()?.fieldDef() ?: emptyList()
        val type = GQLType(
            kind = TypeKind.INTERFACE,
            name = itfName,
            properties = fields.filter { it.argList()?.isEmpty != false }.associate { it.anyName().nameTokens()!!.text to parseType(it.typeSpec()) },
            methods = fields.filter { it.argList()?.isEmpty == false }.associate {
                it.anyName().nameTokens()!!.text to GQLMethod(it.anyName().nameTokens()!!.text, parseType(it.typeSpec()), it.argList()!!.argument().associate { arg -> arg.anyName().nameTokens()!!.text to parseType(arg.typeSpec()) })
            },
        )
        typeRegistry.add(type)
    }

    override fun exitScalarDef(ctx: GraphQLParser.ScalarDefContext) {
        typeRegistry.addScalar(ctx.anyName().nameTokens()!!.text)
    }

    override fun exitGraphqlSchema(ctx: GraphQLParser.GraphqlSchemaContext) {
        generator.emitCode()
    }
}
