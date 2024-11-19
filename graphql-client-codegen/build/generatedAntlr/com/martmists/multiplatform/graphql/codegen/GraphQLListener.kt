// Generated from C:/Users/micha/IdeaProjects/multiplatform-everything/graphql-client-codegen/antlr/GraphQL.g4 by ANTLR 4.13.1
package com.martmists.multiplatform.graphql.codegen

import org.antlr.v4.kotlinruntime.tree.ParseTreeListener

/**
 * This interface defines a complete listener for a parse tree produced by [GraphQLParser].
 */
public interface GraphQLListener : ParseTreeListener {
    /**
     * Enter a parse tree produced by [GraphQLParser.graphqlSchema].
     *
     * @param ctx The parse tree
     */
    public fun enterGraphqlSchema(ctx: GraphQLParser.GraphqlSchemaContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.graphqlSchema].
     *
     * @param ctx The parse tree
     */
    public fun exitGraphqlSchema(ctx: GraphQLParser.GraphqlSchemaContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.description].
     *
     * @param ctx The parse tree
     */
    public fun enterDescription(ctx: GraphQLParser.DescriptionContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.description].
     *
     * @param ctx The parse tree
     */
    public fun exitDescription(ctx: GraphQLParser.DescriptionContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.schemaDef].
     *
     * @param ctx The parse tree
     */
    public fun enterSchemaDef(ctx: GraphQLParser.SchemaDefContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.schemaDef].
     *
     * @param ctx The parse tree
     */
    public fun exitSchemaDef(ctx: GraphQLParser.SchemaDefContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.operationTypeDef].
     *
     * @param ctx The parse tree
     */
    public fun enterOperationTypeDef(ctx: GraphQLParser.OperationTypeDefContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.operationTypeDef].
     *
     * @param ctx The parse tree
     */
    public fun exitOperationTypeDef(ctx: GraphQLParser.OperationTypeDefContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.queryOperationDef].
     *
     * @param ctx The parse tree
     */
    public fun enterQueryOperationDef(ctx: GraphQLParser.QueryOperationDefContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.queryOperationDef].
     *
     * @param ctx The parse tree
     */
    public fun exitQueryOperationDef(ctx: GraphQLParser.QueryOperationDefContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.mutationOperationDef].
     *
     * @param ctx The parse tree
     */
    public fun enterMutationOperationDef(ctx: GraphQLParser.MutationOperationDefContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.mutationOperationDef].
     *
     * @param ctx The parse tree
     */
    public fun exitMutationOperationDef(ctx: GraphQLParser.MutationOperationDefContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.subscriptionOperationDef].
     *
     * @param ctx The parse tree
     */
    public fun enterSubscriptionOperationDef(ctx: GraphQLParser.SubscriptionOperationDefContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.subscriptionOperationDef].
     *
     * @param ctx The parse tree
     */
    public fun exitSubscriptionOperationDef(ctx: GraphQLParser.SubscriptionOperationDefContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.directiveLocationList].
     *
     * @param ctx The parse tree
     */
    public fun enterDirectiveLocationList(ctx: GraphQLParser.DirectiveLocationListContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.directiveLocationList].
     *
     * @param ctx The parse tree
     */
    public fun exitDirectiveLocationList(ctx: GraphQLParser.DirectiveLocationListContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.directiveLocation].
     *
     * @param ctx The parse tree
     */
    public fun enterDirectiveLocation(ctx: GraphQLParser.DirectiveLocationContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.directiveLocation].
     *
     * @param ctx The parse tree
     */
    public fun exitDirectiveLocation(ctx: GraphQLParser.DirectiveLocationContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.executableDirectiveLocation].
     *
     * @param ctx The parse tree
     */
    public fun enterExecutableDirectiveLocation(ctx: GraphQLParser.ExecutableDirectiveLocationContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.executableDirectiveLocation].
     *
     * @param ctx The parse tree
     */
    public fun exitExecutableDirectiveLocation(ctx: GraphQLParser.ExecutableDirectiveLocationContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.typeSystemDirectiveLocation].
     *
     * @param ctx The parse tree
     */
    public fun enterTypeSystemDirectiveLocation(ctx: GraphQLParser.TypeSystemDirectiveLocationContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.typeSystemDirectiveLocation].
     *
     * @param ctx The parse tree
     */
    public fun exitTypeSystemDirectiveLocation(ctx: GraphQLParser.TypeSystemDirectiveLocationContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.directiveDef].
     *
     * @param ctx The parse tree
     */
    public fun enterDirectiveDef(ctx: GraphQLParser.DirectiveDefContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.directiveDef].
     *
     * @param ctx The parse tree
     */
    public fun exitDirectiveDef(ctx: GraphQLParser.DirectiveDefContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.directiveList].
     *
     * @param ctx The parse tree
     */
    public fun enterDirectiveList(ctx: GraphQLParser.DirectiveListContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.directiveList].
     *
     * @param ctx The parse tree
     */
    public fun exitDirectiveList(ctx: GraphQLParser.DirectiveListContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.directive].
     *
     * @param ctx The parse tree
     */
    public fun enterDirective(ctx: GraphQLParser.DirectiveContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.directive].
     *
     * @param ctx The parse tree
     */
    public fun exitDirective(ctx: GraphQLParser.DirectiveContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.directiveArgList].
     *
     * @param ctx The parse tree
     */
    public fun enterDirectiveArgList(ctx: GraphQLParser.DirectiveArgListContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.directiveArgList].
     *
     * @param ctx The parse tree
     */
    public fun exitDirectiveArgList(ctx: GraphQLParser.DirectiveArgListContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.directiveArg].
     *
     * @param ctx The parse tree
     */
    public fun enterDirectiveArg(ctx: GraphQLParser.DirectiveArgContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.directiveArg].
     *
     * @param ctx The parse tree
     */
    public fun exitDirectiveArg(ctx: GraphQLParser.DirectiveArgContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.typeDef].
     *
     * @param ctx The parse tree
     */
    public fun enterTypeDef(ctx: GraphQLParser.TypeDefContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.typeDef].
     *
     * @param ctx The parse tree
     */
    public fun exitTypeDef(ctx: GraphQLParser.TypeDefContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.typeExtDef].
     *
     * @param ctx The parse tree
     */
    public fun enterTypeExtDef(ctx: GraphQLParser.TypeExtDefContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.typeExtDef].
     *
     * @param ctx The parse tree
     */
    public fun exitTypeExtDef(ctx: GraphQLParser.TypeExtDefContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.fieldDefs].
     *
     * @param ctx The parse tree
     */
    public fun enterFieldDefs(ctx: GraphQLParser.FieldDefsContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.fieldDefs].
     *
     * @param ctx The parse tree
     */
    public fun exitFieldDefs(ctx: GraphQLParser.FieldDefsContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.implementationDef].
     *
     * @param ctx The parse tree
     */
    public fun enterImplementationDef(ctx: GraphQLParser.ImplementationDefContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.implementationDef].
     *
     * @param ctx The parse tree
     */
    public fun exitImplementationDef(ctx: GraphQLParser.ImplementationDefContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.inputTypeDef].
     *
     * @param ctx The parse tree
     */
    public fun enterInputTypeDef(ctx: GraphQLParser.InputTypeDefContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.inputTypeDef].
     *
     * @param ctx The parse tree
     */
    public fun exitInputTypeDef(ctx: GraphQLParser.InputTypeDefContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.inputTypeExtDef].
     *
     * @param ctx The parse tree
     */
    public fun enterInputTypeExtDef(ctx: GraphQLParser.InputTypeExtDefContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.inputTypeExtDef].
     *
     * @param ctx The parse tree
     */
    public fun exitInputTypeExtDef(ctx: GraphQLParser.InputTypeExtDefContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.inputValueDefs].
     *
     * @param ctx The parse tree
     */
    public fun enterInputValueDefs(ctx: GraphQLParser.InputValueDefsContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.inputValueDefs].
     *
     * @param ctx The parse tree
     */
    public fun exitInputValueDefs(ctx: GraphQLParser.InputValueDefsContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.inputValueDef].
     *
     * @param ctx The parse tree
     */
    public fun enterInputValueDef(ctx: GraphQLParser.InputValueDefContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.inputValueDef].
     *
     * @param ctx The parse tree
     */
    public fun exitInputValueDef(ctx: GraphQLParser.InputValueDefContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.interfaceDef].
     *
     * @param ctx The parse tree
     */
    public fun enterInterfaceDef(ctx: GraphQLParser.InterfaceDefContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.interfaceDef].
     *
     * @param ctx The parse tree
     */
    public fun exitInterfaceDef(ctx: GraphQLParser.InterfaceDefContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.scalarDef].
     *
     * @param ctx The parse tree
     */
    public fun enterScalarDef(ctx: GraphQLParser.ScalarDefContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.scalarDef].
     *
     * @param ctx The parse tree
     */
    public fun exitScalarDef(ctx: GraphQLParser.ScalarDefContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.unionDef].
     *
     * @param ctx The parse tree
     */
    public fun enterUnionDef(ctx: GraphQLParser.UnionDefContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.unionDef].
     *
     * @param ctx The parse tree
     */
    public fun exitUnionDef(ctx: GraphQLParser.UnionDefContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.unionExtDef].
     *
     * @param ctx The parse tree
     */
    public fun enterUnionExtDef(ctx: GraphQLParser.UnionExtDefContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.unionExtDef].
     *
     * @param ctx The parse tree
     */
    public fun exitUnionExtDef(ctx: GraphQLParser.UnionExtDefContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.unionTypes].
     *
     * @param ctx The parse tree
     */
    public fun enterUnionTypes(ctx: GraphQLParser.UnionTypesContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.unionTypes].
     *
     * @param ctx The parse tree
     */
    public fun exitUnionTypes(ctx: GraphQLParser.UnionTypesContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.enumDef].
     *
     * @param ctx The parse tree
     */
    public fun enterEnumDef(ctx: GraphQLParser.EnumDefContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.enumDef].
     *
     * @param ctx The parse tree
     */
    public fun exitEnumDef(ctx: GraphQLParser.EnumDefContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.enumValueDefs].
     *
     * @param ctx The parse tree
     */
    public fun enterEnumValueDefs(ctx: GraphQLParser.EnumValueDefsContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.enumValueDefs].
     *
     * @param ctx The parse tree
     */
    public fun exitEnumValueDefs(ctx: GraphQLParser.EnumValueDefsContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.enumValueDef].
     *
     * @param ctx The parse tree
     */
    public fun enterEnumValueDef(ctx: GraphQLParser.EnumValueDefContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.enumValueDef].
     *
     * @param ctx The parse tree
     */
    public fun exitEnumValueDef(ctx: GraphQLParser.EnumValueDefContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.fieldDef].
     *
     * @param ctx The parse tree
     */
    public fun enterFieldDef(ctx: GraphQLParser.FieldDefContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.fieldDef].
     *
     * @param ctx The parse tree
     */
    public fun exitFieldDef(ctx: GraphQLParser.FieldDefContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.argList].
     *
     * @param ctx The parse tree
     */
    public fun enterArgList(ctx: GraphQLParser.ArgListContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.argList].
     *
     * @param ctx The parse tree
     */
    public fun exitArgList(ctx: GraphQLParser.ArgListContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.argument].
     *
     * @param ctx The parse tree
     */
    public fun enterArgument(ctx: GraphQLParser.ArgumentContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.argument].
     *
     * @param ctx The parse tree
     */
    public fun exitArgument(ctx: GraphQLParser.ArgumentContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.typeSpec].
     *
     * @param ctx The parse tree
     */
    public fun enterTypeSpec(ctx: GraphQLParser.TypeSpecContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.typeSpec].
     *
     * @param ctx The parse tree
     */
    public fun exitTypeSpec(ctx: GraphQLParser.TypeSpecContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.typeName].
     *
     * @param ctx The parse tree
     */
    public fun enterTypeName(ctx: GraphQLParser.TypeNameContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.typeName].
     *
     * @param ctx The parse tree
     */
    public fun exitTypeName(ctx: GraphQLParser.TypeNameContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.listType].
     *
     * @param ctx The parse tree
     */
    public fun enterListType(ctx: GraphQLParser.ListTypeContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.listType].
     *
     * @param ctx The parse tree
     */
    public fun exitListType(ctx: GraphQLParser.ListTypeContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.required].
     *
     * @param ctx The parse tree
     */
    public fun enterRequired(ctx: GraphQLParser.RequiredContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.required].
     *
     * @param ctx The parse tree
     */
    public fun exitRequired(ctx: GraphQLParser.RequiredContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.defaultValue].
     *
     * @param ctx The parse tree
     */
    public fun enterDefaultValue(ctx: GraphQLParser.DefaultValueContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.defaultValue].
     *
     * @param ctx The parse tree
     */
    public fun exitDefaultValue(ctx: GraphQLParser.DefaultValueContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.anyName].
     *
     * @param ctx The parse tree
     */
    public fun enterAnyName(ctx: GraphQLParser.AnyNameContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.anyName].
     *
     * @param ctx The parse tree
     */
    public fun exitAnyName(ctx: GraphQLParser.AnyNameContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.nameTokens].
     *
     * @param ctx The parse tree
     */
    public fun enterNameTokens(ctx: GraphQLParser.NameTokensContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.nameTokens].
     *
     * @param ctx The parse tree
     */
    public fun exitNameTokens(ctx: GraphQLParser.NameTokensContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.booleanValue].
     *
     * @param ctx The parse tree
     */
    public fun enterBooleanValue(ctx: GraphQLParser.BooleanValueContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.booleanValue].
     *
     * @param ctx The parse tree
     */
    public fun exitBooleanValue(ctx: GraphQLParser.BooleanValueContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.value].
     *
     * @param ctx The parse tree
     */
    public fun enterValue(ctx: GraphQLParser.ValueContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.value].
     *
     * @param ctx The parse tree
     */
    public fun exitValue(ctx: GraphQLParser.ValueContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.enumValue].
     *
     * @param ctx The parse tree
     */
    public fun enterEnumValue(ctx: GraphQLParser.EnumValueContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.enumValue].
     *
     * @param ctx The parse tree
     */
    public fun exitEnumValue(ctx: GraphQLParser.EnumValueContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.arrayValue].
     *
     * @param ctx The parse tree
     */
    public fun enterArrayValue(ctx: GraphQLParser.ArrayValueContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.arrayValue].
     *
     * @param ctx The parse tree
     */
    public fun exitArrayValue(ctx: GraphQLParser.ArrayValueContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.objectValue].
     *
     * @param ctx The parse tree
     */
    public fun enterObjectValue(ctx: GraphQLParser.ObjectValueContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.objectValue].
     *
     * @param ctx The parse tree
     */
    public fun exitObjectValue(ctx: GraphQLParser.ObjectValueContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.objectField].
     *
     * @param ctx The parse tree
     */
    public fun enterObjectField(ctx: GraphQLParser.ObjectFieldContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.objectField].
     *
     * @param ctx The parse tree
     */
    public fun exitObjectField(ctx: GraphQLParser.ObjectFieldContext)

    /**
     * Enter a parse tree produced by [GraphQLParser.nullValue].
     *
     * @param ctx The parse tree
     */
    public fun enterNullValue(ctx: GraphQLParser.NullValueContext)

    /**
     * Exit a parse tree produced by [GraphQLParser.nullValue].
     *
     * @param ctx The parse tree
     */
    public fun exitNullValue(ctx: GraphQLParser.NullValueContext)

}
