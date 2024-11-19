package com.martmists.multiplatform.graphql.codegen

import java.io.File

class GraphQLGeneratingListener(outputDir: File, packageName: String, private val ruleNames: Array<String>) :
    GraphQLBaseListener() {
    private val codeEmitter = GraphQLCodeGenerator(outputDir, packageName)

    private val propertyList = mutableListOf<PropertyDef>()
    private val methodList = mutableListOf<MethodDef>()
    private val enumList = mutableListOf<String>()
    private var hasSubscriptions = false

    override fun exitTypeDef(ctx: GraphQLParser.TypeDefContext) {
        val typeName = ctx.anyName().text
        when {
            typeName.startsWith("__") -> {
                // ignore
            }

            typeName == "Query" -> {
                codeEmitter.emitQuery(propertyList, methodList)
            }

            typeName == "Mutation" -> {
                codeEmitter.emitMutation(propertyList, methodList)
            }

            typeName == "Subscription" -> {
                codeEmitter.emitSubscription(propertyList, methodList)
                hasSubscriptions = true
            }

            else -> {
                codeEmitter.emitType(typeName, null, propertyList, methodList)
            }
        }

        propertyList.clear()
        methodList.clear()
    }

    override fun exitFieldDef(ctx: GraphQLParser.FieldDefContext) {
        val name = ctx.anyName().text
        val type = ctx.typeSpec().text

        if ((ctx.argList()?.text ?: "").isNotBlank()) {
            val args = ctx.argList()!!.argument().map {
                val pName = it.anyName().text
                val pType = it.typeSpec().text
                PropertyDef(pName, parseType(pType))
            }
            methodList.add(MethodDef(name, parseType(type), args))
        } else {
            propertyList.add(PropertyDef(name, parseType(type)))
        }
    }

    override fun exitEnumDef(ctx: GraphQLParser.EnumDefContext) {
        val typeName = ctx.anyName().text
        enumList.add(typeName)
        val items = ctx.enumValueDefs().enumValueDef().map {
            it.text
        }
        codeEmitter.emitEnum(typeName, items)
    }

    private fun parseType(type: String): Type {
        var t = type
        var nullable = true
        if (t.endsWith('!')) {
            t = t.substring(0, t.length - 1)
            nullable = false
        }
        var list = false
        if (t.startsWith('[') && t.endsWith(']')) {
            t = t.substring(1, t.length - 1)
            list = true
        }
        return if (list) {
            return ListType(parseType(t), nullable)
        } else {
            Type(t, t in Type.scalars, nullable, false, t in enumList)
        }
    }

    override fun exitGraphqlSchema(ctx: GraphQLParser.GraphqlSchemaContext) {
        codeEmitter.emitCore(!hasSubscriptions)
    }
}
