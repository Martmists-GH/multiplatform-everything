package com.martmists.multiplatform.graphql.codegen;
// Generated from GraphQL.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GraphQLParser}.
 */
public interface GraphQLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#graphqlSchema}.
	 * @param ctx the parse tree
	 */
	void enterGraphqlSchema(GraphQLParser.GraphqlSchemaContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#graphqlSchema}.
	 * @param ctx the parse tree
	 */
	void exitGraphqlSchema(GraphQLParser.GraphqlSchemaContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#description}.
	 * @param ctx the parse tree
	 */
	void enterDescription(GraphQLParser.DescriptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#description}.
	 * @param ctx the parse tree
	 */
	void exitDescription(GraphQLParser.DescriptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#schemaDef}.
	 * @param ctx the parse tree
	 */
	void enterSchemaDef(GraphQLParser.SchemaDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#schemaDef}.
	 * @param ctx the parse tree
	 */
	void exitSchemaDef(GraphQLParser.SchemaDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#operationTypeDef}.
	 * @param ctx the parse tree
	 */
	void enterOperationTypeDef(GraphQLParser.OperationTypeDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#operationTypeDef}.
	 * @param ctx the parse tree
	 */
	void exitOperationTypeDef(GraphQLParser.OperationTypeDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#queryOperationDef}.
	 * @param ctx the parse tree
	 */
	void enterQueryOperationDef(GraphQLParser.QueryOperationDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#queryOperationDef}.
	 * @param ctx the parse tree
	 */
	void exitQueryOperationDef(GraphQLParser.QueryOperationDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#mutationOperationDef}.
	 * @param ctx the parse tree
	 */
	void enterMutationOperationDef(GraphQLParser.MutationOperationDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#mutationOperationDef}.
	 * @param ctx the parse tree
	 */
	void exitMutationOperationDef(GraphQLParser.MutationOperationDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#subscriptionOperationDef}.
	 * @param ctx the parse tree
	 */
	void enterSubscriptionOperationDef(GraphQLParser.SubscriptionOperationDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#subscriptionOperationDef}.
	 * @param ctx the parse tree
	 */
	void exitSubscriptionOperationDef(GraphQLParser.SubscriptionOperationDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#directiveLocationList}.
	 * @param ctx the parse tree
	 */
	void enterDirectiveLocationList(GraphQLParser.DirectiveLocationListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#directiveLocationList}.
	 * @param ctx the parse tree
	 */
	void exitDirectiveLocationList(GraphQLParser.DirectiveLocationListContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#directiveLocation}.
	 * @param ctx the parse tree
	 */
	void enterDirectiveLocation(GraphQLParser.DirectiveLocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#directiveLocation}.
	 * @param ctx the parse tree
	 */
	void exitDirectiveLocation(GraphQLParser.DirectiveLocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#executableDirectiveLocation}.
	 * @param ctx the parse tree
	 */
	void enterExecutableDirectiveLocation(GraphQLParser.ExecutableDirectiveLocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#executableDirectiveLocation}.
	 * @param ctx the parse tree
	 */
	void exitExecutableDirectiveLocation(GraphQLParser.ExecutableDirectiveLocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#typeSystemDirectiveLocation}.
	 * @param ctx the parse tree
	 */
	void enterTypeSystemDirectiveLocation(GraphQLParser.TypeSystemDirectiveLocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#typeSystemDirectiveLocation}.
	 * @param ctx the parse tree
	 */
	void exitTypeSystemDirectiveLocation(GraphQLParser.TypeSystemDirectiveLocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#directiveDef}.
	 * @param ctx the parse tree
	 */
	void enterDirectiveDef(GraphQLParser.DirectiveDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#directiveDef}.
	 * @param ctx the parse tree
	 */
	void exitDirectiveDef(GraphQLParser.DirectiveDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#directiveList}.
	 * @param ctx the parse tree
	 */
	void enterDirectiveList(GraphQLParser.DirectiveListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#directiveList}.
	 * @param ctx the parse tree
	 */
	void exitDirectiveList(GraphQLParser.DirectiveListContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#directive}.
	 * @param ctx the parse tree
	 */
	void enterDirective(GraphQLParser.DirectiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#directive}.
	 * @param ctx the parse tree
	 */
	void exitDirective(GraphQLParser.DirectiveContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#directiveArgList}.
	 * @param ctx the parse tree
	 */
	void enterDirectiveArgList(GraphQLParser.DirectiveArgListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#directiveArgList}.
	 * @param ctx the parse tree
	 */
	void exitDirectiveArgList(GraphQLParser.DirectiveArgListContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#directiveArg}.
	 * @param ctx the parse tree
	 */
	void enterDirectiveArg(GraphQLParser.DirectiveArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#directiveArg}.
	 * @param ctx the parse tree
	 */
	void exitDirectiveArg(GraphQLParser.DirectiveArgContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#typeDef}.
	 * @param ctx the parse tree
	 */
	void enterTypeDef(GraphQLParser.TypeDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#typeDef}.
	 * @param ctx the parse tree
	 */
	void exitTypeDef(GraphQLParser.TypeDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#typeExtDef}.
	 * @param ctx the parse tree
	 */
	void enterTypeExtDef(GraphQLParser.TypeExtDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#typeExtDef}.
	 * @param ctx the parse tree
	 */
	void exitTypeExtDef(GraphQLParser.TypeExtDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#fieldDefs}.
	 * @param ctx the parse tree
	 */
	void enterFieldDefs(GraphQLParser.FieldDefsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#fieldDefs}.
	 * @param ctx the parse tree
	 */
	void exitFieldDefs(GraphQLParser.FieldDefsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#implementationDef}.
	 * @param ctx the parse tree
	 */
	void enterImplementationDef(GraphQLParser.ImplementationDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#implementationDef}.
	 * @param ctx the parse tree
	 */
	void exitImplementationDef(GraphQLParser.ImplementationDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#inputTypeDef}.
	 * @param ctx the parse tree
	 */
	void enterInputTypeDef(GraphQLParser.InputTypeDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#inputTypeDef}.
	 * @param ctx the parse tree
	 */
	void exitInputTypeDef(GraphQLParser.InputTypeDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#inputTypeExtDef}.
	 * @param ctx the parse tree
	 */
	void enterInputTypeExtDef(GraphQLParser.InputTypeExtDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#inputTypeExtDef}.
	 * @param ctx the parse tree
	 */
	void exitInputTypeExtDef(GraphQLParser.InputTypeExtDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#inputValueDefs}.
	 * @param ctx the parse tree
	 */
	void enterInputValueDefs(GraphQLParser.InputValueDefsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#inputValueDefs}.
	 * @param ctx the parse tree
	 */
	void exitInputValueDefs(GraphQLParser.InputValueDefsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#inputValueDef}.
	 * @param ctx the parse tree
	 */
	void enterInputValueDef(GraphQLParser.InputValueDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#inputValueDef}.
	 * @param ctx the parse tree
	 */
	void exitInputValueDef(GraphQLParser.InputValueDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#interfaceDef}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceDef(GraphQLParser.InterfaceDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#interfaceDef}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceDef(GraphQLParser.InterfaceDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#scalarDef}.
	 * @param ctx the parse tree
	 */
	void enterScalarDef(GraphQLParser.ScalarDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#scalarDef}.
	 * @param ctx the parse tree
	 */
	void exitScalarDef(GraphQLParser.ScalarDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#unionDef}.
	 * @param ctx the parse tree
	 */
	void enterUnionDef(GraphQLParser.UnionDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#unionDef}.
	 * @param ctx the parse tree
	 */
	void exitUnionDef(GraphQLParser.UnionDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#unionExtDef}.
	 * @param ctx the parse tree
	 */
	void enterUnionExtDef(GraphQLParser.UnionExtDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#unionExtDef}.
	 * @param ctx the parse tree
	 */
	void exitUnionExtDef(GraphQLParser.UnionExtDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#unionTypes}.
	 * @param ctx the parse tree
	 */
	void enterUnionTypes(GraphQLParser.UnionTypesContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#unionTypes}.
	 * @param ctx the parse tree
	 */
	void exitUnionTypes(GraphQLParser.UnionTypesContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#enumDef}.
	 * @param ctx the parse tree
	 */
	void enterEnumDef(GraphQLParser.EnumDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#enumDef}.
	 * @param ctx the parse tree
	 */
	void exitEnumDef(GraphQLParser.EnumDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#enumValueDefs}.
	 * @param ctx the parse tree
	 */
	void enterEnumValueDefs(GraphQLParser.EnumValueDefsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#enumValueDefs}.
	 * @param ctx the parse tree
	 */
	void exitEnumValueDefs(GraphQLParser.EnumValueDefsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#enumValueDef}.
	 * @param ctx the parse tree
	 */
	void enterEnumValueDef(GraphQLParser.EnumValueDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#enumValueDef}.
	 * @param ctx the parse tree
	 */
	void exitEnumValueDef(GraphQLParser.EnumValueDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#fieldDef}.
	 * @param ctx the parse tree
	 */
	void enterFieldDef(GraphQLParser.FieldDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#fieldDef}.
	 * @param ctx the parse tree
	 */
	void exitFieldDef(GraphQLParser.FieldDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#argList}.
	 * @param ctx the parse tree
	 */
	void enterArgList(GraphQLParser.ArgListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#argList}.
	 * @param ctx the parse tree
	 */
	void exitArgList(GraphQLParser.ArgListContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#argument}.
	 * @param ctx the parse tree
	 */
	void enterArgument(GraphQLParser.ArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#argument}.
	 * @param ctx the parse tree
	 */
	void exitArgument(GraphQLParser.ArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#typeSpec}.
	 * @param ctx the parse tree
	 */
	void enterTypeSpec(GraphQLParser.TypeSpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#typeSpec}.
	 * @param ctx the parse tree
	 */
	void exitTypeSpec(GraphQLParser.TypeSpecContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#typeName}.
	 * @param ctx the parse tree
	 */
	void enterTypeName(GraphQLParser.TypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#typeName}.
	 * @param ctx the parse tree
	 */
	void exitTypeName(GraphQLParser.TypeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#listType}.
	 * @param ctx the parse tree
	 */
	void enterListType(GraphQLParser.ListTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#listType}.
	 * @param ctx the parse tree
	 */
	void exitListType(GraphQLParser.ListTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#required}.
	 * @param ctx the parse tree
	 */
	void enterRequired(GraphQLParser.RequiredContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#required}.
	 * @param ctx the parse tree
	 */
	void exitRequired(GraphQLParser.RequiredContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#defaultValue}.
	 * @param ctx the parse tree
	 */
	void enterDefaultValue(GraphQLParser.DefaultValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#defaultValue}.
	 * @param ctx the parse tree
	 */
	void exitDefaultValue(GraphQLParser.DefaultValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#anyName}.
	 * @param ctx the parse tree
	 */
	void enterAnyName(GraphQLParser.AnyNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#anyName}.
	 * @param ctx the parse tree
	 */
	void exitAnyName(GraphQLParser.AnyNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#nameTokens}.
	 * @param ctx the parse tree
	 */
	void enterNameTokens(GraphQLParser.NameTokensContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#nameTokens}.
	 * @param ctx the parse tree
	 */
	void exitNameTokens(GraphQLParser.NameTokensContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#booleanValue}.
	 * @param ctx the parse tree
	 */
	void enterBooleanValue(GraphQLParser.BooleanValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#booleanValue}.
	 * @param ctx the parse tree
	 */
	void exitBooleanValue(GraphQLParser.BooleanValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(GraphQLParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(GraphQLParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#enumValue}.
	 * @param ctx the parse tree
	 */
	void enterEnumValue(GraphQLParser.EnumValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#enumValue}.
	 * @param ctx the parse tree
	 */
	void exitEnumValue(GraphQLParser.EnumValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#arrayValue}.
	 * @param ctx the parse tree
	 */
	void enterArrayValue(GraphQLParser.ArrayValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#arrayValue}.
	 * @param ctx the parse tree
	 */
	void exitArrayValue(GraphQLParser.ArrayValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#objectValue}.
	 * @param ctx the parse tree
	 */
	void enterObjectValue(GraphQLParser.ObjectValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#objectValue}.
	 * @param ctx the parse tree
	 */
	void exitObjectValue(GraphQLParser.ObjectValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#objectField}.
	 * @param ctx the parse tree
	 */
	void enterObjectField(GraphQLParser.ObjectFieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#objectField}.
	 * @param ctx the parse tree
	 */
	void exitObjectField(GraphQLParser.ObjectFieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphQLParser#nullValue}.
	 * @param ctx the parse tree
	 */
	void enterNullValue(GraphQLParser.NullValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphQLParser#nullValue}.
	 * @param ctx the parse tree
	 */
	void exitNullValue(GraphQLParser.NullValueContext ctx);
}
