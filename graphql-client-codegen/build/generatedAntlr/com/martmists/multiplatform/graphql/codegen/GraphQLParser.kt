// Generated from /home/mart/git/kotlin/multiplatfom-everything/graphql-client-codegen/src/main/antlr/GraphQL.g4 by ANTLR 4.13.1
package com.martmists.multiplatform.graphql.codegen

import com.strumenta.antlrkotlin.runtime.JsName
import org.antlr.v4.kotlinruntime.*
import org.antlr.v4.kotlinruntime.atn.*
import org.antlr.v4.kotlinruntime.atn.ATN.Companion.INVALID_ALT_NUMBER
import org.antlr.v4.kotlinruntime.dfa.*
import org.antlr.v4.kotlinruntime.misc.*
import org.antlr.v4.kotlinruntime.tree.*
import kotlin.jvm.JvmField

@Suppress(
    // This is required as we are using a custom JsName alias that is not recognized by the IDE.
    // No name clashes will happen tho.
    "JS_NAME_CLASH",
    "UNUSED_VARIABLE",
    "ClassName",
    "FunctionName",
    "LocalVariableName",
    "ConstPropertyName",
    "ConvertSecondaryConstructorToPrimary",
    "CanBeVal",
)
public open class GraphQLParser(input: TokenStream) : Parser(input) {
    private companion object {
        init {
            RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.runtimeVersion)
        }

        private const val SERIALIZED_ATN: String =
            "\u0004\u0001\u002c\u01d0\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002\u0008\u0007\u0008\u0002\u0009\u0007\u0009\u0002\u000a\u0007\u000a\u0002\u000b\u0007\u000b\u0002\u000c\u0007\u000c\u0002\u000d\u0007\u000d\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0002\u0020\u0007\u0020\u0002\u0021\u0007\u0021\u0002\u0022\u0007\u0022\u0002\u0023\u0007\u0023\u0002\u0024\u0007\u0024\u0002\u0025\u0007\u0025\u0002\u0026\u0007\u0026\u0002\u0027\u0007\u0027\u0002\u0028\u0007\u0028\u0002\u0029\u0007\u0029\u0002\u002a\u0007\u002a\u0002\u002b\u0007\u002b\u0002\u002c\u0007\u002c\u0002\u002d\u0007\u002d\u0002\u002e\u0007\u002e\u0002\u002f\u0007\u002f\u0002\u0030\u0007\u0030\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000\u006e\u0008\u0000\u000a\u0000\u000c\u0000\u0071\u0009\u0000\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0003\u0002\u0077\u0008\u0002\u0001\u0002\u0001\u0002\u0004\u0002\u007b\u0008\u0002\u000b\u0002\u000c\u0002\u007c\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003\u0084\u0008\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0005\u0007\u0095\u0008\u0007\u000a\u0007\u000c\u0007\u0098\u0009\u0007\u0001\u0007\u0001\u0007\u0001\u0008\u0001\u0008\u0003\u0008\u009e\u0008\u0008\u0001\u0009\u0001\u0009\u0001\u000a\u0001\u000a\u0001\u000b\u0003\u000b\u00a5\u0008\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u00ab\u0008\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000c\u0004\u000c\u00b1\u0008\u000c\u000b\u000c\u000c\u000c\u00b2\u0001\u000d\u0001\u000d\u0001\u000d\u0003\u000d\u00b8\u0008\u000d\u0001\u000e\u0001\u000e\u0004\u000e\u00bc\u0008\u000e\u000b\u000e\u000c\u000e\u00bd\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0003\u0010\u00c7\u0008\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0003\u0010\u00cc\u0008\u0010\u0001\u0010\u0003\u0010\u00cf\u0008\u0010\u0001\u0010\u0003\u0010\u00d2\u0008\u0010\u0001\u0011\u0003\u0011\u00d5\u0008\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0003\u0011\u00db\u0008\u0011\u0001\u0011\u0003\u0011\u00de\u0008\u0011\u0001\u0011\u0003\u0011\u00e1\u0008\u0011\u0001\u0012\u0001\u0012\u0004\u0012\u00e5\u0008\u0012\u000b\u0012\u000c\u0012\u00e6\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0003\u0013\u00ed\u0008\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0005\u0013\u00f2\u0008\u0013\u000a\u0013\u000c\u0013\u00f5\u0009\u0013\u0001\u0014\u0003\u0014\u00f8\u0008\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0003\u0014\u00fd\u0008\u0014\u0001\u0014\u0003\u0014\u0100\u0008\u0014\u0001\u0015\u0003\u0015\u0103\u0008\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0003\u0015\u0109\u0008\u0015\u0001\u0015\u0003\u0015\u010c\u0008\u0015\u0001\u0016\u0001\u0016\u0004\u0016\u0110\u0008\u0016\u000b\u0016\u000c\u0016\u0111\u0001\u0016\u0001\u0016\u0001\u0017\u0003\u0017\u0117\u0008\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0003\u0017\u011d\u0008\u0017\u0001\u0017\u0003\u0017\u0120\u0008\u0017\u0001\u0018\u0003\u0018\u0123\u0008\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0003\u0018\u0128\u0008\u0018\u0001\u0018\u0003\u0018\u012b\u0008\u0018\u0001\u0019\u0003\u0019\u012e\u0008\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0003\u0019\u0133\u0008\u0019\u0001\u001a\u0003\u001a\u0136\u0008\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0003\u001a\u013b\u0008\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001b\u0003\u001b\u0141\u0008\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0003\u001b\u0147\u0008\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001c\u0001\u001c\u0001\u001c\u0005\u001c\u014f\u0008\u001c\u000a\u001c\u000c\u001c\u0152\u0009\u001c\u0001\u001c\u0001\u001c\u0001\u001d\u0003\u001d\u0157\u0008\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0003\u001d\u015c\u0008\u001d\u0001\u001d\u0001\u001d\u0001\u001e\u0001\u001e\u0004\u001e\u0162\u0008\u001e\u000b\u001e\u000c\u001e\u0163\u0001\u001e\u0001\u001e\u0001\u001f\u0003\u001f\u0169\u0008\u001f\u0001\u001f\u0001\u001f\u0003\u001f\u016d\u0008\u001f\u0001\u0020\u0003\u0020\u0170\u0008\u0020\u0001\u0020\u0001\u0020\u0003\u0020\u0174\u0008\u0020\u0001\u0020\u0001\u0020\u0001\u0020\u0003\u0020\u0179\u0008\u0020\u0001\u0021\u0001\u0021\u0004\u0021\u017d\u0008\u0021\u000b\u0021\u000c\u0021\u017e\u0001\u0021\u0001\u0021\u0001\u0022\u0003\u0022\u0184\u0008\u0022\u0001\u0022\u0001\u0022\u0001\u0022\u0001\u0022\u0003\u0022\u018a\u0008\u0022\u0001\u0022\u0003\u0022\u018d\u0008\u0022\u0001\u0023\u0001\u0023\u0003\u0023\u0191\u0008\u0023\u0001\u0023\u0003\u0023\u0194\u0008\u0023\u0001\u0024\u0001\u0024\u0001\u0025\u0001\u0025\u0001\u0025\u0001\u0025\u0001\u0026\u0001\u0026\u0001\u0027\u0001\u0027\u0001\u0027\u0001\u0028\u0001\u0028\u0001\u0028\u0001\u0028\u0003\u0028\u01a5\u0008\u0028\u0001\u0029\u0001\u0029\u0001\u002a\u0001\u002a\u0001\u002b\u0001\u002b\u0001\u002b\u0001\u002b\u0001\u002b\u0001\u002b\u0001\u002b\u0001\u002b\u0001\u002b\u0003\u002b\u01b4\u0008\u002b\u0001\u002c\u0001\u002c\u0001\u002d\u0001\u002d\u0005\u002d\u01ba\u0008\u002d\u000a\u002d\u000c\u002d\u01bd\u0009\u002d\u0001\u002d\u0001\u002d\u0001\u002e\u0001\u002e\u0005\u002e\u01c3\u0008\u002e\u000a\u002e\u000c\u002e\u01c6\u0009\u002e\u0001\u002e\u0001\u002e\u0001\u002f\u0001\u002f\u0001\u002f\u0001\u002f\u0001\u0030\u0001\u0030\u0001\u0030\u0000\u0000\u0031\u0000\u0002\u0004\u0006\u0008\u000a\u000c\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e\u0020\u0022\u0024\u0026\u0028\u002a\u002c\u002e\u0030\u0032\u0034\u0036\u0038\u003a\u003c\u003e\u0040\u0042\u0044\u0046\u0048\u004a\u004c\u004e\u0050\u0052\u0054\u0056\u0058\u005a\u005c\u005e\u0060\u0000\u0003\u0001\u0000\u002a\u002b\u0002\u0000\u000d\u001e\u0022\u0022\u0001\u0000\u001f\u0020\u01ee\u0000\u006f\u0001\u0000\u0000\u0000\u0002\u0072\u0001\u0000\u0000\u0000\u0004\u0074\u0001\u0000\u0000\u0000\u0006\u0083\u0001\u0000\u0000\u0000\u0008\u0085\u0001\u0000\u0000\u0000\u000a\u0089\u0001\u0000\u0000\u0000\u000c\u008d\u0001\u0000\u0000\u0000\u000e\u0096\u0001\u0000\u0000\u0000\u0010\u009d\u0001\u0000\u0000\u0000\u0012\u009f\u0001\u0000\u0000\u0000\u0014\u00a1\u0001\u0000\u0000\u0000\u0016\u00a4\u0001\u0000\u0000\u0000\u0018\u00b0\u0001\u0000\u0000\u0000\u001a\u00b4\u0001\u0000\u0000\u0000\u001c\u00b9\u0001\u0000\u0000\u0000\u001e\u00c1\u0001\u0000\u0000\u0000\u0020\u00c6\u0001\u0000\u0000\u0000\u0022\u00d4\u0001\u0000\u0000\u0000\u0024\u00e2\u0001\u0000\u0000\u0000\u0026\u00ea\u0001\u0000\u0000\u0000\u0028\u00f7\u0001\u0000\u0000\u0000\u002a\u0102\u0001\u0000\u0000\u0000\u002c\u010d\u0001\u0000\u0000\u0000\u002e\u0116\u0001\u0000\u0000\u0000\u0030\u0122\u0001\u0000\u0000\u0000\u0032\u012d\u0001\u0000\u0000\u0000\u0034\u0135\u0001\u0000\u0000\u0000\u0036\u0140\u0001\u0000\u0000\u0000\u0038\u0150\u0001\u0000\u0000\u0000\u003a\u0156\u0001\u0000\u0000\u0000\u003c\u015f\u0001\u0000\u0000\u0000\u003e\u0168\u0001\u0000\u0000\u0000\u0040\u016f\u0001\u0000\u0000\u0000\u0042\u017a\u0001\u0000\u0000\u0000\u0044\u0183\u0001\u0000\u0000\u0000\u0046\u0190\u0001\u0000\u0000\u0000\u0048\u0195\u0001\u0000\u0000\u0000\u004a\u0197\u0001\u0000\u0000\u0000\u004c\u019b\u0001\u0000\u0000\u0000\u004e\u019d\u0001\u0000\u0000\u0000\u0050\u01a4\u0001\u0000\u0000\u0000\u0052\u01a6\u0001\u0000\u0000\u0000\u0054\u01a8\u0001\u0000\u0000\u0000\u0056\u01b3\u0001\u0000\u0000\u0000\u0058\u01b5\u0001\u0000\u0000\u0000\u005a\u01b7\u0001\u0000\u0000\u0000\u005c\u01c0\u0001\u0000\u0000\u0000\u005e\u01c9\u0001\u0000\u0000\u0000\u0060\u01cd\u0001\u0000\u0000\u0000\u0062\u006e\u0003\u0004\u0002\u0000\u0063\u006e\u0003\u0020\u0010\u0000\u0064\u006e\u0003\u0022\u0011\u0000\u0065\u006e\u0003\u0028\u0014\u0000\u0066\u006e\u0003\u002a\u0015\u0000\u0067\u006e\u0003\u0034\u001a\u0000\u0068\u006e\u0003\u0036\u001b\u0000\u0069\u006e\u0003\u003a\u001d\u0000\u006a\u006e\u0003\u0030\u0018\u0000\u006b\u006e\u0003\u0032\u0019\u0000\u006c\u006e\u0003\u0016\u000b\u0000\u006d\u0062\u0001\u0000\u0000\u0000\u006d\u0063\u0001\u0000\u0000\u0000\u006d\u0064\u0001\u0000\u0000\u0000\u006d\u0065\u0001\u0000\u0000\u0000\u006d\u0066\u0001\u0000\u0000\u0000\u006d\u0067\u0001\u0000\u0000\u0000\u006d\u0068\u0001\u0000\u0000\u0000\u006d\u0069\u0001\u0000\u0000\u0000\u006d\u006a\u0001\u0000\u0000\u0000\u006d\u006b\u0001\u0000\u0000\u0000\u006d\u006c\u0001\u0000\u0000\u0000\u006e\u0071\u0001\u0000\u0000\u0000\u006f\u006d\u0001\u0000\u0000\u0000\u006f\u0070\u0001\u0000\u0000\u0000\u0070\u0001\u0001\u0000\u0000\u0000\u0071\u006f\u0001\u0000\u0000\u0000\u0072\u0073\u0007\u0000\u0000\u0000\u0073\u0003\u0001\u0000\u0000\u0000\u0074\u0076\u0005\u0012\u0000\u0000\u0075\u0077\u0003\u0018\u000c\u0000\u0076\u0075\u0001\u0000\u0000\u0000\u0076\u0077\u0001\u0000\u0000\u0000\u0077\u0078\u0001\u0000\u0000\u0000\u0078\u007a\u0005\u0001\u0000\u0000\u0079\u007b\u0003\u0006\u0003\u0000\u007a\u0079\u0001\u0000\u0000\u0000\u007b\u007c\u0001\u0000\u0000\u0000\u007c\u007a\u0001\u0000\u0000\u0000\u007c\u007d\u0001\u0000\u0000\u0000\u007d\u007e\u0001\u0000\u0000\u0000\u007e\u007f\u0005\u0002\u0000\u0000\u007f\u0005\u0001\u0000\u0000\u0000\u0080\u0084\u0003\u0008\u0004\u0000\u0081\u0084\u0003\u000a\u0005\u0000\u0082\u0084\u0003\u000c\u0006\u0000\u0083\u0080\u0001\u0000\u0000\u0000\u0083\u0081\u0001\u0000\u0000\u0000\u0083\u0082\u0001\u0000\u0000\u0000\u0084\u0007\u0001\u0000\u0000\u0000\u0085\u0086\u0005\u001b\u0000\u0000\u0086\u0087\u0005\u0003\u0000\u0000\u0087\u0088\u0003\u0050\u0028\u0000\u0088\u0009\u0001\u0000\u0000\u0000\u0089\u008a\u0005\u001c\u0000\u0000\u008a\u008b\u0005\u0003\u0000\u0000\u008b\u008c\u0003\u0050\u0028\u0000\u008c\u000b\u0001\u0000\u0000\u0000\u008d\u008e\u0005\u001d\u0000\u0000\u008e\u008f\u0005\u0003\u0000\u0000\u008f\u0090\u0003\u0050\u0028\u0000\u0090\u000d\u0001\u0000\u0000\u0000\u0091\u0092\u0003\u0010\u0008\u0000\u0092\u0093\u0005\u0004\u0000\u0000\u0093\u0095\u0001\u0000\u0000\u0000\u0094\u0091\u0001\u0000\u0000\u0000\u0095\u0098\u0001\u0000\u0000\u0000\u0096\u0094\u0001\u0000\u0000\u0000\u0096\u0097\u0001\u0000\u0000\u0000\u0097\u0099\u0001\u0000\u0000\u0000\u0098\u0096\u0001\u0000\u0000\u0000\u0099\u009a\u0003\u0010\u0008\u0000\u009a\u000f\u0001\u0000\u0000\u0000\u009b\u009e\u0003\u0012\u0009\u0000\u009c\u009e\u0003\u0014\u000a\u0000\u009d\u009b\u0001\u0000\u0000\u0000\u009d\u009c\u0001\u0000\u0000\u0000\u009e\u0011\u0001\u0000\u0000\u0000\u009f\u00a0\u0005\u000d\u0000\u0000\u00a0\u0013\u0001\u0000\u0000\u0000\u00a1\u00a2\u0005\u000e\u0000\u0000\u00a2\u0015\u0001\u0000\u0000\u0000\u00a3\u00a5\u0003\u0002\u0001\u0000\u00a4\u00a3\u0001\u0000\u0000\u0000\u00a4\u00a5\u0001\u0000\u0000\u0000\u00a5\u00a6\u0001\u0000\u0000\u0000\u00a6\u00a7\u0005\u0016\u0000\u0000\u00a7\u00a8\u0005\u0005\u0000\u0000\u00a8\u00aa\u0003\u0050\u0028\u0000\u00a9\u00ab\u0003\u0042\u0021\u0000\u00aa\u00a9\u0001\u0000\u0000\u0000\u00aa\u00ab\u0001\u0000\u0000\u0000\u00ab\u00ac\u0001\u0000\u0000\u0000\u00ac\u00ad\u0005\u0019\u0000\u0000\u00ad\u00ae\u0003\u000e\u0007\u0000\u00ae\u0017\u0001\u0000\u0000\u0000\u00af\u00b1\u0003\u001a\u000d\u0000\u00b0\u00af\u0001\u0000\u0000\u0000\u00b1\u00b2\u0001\u0000\u0000\u0000\u00b2\u00b0\u0001\u0000\u0000\u0000\u00b2\u00b3\u0001\u0000\u0000\u0000\u00b3\u0019\u0001\u0000\u0000\u0000\u00b4\u00b5\u0005\u0005\u0000\u0000\u00b5\u00b7\u0003\u0050\u0028\u0000\u00b6\u00b8\u0003\u001c\u000e\u0000\u00b7\u00b6\u0001\u0000\u0000\u0000\u00b7\u00b8\u0001\u0000\u0000\u0000\u00b8\u001b\u0001\u0000\u0000\u0000\u00b9\u00bb\u0005\u0006\u0000\u0000\u00ba\u00bc\u0003\u001e\u000f\u0000\u00bb\u00ba\u0001\u0000\u0000\u0000\u00bc\u00bd\u0001\u0000\u0000\u0000\u00bd\u00bb\u0001\u0000\u0000\u0000\u00bd\u00be\u0001\u0000\u0000\u0000\u00be\u00bf\u0001\u0000\u0000\u0000\u00bf\u00c0\u0005\u0007\u0000\u0000\u00c0\u001d\u0001\u0000\u0000\u0000\u00c1\u00c2\u0003\u0050\u0028\u0000\u00c2\u00c3\u0005\u0003\u0000\u0000\u00c3\u00c4\u0003\u0056\u002b\u0000\u00c4\u001f\u0001\u0000\u0000\u0000\u00c5\u00c7\u0003\u0002\u0001\u0000\u00c6\u00c5\u0001\u0000\u0000\u0000\u00c6\u00c7\u0001\u0000\u0000\u0000\u00c7\u00c8\u0001\u0000\u0000\u0000\u00c8\u00c9\u0005\u000f\u0000\u0000\u00c9\u00cb\u0003\u0050\u0028\u0000\u00ca\u00cc\u0003\u0026\u0013\u0000\u00cb\u00ca\u0001\u0000\u0000\u0000\u00cb\u00cc\u0001\u0000\u0000\u0000\u00cc\u00ce\u0001\u0000\u0000\u0000\u00cd\u00cf\u0003\u0018\u000c\u0000\u00ce\u00cd\u0001\u0000\u0000\u0000\u00ce\u00cf\u0001\u0000\u0000\u0000\u00cf\u00d1\u0001\u0000\u0000\u0000\u00d0\u00d2\u0003\u0024\u0012\u0000\u00d1\u00d0\u0001\u0000\u0000\u0000\u00d1\u00d2\u0001\u0000\u0000\u0000\u00d2\u0021\u0001\u0000\u0000\u0000\u00d3\u00d5\u0003\u0002\u0001\u0000\u00d4\u00d3\u0001\u0000\u0000\u0000\u00d4\u00d5\u0001\u0000\u0000\u0000\u00d5\u00d6\u0001\u0000\u0000\u0000\u00d6\u00d7\u0005\u0017\u0000\u0000\u00d7\u00d8\u0005\u000f\u0000\u0000\u00d8\u00da\u0003\u0050\u0028\u0000\u00d9\u00db\u0003\u0026\u0013\u0000\u00da\u00d9\u0001\u0000\u0000\u0000\u00da\u00db\u0001\u0000\u0000\u0000\u00db\u00dd\u0001\u0000\u0000\u0000\u00dc\u00de\u0003\u0018\u000c\u0000\u00dd\u00dc\u0001\u0000\u0000\u0000\u00dd\u00de\u0001\u0000\u0000\u0000\u00de\u00e0\u0001\u0000\u0000\u0000\u00df\u00e1\u0003\u0024\u0012\u0000\u00e0\u00df\u0001\u0000\u0000\u0000\u00e0\u00e1\u0001\u0000\u0000\u0000\u00e1\u0023\u0001\u0000\u0000\u0000\u00e2\u00e4\u0005\u0001\u0000\u0000\u00e3\u00e5\u0003\u0040\u0020\u0000\u00e4\u00e3\u0001\u0000\u0000\u0000\u00e5\u00e6\u0001\u0000\u0000\u0000\u00e6\u00e4\u0001\u0000\u0000\u0000\u00e6\u00e7\u0001\u0000\u0000\u0000\u00e7\u00e8\u0001\u0000\u0000\u0000\u00e8\u00e9\u0005\u0002\u0000\u0000\u00e9\u0025\u0001\u0000\u0000\u0000\u00ea\u00ec\u0005\u0010\u0000\u0000\u00eb\u00ed\u0005\u0008\u0000\u0000\u00ec\u00eb\u0001\u0000\u0000\u0000\u00ec\u00ed\u0001\u0000\u0000\u0000\u00ed\u00ee\u0001\u0000\u0000\u0000\u00ee\u00f3\u0005\u0022\u0000\u0000\u00ef\u00f0\u0005\u0008\u0000\u0000\u00f0\u00f2\u0005\u0022\u0000\u0000\u00f1\u00ef\u0001\u0000\u0000\u0000\u00f2\u00f5\u0001\u0000\u0000\u0000\u00f3\u00f1\u0001\u0000\u0000\u0000\u00f3\u00f4\u0001\u0000\u0000\u0000\u00f4\u0027\u0001\u0000\u0000\u0000\u00f5\u00f3\u0001\u0000\u0000\u0000\u00f6\u00f8\u0003\u0002\u0001\u0000\u00f7\u00f6\u0001\u0000\u0000\u0000\u00f7\u00f8\u0001\u0000\u0000\u0000\u00f8\u00f9\u0001\u0000\u0000\u0000\u00f9\u00fa\u0005\u0015\u0000\u0000\u00fa\u00fc\u0003\u0050\u0028\u0000\u00fb\u00fd\u0003\u0018\u000c\u0000\u00fc\u00fb\u0001\u0000\u0000\u0000\u00fc\u00fd\u0001\u0000\u0000\u0000\u00fd\u00ff\u0001\u0000\u0000\u0000\u00fe\u0100\u0003\u002c\u0016\u0000\u00ff\u00fe\u0001\u0000\u0000\u0000\u00ff\u0100\u0001\u0000\u0000\u0000\u0100\u0029\u0001\u0000\u0000\u0000\u0101\u0103\u0003\u0002\u0001\u0000\u0102\u0101\u0001\u0000\u0000\u0000\u0102\u0103\u0001\u0000\u0000\u0000\u0103\u0104\u0001\u0000\u0000\u0000\u0104\u0105\u0005\u0017\u0000\u0000\u0105\u0106\u0005\u0015\u0000\u0000\u0106\u0108\u0003\u0050\u0028\u0000\u0107\u0109\u0003\u0018\u000c\u0000\u0108\u0107\u0001\u0000\u0000\u0000\u0108\u0109\u0001\u0000\u0000\u0000\u0109\u010b\u0001\u0000\u0000\u0000\u010a\u010c\u0003\u002c\u0016\u0000\u010b\u010a\u0001\u0000\u0000\u0000\u010b\u010c\u0001\u0000\u0000\u0000\u010c\u002b\u0001\u0000\u0000\u0000\u010d\u010f\u0005\u0001\u0000\u0000\u010e\u0110\u0003\u002e\u0017\u0000\u010f\u010e\u0001\u0000\u0000\u0000\u0110\u0111\u0001\u0000\u0000\u0000\u0111\u010f\u0001\u0000\u0000\u0000\u0111\u0112\u0001\u0000\u0000\u0000\u0112\u0113\u0001\u0000\u0000\u0000\u0113\u0114\u0005\u0002\u0000\u0000\u0114\u002d\u0001\u0000\u0000\u0000\u0115\u0117\u0003\u0002\u0001\u0000\u0116\u0115\u0001\u0000\u0000\u0000\u0116\u0117\u0001\u0000\u0000\u0000\u0117\u0118\u0001\u0000\u0000\u0000\u0118\u0119\u0003\u0050\u0028\u0000\u0119\u011a\u0005\u0003\u0000\u0000\u011a\u011c\u0003\u0046\u0023\u0000\u011b\u011d\u0003\u004e\u0027\u0000\u011c\u011b\u0001\u0000\u0000\u0000\u011c\u011d\u0001\u0000\u0000\u0000\u011d\u011f\u0001\u0000\u0000\u0000\u011e\u0120\u0003\u0018\u000c\u0000\u011f\u011e\u0001\u0000\u0000\u0000\u011f\u0120\u0001\u0000\u0000\u0000\u0120\u002f\u0001\u0000\u0000\u0000\u0121\u0123\u0003\u0002\u0001\u0000\u0122\u0121\u0001\u0000\u0000\u0000\u0122\u0123\u0001\u0000\u0000\u0000\u0123\u0124\u0001\u0000\u0000\u0000\u0124\u0125\u0005\u0011\u0000\u0000\u0125\u0127\u0003\u0050\u0028\u0000\u0126\u0128\u0003\u0018\u000c\u0000\u0127\u0126\u0001\u0000\u0000\u0000\u0127\u0128\u0001\u0000\u0000\u0000\u0128\u012a\u0001\u0000\u0000\u0000\u0129\u012b\u0003\u0024\u0012\u0000\u012a\u0129\u0001\u0000\u0000\u0000\u012a\u012b\u0001\u0000\u0000\u0000\u012b\u0031\u0001\u0000\u0000\u0000\u012c\u012e\u0003\u0002\u0001\u0000\u012d\u012c\u0001\u0000\u0000\u0000\u012d\u012e\u0001\u0000\u0000\u0000\u012e\u012f\u0001\u0000\u0000\u0000\u012f\u0130\u0005\u0018\u0000\u0000\u0130\u0132\u0003\u0050\u0028\u0000\u0131\u0133\u0003\u0018\u000c\u0000\u0132\u0131\u0001\u0000\u0000\u0000\u0132\u0133\u0001\u0000\u0000\u0000\u0133\u0033\u0001\u0000\u0000\u0000\u0134\u0136\u0003\u0002\u0001\u0000\u0135\u0134\u0001\u0000\u0000\u0000\u0135\u0136\u0001\u0000\u0000\u0000\u0136\u0137\u0001\u0000\u0000\u0000\u0137\u0138\u0005\u0014\u0000\u0000\u0138\u013a\u0003\u0050\u0028\u0000\u0139\u013b\u0003\u0018\u000c\u0000\u013a\u0139\u0001\u0000\u0000\u0000\u013a\u013b\u0001\u0000\u0000\u0000\u013b\u013c\u0001\u0000\u0000\u0000\u013c\u013d\u0005\u0009\u0000\u0000\u013d\u013e\u0003\u0038\u001c\u0000\u013e\u0035\u0001\u0000\u0000\u0000\u013f\u0141\u0003\u0002\u0001\u0000\u0140\u013f\u0001\u0000\u0000\u0000\u0140\u0141\u0001\u0000\u0000\u0000\u0141\u0142\u0001\u0000\u0000\u0000\u0142\u0143\u0005\u0017\u0000\u0000\u0143\u0144\u0005\u0014\u0000\u0000\u0144\u0146\u0003\u0050\u0028\u0000\u0145\u0147\u0003\u0018\u000c\u0000\u0146\u0145\u0001\u0000\u0000\u0000\u0146\u0147\u0001\u0000\u0000\u0000\u0147\u0148\u0001\u0000\u0000\u0000\u0148\u0149\u0005\u0009\u0000\u0000\u0149\u014a\u0003\u0038\u001c\u0000\u014a\u0037\u0001\u0000\u0000\u0000\u014b\u014c\u0003\u0050\u0028\u0000\u014c\u014d\u0005\u0004\u0000\u0000\u014d\u014f\u0001\u0000\u0000\u0000\u014e\u014b\u0001\u0000\u0000\u0000\u014f\u0152\u0001\u0000\u0000\u0000\u0150\u014e\u0001\u0000\u0000\u0000\u0150\u0151\u0001\u0000\u0000\u0000\u0151\u0153\u0001\u0000\u0000\u0000\u0152\u0150\u0001\u0000\u0000\u0000\u0153\u0154\u0003\u0050\u0028\u0000\u0154\u0039\u0001\u0000\u0000\u0000\u0155\u0157\u0003\u0002\u0001\u0000\u0156\u0155\u0001\u0000\u0000\u0000\u0156\u0157\u0001\u0000\u0000\u0000\u0157\u0158\u0001\u0000\u0000\u0000\u0158\u0159\u0005\u0013\u0000\u0000\u0159\u015b\u0003\u0050\u0028\u0000\u015a\u015c\u0003\u0018\u000c\u0000\u015b\u015a\u0001\u0000\u0000\u0000\u015b\u015c\u0001\u0000\u0000\u0000\u015c\u015d\u0001\u0000\u0000\u0000\u015d\u015e\u0003\u003c\u001e\u0000\u015e\u003b\u0001\u0000\u0000\u0000\u015f\u0161\u0005\u0001\u0000\u0000\u0160\u0162\u0003\u003e\u001f\u0000\u0161\u0160\u0001\u0000\u0000\u0000\u0162\u0163\u0001\u0000\u0000\u0000\u0163\u0161\u0001\u0000\u0000\u0000\u0163\u0164\u0001\u0000\u0000\u0000\u0164\u0165\u0001\u0000\u0000\u0000\u0165\u0166\u0005\u0002\u0000\u0000\u0166\u003d\u0001\u0000\u0000\u0000\u0167\u0169\u0003\u0002\u0001\u0000\u0168\u0167\u0001\u0000\u0000\u0000\u0168\u0169\u0001\u0000\u0000\u0000\u0169\u016a\u0001\u0000\u0000\u0000\u016a\u016c\u0003\u0052\u0029\u0000\u016b\u016d\u0003\u0018\u000c\u0000\u016c\u016b\u0001\u0000\u0000\u0000\u016c\u016d\u0001\u0000\u0000\u0000\u016d\u003f\u0001\u0000\u0000\u0000\u016e\u0170\u0003\u0002\u0001\u0000\u016f\u016e\u0001\u0000\u0000\u0000\u016f\u0170\u0001\u0000\u0000\u0000\u0170\u0171\u0001\u0000\u0000\u0000\u0171\u0173\u0003\u0050\u0028\u0000\u0172\u0174\u0003\u0042\u0021\u0000\u0173\u0172\u0001\u0000\u0000\u0000\u0173\u0174\u0001\u0000\u0000\u0000\u0174\u0175\u0001\u0000\u0000\u0000\u0175\u0176\u0005\u0003\u0000\u0000\u0176\u0178\u0003\u0046\u0023\u0000\u0177\u0179\u0003\u0018\u000c\u0000\u0178\u0177\u0001\u0000\u0000\u0000\u0178\u0179\u0001\u0000\u0000\u0000\u0179\u0041\u0001\u0000\u0000\u0000\u017a\u017c\u0005\u0006\u0000\u0000\u017b\u017d\u0003\u0044\u0022\u0000\u017c\u017b\u0001\u0000\u0000\u0000\u017d\u017e\u0001\u0000\u0000\u0000\u017e\u017c\u0001\u0000\u0000\u0000\u017e\u017f\u0001\u0000\u0000\u0000\u017f\u0180\u0001\u0000\u0000\u0000\u0180\u0181\u0005\u0007\u0000\u0000\u0181\u0043\u0001\u0000\u0000\u0000\u0182\u0184\u0003\u0002\u0001\u0000\u0183\u0182\u0001\u0000\u0000\u0000\u0183\u0184\u0001\u0000\u0000\u0000\u0184\u0185\u0001\u0000\u0000\u0000\u0185\u0186\u0003\u0050\u0028\u0000\u0186\u0187\u0005\u0003\u0000\u0000\u0187\u0189\u0003\u0046\u0023\u0000\u0188\u018a\u0003\u004e\u0027\u0000\u0189\u0188\u0001\u0000\u0000\u0000\u0189\u018a\u0001\u0000\u0000\u0000\u018a\u018c\u0001\u0000\u0000\u0000\u018b\u018d\u0003\u0018\u000c\u0000\u018c\u018b\u0001\u0000\u0000\u0000\u018c\u018d\u0001\u0000\u0000\u0000\u018d\u0045\u0001\u0000\u0000\u0000\u018e\u0191\u0003\u0048\u0024\u0000\u018f\u0191\u0003\u004a\u0025\u0000\u0190\u018e\u0001\u0000\u0000\u0000\u0190\u018f\u0001\u0000\u0000\u0000\u0191\u0193\u0001\u0000\u0000\u0000\u0192\u0194\u0003\u004c\u0026\u0000\u0193\u0192\u0001\u0000\u0000\u0000\u0193\u0194\u0001\u0000\u0000\u0000\u0194\u0047\u0001\u0000\u0000\u0000\u0195\u0196\u0003\u0050\u0028\u0000\u0196\u0049\u0001\u0000\u0000\u0000\u0197\u0198\u0005\u000a\u0000\u0000\u0198\u0199\u0003\u0046\u0023\u0000\u0199\u019a\u0005\u000b\u0000\u0000\u019a\u004b\u0001\u0000\u0000\u0000\u019b\u019c\u0005\u000c\u0000\u0000\u019c\u004d\u0001\u0000\u0000\u0000\u019d\u019e\u0005\u0009\u0000\u0000\u019e\u019f\u0003\u0056\u002b\u0000\u019f\u004f\u0001\u0000\u0000\u0000\u01a0\u01a5\u0003\u0052\u0029\u0000\u01a1\u01a5\u0005\u001f\u0000\u0000\u01a2\u01a5\u0005\u0020\u0000\u0000\u01a3\u01a5\u0005\u0021\u0000\u0000\u01a4\u01a0\u0001\u0000\u0000\u0000\u01a4\u01a1\u0001\u0000\u0000\u0000\u01a4\u01a2\u0001\u0000\u0000\u0000\u01a4\u01a3\u0001\u0000\u0000\u0000\u01a5\u0051\u0001\u0000\u0000\u0000\u01a6\u01a7\u0007\u0001\u0000\u0000\u01a7\u0053\u0001\u0000\u0000\u0000\u01a8\u01a9\u0007\u0002\u0000\u0000\u01a9\u0055\u0001\u0000\u0000\u0000\u01aa\u01b4\u0005\u0023\u0000\u0000\u01ab\u01b4\u0005\u0024\u0000\u0000\u01ac\u01b4\u0005\u002a\u0000\u0000\u01ad\u01b4\u0005\u002b\u0000\u0000\u01ae\u01b4\u0003\u0054\u002a\u0000\u01af\u01b4\u0003\u0060\u0030\u0000\u01b0\u01b4\u0003\u0058\u002c\u0000\u01b1\u01b4\u0003\u005a\u002d\u0000\u01b2\u01b4\u0003\u005c\u002e\u0000\u01b3\u01aa\u0001\u0000\u0000\u0000\u01b3\u01ab\u0001\u0000\u0000\u0000\u01b3\u01ac\u0001\u0000\u0000\u0000\u01b3\u01ad\u0001\u0000\u0000\u0000\u01b3\u01ae\u0001\u0000\u0000\u0000\u01b3\u01af\u0001\u0000\u0000\u0000\u01b3\u01b0\u0001\u0000\u0000\u0000\u01b3\u01b1\u0001\u0000\u0000\u0000\u01b3\u01b2\u0001\u0000\u0000\u0000\u01b4\u0057\u0001\u0000\u0000\u0000\u01b5\u01b6\u0003\u0052\u0029\u0000\u01b6\u0059\u0001\u0000\u0000\u0000\u01b7\u01bb\u0005\u000a\u0000\u0000\u01b8\u01ba\u0003\u0056\u002b\u0000\u01b9\u01b8\u0001\u0000\u0000\u0000\u01ba\u01bd\u0001\u0000\u0000\u0000\u01bb\u01b9\u0001\u0000\u0000\u0000\u01bb\u01bc\u0001\u0000\u0000\u0000\u01bc\u01be\u0001\u0000\u0000\u0000\u01bd\u01bb\u0001\u0000\u0000\u0000\u01be\u01bf\u0005\u000b\u0000\u0000\u01bf\u005b\u0001\u0000\u0000\u0000\u01c0\u01c4\u0005\u0001\u0000\u0000\u01c1\u01c3\u0003\u005e\u002f\u0000\u01c2\u01c1\u0001\u0000\u0000\u0000\u01c3\u01c6\u0001\u0000\u0000\u0000\u01c4\u01c2\u0001\u0000\u0000\u0000\u01c4\u01c5\u0001\u0000\u0000\u0000\u01c5\u01c7\u0001\u0000\u0000\u0000\u01c6\u01c4\u0001\u0000\u0000\u0000\u01c7\u01c8\u0005\u0002\u0000\u0000\u01c8\u005d\u0001\u0000\u0000\u0000\u01c9\u01ca\u0003\u0050\u0028\u0000\u01ca\u01cb\u0005\u0003\u0000\u0000\u01cb\u01cc\u0003\u0056\u002b\u0000\u01cc\u005f\u0001\u0000\u0000\u0000\u01cd\u01ce\u0005\u0021\u0000\u0000\u01ce\u0061\u0001\u0000\u0000\u0000\u003d\u006d\u006f\u0076\u007c\u0083\u0096\u009d\u00a4\u00aa\u00b2\u00b7\u00bd\u00c6\u00cb\u00ce\u00d1\u00d4\u00da\u00dd\u00e0\u00e6\u00ec\u00f3\u00f7\u00fc\u00ff\u0102\u0108\u010b\u0111\u0116\u011c\u011f\u0122\u0127\u012a\u012d\u0132\u0135\u013a\u0140\u0146\u0150\u0156\u015b\u0163\u0168\u016c\u016f\u0173\u0178\u017e\u0183\u0189\u018c\u0190\u0193\u01a4\u01b3\u01bb\u01c4"

        private val ATN = ATNDeserializer().deserialize(SERIALIZED_ATN.toCharArray())

        private val DECISION_TO_DFA = Array(ATN.numberOfDecisions) {
            DFA(ATN.getDecisionState(it)!!, it)
        }

        private val SHARED_CONTEXT_CACHE = PredictionContextCache()
        private val RULE_NAMES: Array<String> = arrayOf(
            "graphqlSchema", "description", "schemaDef", "operationTypeDef", 
            "queryOperationDef", "mutationOperationDef", "subscriptionOperationDef", 
            "directiveLocationList", "directiveLocation", "executableDirectiveLocation", 
            "typeSystemDirectiveLocation", "directiveDef", "directiveList", 
            "directive", "directiveArgList", "directiveArg", "typeDef", 
            "typeExtDef", "fieldDefs", "implementationDef", "inputTypeDef", 
            "inputTypeExtDef", "inputValueDefs", "inputValueDef", "interfaceDef", 
            "scalarDef", "unionDef", "unionExtDef", "unionTypes", "enumDef", 
            "enumValueDefs", "enumValueDef", "fieldDef", "argList", "argument", 
            "typeSpec", "typeName", "listType", "required", "defaultValue", 
            "anyName", "nameTokens", "booleanValue", "value", "enumValue", 
            "arrayValue", "objectValue", "objectField", "nullValue"
        )

        private val LITERAL_NAMES: Array<String?> = arrayOf(
            null, "'{'", "'}'", "':'", "'|'", "'@'", "'('", "')'", "'&'", 
            "'='", "'['", "']'", "'!'", null, null, "'type'", "'implements'", 
            "'interface'", "'schema'", "'enum'", "'union'", "'input'", "'directive'", 
            "'extend'", "'scalar'", "'on'", "'fragment'", "'query'", "'mutation'", 
            "'subscription'", "'value'", "'true'", "'false'", "'null'", 
            null, null, null, "'-'"
        )

        private val SYMBOLIC_NAMES: Array<String?> = arrayOf(
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, "EXECUTABLE_DIRECTIVE_LOCATION", "TYPE_SYSTEM_DIRECTIVE_LOCATION", 
            "K_TYPE", "K_IMPLEMENTS", "K_INTERFACE", "K_SCHEMA", "K_ENUM", 
            "K_UNION", "K_INPUT", "K_DIRECTIVE", "K_EXTEND", "K_SCALAR", 
            "K_ON", "K_FRAGMENT", "K_QUERY", "K_MUTATION", "K_SUBSCRIPTION", 
            "K_VALUE", "K_TRUE", "K_FALSE", "K_NULL", "Name", "IntValue", 
            "FloatValue", "Sign", "IntegerPart", "NonZeroDigit", "ExponentPart", 
            "Digit", "StringValue", "BlockStringValue", "Ignored"
        )

        private val VOCABULARY = VocabularyImpl(LITERAL_NAMES, SYMBOLIC_NAMES)

        private val TOKEN_NAMES: Array<String> = Array(SYMBOLIC_NAMES.size) {
            VOCABULARY.getLiteralName(it)
                ?: VOCABULARY.getSymbolicName(it)
                ?: "<INVALID>"
        }
    }

    public object Tokens {
        public const val EOF: Int = -1
        public const val T__0: Int = 1
        public const val T__1: Int = 2
        public const val T__2: Int = 3
        public const val T__3: Int = 4
        public const val T__4: Int = 5
        public const val T__5: Int = 6
        public const val T__6: Int = 7
        public const val T__7: Int = 8
        public const val T__8: Int = 9
        public const val T__9: Int = 10
        public const val T__10: Int = 11
        public const val T__11: Int = 12
        public const val EXECUTABLE_DIRECTIVE_LOCATION: Int = 13
        public const val TYPE_SYSTEM_DIRECTIVE_LOCATION: Int = 14
        public const val K_TYPE: Int = 15
        public const val K_IMPLEMENTS: Int = 16
        public const val K_INTERFACE: Int = 17
        public const val K_SCHEMA: Int = 18
        public const val K_ENUM: Int = 19
        public const val K_UNION: Int = 20
        public const val K_INPUT: Int = 21
        public const val K_DIRECTIVE: Int = 22
        public const val K_EXTEND: Int = 23
        public const val K_SCALAR: Int = 24
        public const val K_ON: Int = 25
        public const val K_FRAGMENT: Int = 26
        public const val K_QUERY: Int = 27
        public const val K_MUTATION: Int = 28
        public const val K_SUBSCRIPTION: Int = 29
        public const val K_VALUE: Int = 30
        public const val K_TRUE: Int = 31
        public const val K_FALSE: Int = 32
        public const val K_NULL: Int = 33
        public const val Name: Int = 34
        public const val IntValue: Int = 35
        public const val FloatValue: Int = 36
        public const val Sign: Int = 37
        public const val IntegerPart: Int = 38
        public const val NonZeroDigit: Int = 39
        public const val ExponentPart: Int = 40
        public const val Digit: Int = 41
        public const val StringValue: Int = 42
        public const val BlockStringValue: Int = 43
        public const val Ignored: Int = 44
    }

    public object Rules {
        public const val GraphqlSchema: Int = 0
        public const val Description: Int = 1
        public const val SchemaDef: Int = 2
        public const val OperationTypeDef: Int = 3
        public const val QueryOperationDef: Int = 4
        public const val MutationOperationDef: Int = 5
        public const val SubscriptionOperationDef: Int = 6
        public const val DirectiveLocationList: Int = 7
        public const val DirectiveLocation: Int = 8
        public const val ExecutableDirectiveLocation: Int = 9
        public const val TypeSystemDirectiveLocation: Int = 10
        public const val DirectiveDef: Int = 11
        public const val DirectiveList: Int = 12
        public const val Directive: Int = 13
        public const val DirectiveArgList: Int = 14
        public const val DirectiveArg: Int = 15
        public const val TypeDef: Int = 16
        public const val TypeExtDef: Int = 17
        public const val FieldDefs: Int = 18
        public const val ImplementationDef: Int = 19
        public const val InputTypeDef: Int = 20
        public const val InputTypeExtDef: Int = 21
        public const val InputValueDefs: Int = 22
        public const val InputValueDef: Int = 23
        public const val InterfaceDef: Int = 24
        public const val ScalarDef: Int = 25
        public const val UnionDef: Int = 26
        public const val UnionExtDef: Int = 27
        public const val UnionTypes: Int = 28
        public const val EnumDef: Int = 29
        public const val EnumValueDefs: Int = 30
        public const val EnumValueDef: Int = 31
        public const val FieldDef: Int = 32
        public const val ArgList: Int = 33
        public const val Argument: Int = 34
        public const val TypeSpec: Int = 35
        public const val TypeName: Int = 36
        public const val ListType: Int = 37
        public const val Required: Int = 38
        public const val DefaultValue: Int = 39
        public const val AnyName: Int = 40
        public const val NameTokens: Int = 41
        public const val BooleanValue: Int = 42
        public const val Value: Int = 43
        public const val EnumValue: Int = 44
        public const val ArrayValue: Int = 45
        public const val ObjectValue: Int = 46
        public const val ObjectField: Int = 47
        public const val NullValue: Int = 48
    }

    override var interpreter: ParserATNSimulator =
        @Suppress("LeakingThis")
        ParserATNSimulator(this, ATN, DECISION_TO_DFA, SHARED_CONTEXT_CACHE)

    override val grammarFileName: String =
        "GraphQL.g4"

    @Deprecated("Use vocabulary instead", replaceWith = ReplaceWith("vocabulary"))
    override val tokenNames: Array<String> =
        TOKEN_NAMES

    override val ruleNames: Array<String> =
        RULE_NAMES

    override val atn: ATN =
        ATN

    override val vocabulary: Vocabulary =
        VOCABULARY

    override val serializedATN: String =
        SERIALIZED_ATN

    /* Named actions */

    /* Funcs */
    public open class GraphqlSchemaContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.GraphqlSchema

        public fun schemaDef(): List<SchemaDefContext> = getRuleContexts(SchemaDefContext::class)
        public fun schemaDef(i: Int): SchemaDefContext? = getRuleContext(SchemaDefContext::class, i)
        public fun typeDef(): List<TypeDefContext> = getRuleContexts(TypeDefContext::class)
        public fun typeDef(i: Int): TypeDefContext? = getRuleContext(TypeDefContext::class, i)
        public fun typeExtDef(): List<TypeExtDefContext> = getRuleContexts(TypeExtDefContext::class)
        public fun typeExtDef(i: Int): TypeExtDefContext? = getRuleContext(TypeExtDefContext::class, i)
        public fun inputTypeDef(): List<InputTypeDefContext> = getRuleContexts(InputTypeDefContext::class)
        public fun inputTypeDef(i: Int): InputTypeDefContext? = getRuleContext(InputTypeDefContext::class, i)
        public fun inputTypeExtDef(): List<InputTypeExtDefContext> = getRuleContexts(InputTypeExtDefContext::class)
        public fun inputTypeExtDef(i: Int): InputTypeExtDefContext? = getRuleContext(InputTypeExtDefContext::class, i)
        public fun unionDef(): List<UnionDefContext> = getRuleContexts(UnionDefContext::class)
        public fun unionDef(i: Int): UnionDefContext? = getRuleContext(UnionDefContext::class, i)
        public fun unionExtDef(): List<UnionExtDefContext> = getRuleContexts(UnionExtDefContext::class)
        public fun unionExtDef(i: Int): UnionExtDefContext? = getRuleContext(UnionExtDefContext::class, i)
        public fun enumDef(): List<EnumDefContext> = getRuleContexts(EnumDefContext::class)
        public fun enumDef(i: Int): EnumDefContext? = getRuleContext(EnumDefContext::class, i)
        public fun interfaceDef(): List<InterfaceDefContext> = getRuleContexts(InterfaceDefContext::class)
        public fun interfaceDef(i: Int): InterfaceDefContext? = getRuleContext(InterfaceDefContext::class, i)
        public fun scalarDef(): List<ScalarDefContext> = getRuleContexts(ScalarDefContext::class)
        public fun scalarDef(i: Int): ScalarDefContext? = getRuleContext(ScalarDefContext::class, i)
        public fun directiveDef(): List<DirectiveDefContext> = getRuleContexts(DirectiveDefContext::class)
        public fun directiveDef(i: Int): DirectiveDefContext? = getRuleContext(DirectiveDefContext::class, i)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterGraphqlSchema(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitGraphqlSchema(this)
            }
        }
    }


    public fun graphqlSchema(): GraphqlSchemaContext {
        var _localctx = GraphqlSchemaContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 0, Rules.GraphqlSchema)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 111
            errorHandler.sync(this)
            _la = _input.LA(1)

            while ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and 13194172989440L) != 0L)) {
                this.state = 109
                errorHandler.sync(this)

                when (interpreter.adaptivePredict(_input, 0, context)) {
                    1 -> {
                        this.state = 98
                        schemaDef()

                    }2 -> {
                        this.state = 99
                        typeDef()

                    }3 -> {
                        this.state = 100
                        typeExtDef()

                    }4 -> {
                        this.state = 101
                        inputTypeDef()

                    }5 -> {
                        this.state = 102
                        inputTypeExtDef()

                    }6 -> {
                        this.state = 103
                        unionDef()

                    }7 -> {
                        this.state = 104
                        unionExtDef()

                    }8 -> {
                        this.state = 105
                        enumDef()

                    }9 -> {
                        this.state = 106
                        interfaceDef()

                    }10 -> {
                        this.state = 107
                        scalarDef()

                    }11 -> {
                        this.state = 108
                        directiveDef()

                    }
                }
                this.state = 113
                errorHandler.sync(this)
                _la = _input.LA(1)
            }
        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class DescriptionContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.Description

        public fun StringValue(): TerminalNode? = getToken(Tokens.StringValue, 0)
        public fun BlockStringValue(): TerminalNode? = getToken(Tokens.BlockStringValue, 0)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterDescription(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitDescription(this)
            }
        }
    }


    public fun description(): DescriptionContext {
        var _localctx = DescriptionContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 2, Rules.Description)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 114
            _la = _input.LA(1)

            if (!(_la == Tokens.StringValue || _la == Tokens.BlockStringValue)) {
                errorHandler.recoverInline(this)
            }
            else {
                if (_input.LA(1) == Tokens.EOF) {
                    isMatchedEOF = true
                }

                errorHandler.reportMatch(this)
                consume()
            }
        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class SchemaDefContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.SchemaDef

        public fun K_SCHEMA(): TerminalNode = getToken(Tokens.K_SCHEMA, 0)!!
        public fun directiveList(): DirectiveListContext? = getRuleContext(DirectiveListContext::class, 0)
        public fun operationTypeDef(): List<OperationTypeDefContext> = getRuleContexts(OperationTypeDefContext::class)
        public fun operationTypeDef(i: Int): OperationTypeDefContext? = getRuleContext(OperationTypeDefContext::class, i)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterSchemaDef(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitSchemaDef(this)
            }
        }
    }


    public fun schemaDef(): SchemaDefContext {
        var _localctx = SchemaDefContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 4, Rules.SchemaDef)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 116
            match(Tokens.K_SCHEMA)

            this.state = 118
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__4) {
                this.state = 117
                directiveList()

            }
            this.state = 120
            match(Tokens.T__0)

            this.state = 122 
            errorHandler.sync(this)
            _la = _input.LA(1)

            do {
                this.state = 121
                operationTypeDef()

                this.state = 124 
                errorHandler.sync(this)
                _la = _input.LA(1)
            } while ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and 939524096L) != 0L))
            this.state = 126
            match(Tokens.T__1)

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class OperationTypeDefContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.OperationTypeDef

        public fun queryOperationDef(): QueryOperationDefContext? = getRuleContext(QueryOperationDefContext::class, 0)
        public fun mutationOperationDef(): MutationOperationDefContext? = getRuleContext(MutationOperationDefContext::class, 0)
        public fun subscriptionOperationDef(): SubscriptionOperationDefContext? = getRuleContext(SubscriptionOperationDefContext::class, 0)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterOperationTypeDef(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitOperationTypeDef(this)
            }
        }
    }


    public fun operationTypeDef(): OperationTypeDefContext {
        var _localctx = OperationTypeDefContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 6, Rules.OperationTypeDef)

        try {
            this.state = 131
            errorHandler.sync(this)

            when (_input.LA(1)) {
                Tokens.K_QUERY -> /*LL1AltBlock*/ {
                    enterOuterAlt(_localctx, 1)
                    this.state = 128
                    queryOperationDef()

                }Tokens.K_MUTATION -> /*LL1AltBlock*/ {
                    enterOuterAlt(_localctx, 2)
                    this.state = 129
                    mutationOperationDef()

                }Tokens.K_SUBSCRIPTION -> /*LL1AltBlock*/ {
                    enterOuterAlt(_localctx, 3)
                    this.state = 130
                    subscriptionOperationDef()

                }
                else -> throw NoViableAltException(this)
            }
        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class QueryOperationDefContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.QueryOperationDef

        public fun K_QUERY(): TerminalNode = getToken(Tokens.K_QUERY, 0)!!
        public fun anyName(): AnyNameContext = getRuleContext(AnyNameContext::class, 0)!!

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterQueryOperationDef(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitQueryOperationDef(this)
            }
        }
    }


    public fun queryOperationDef(): QueryOperationDefContext {
        var _localctx = QueryOperationDefContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 8, Rules.QueryOperationDef)

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 133
            match(Tokens.K_QUERY)

            this.state = 134
            match(Tokens.T__2)

            this.state = 135
            anyName()

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class MutationOperationDefContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.MutationOperationDef

        public fun K_MUTATION(): TerminalNode = getToken(Tokens.K_MUTATION, 0)!!
        public fun anyName(): AnyNameContext = getRuleContext(AnyNameContext::class, 0)!!

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterMutationOperationDef(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitMutationOperationDef(this)
            }
        }
    }


    public fun mutationOperationDef(): MutationOperationDefContext {
        var _localctx = MutationOperationDefContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 10, Rules.MutationOperationDef)

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 137
            match(Tokens.K_MUTATION)

            this.state = 138
            match(Tokens.T__2)

            this.state = 139
            anyName()

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class SubscriptionOperationDefContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.SubscriptionOperationDef

        public fun K_SUBSCRIPTION(): TerminalNode = getToken(Tokens.K_SUBSCRIPTION, 0)!!
        public fun anyName(): AnyNameContext = getRuleContext(AnyNameContext::class, 0)!!

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterSubscriptionOperationDef(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitSubscriptionOperationDef(this)
            }
        }
    }


    public fun subscriptionOperationDef(): SubscriptionOperationDefContext {
        var _localctx = SubscriptionOperationDefContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 12, Rules.SubscriptionOperationDef)

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 141
            match(Tokens.K_SUBSCRIPTION)

            this.state = 142
            match(Tokens.T__2)

            this.state = 143
            anyName()

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class DirectiveLocationListContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.DirectiveLocationList

        public fun directiveLocation(): List<DirectiveLocationContext> = getRuleContexts(DirectiveLocationContext::class)
        public fun directiveLocation(i: Int): DirectiveLocationContext? = getRuleContext(DirectiveLocationContext::class, i)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterDirectiveLocationList(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitDirectiveLocationList(this)
            }
        }
    }


    public fun directiveLocationList(): DirectiveLocationListContext {
        var _localctx = DirectiveLocationListContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 14, Rules.DirectiveLocationList)

        try {
            var _alt: Int
            enterOuterAlt(_localctx, 1)
            this.state = 150
            errorHandler.sync(this)
            _alt = interpreter.adaptivePredict(_input, 5, context)

            while (_alt != 2 && _alt != INVALID_ALT_NUMBER) {
                if (_alt == 1 ) {
                    this.state = 145
                    directiveLocation()

                    this.state = 146
                    match(Tokens.T__3)
             
                }

                this.state = 152
                errorHandler.sync(this)
                _alt = interpreter.adaptivePredict(_input, 5, context)
            }
            this.state = 153
            directiveLocation()

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class DirectiveLocationContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.DirectiveLocation

        public fun executableDirectiveLocation(): ExecutableDirectiveLocationContext? = getRuleContext(ExecutableDirectiveLocationContext::class, 0)
        public fun typeSystemDirectiveLocation(): TypeSystemDirectiveLocationContext? = getRuleContext(TypeSystemDirectiveLocationContext::class, 0)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterDirectiveLocation(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitDirectiveLocation(this)
            }
        }
    }


    public fun directiveLocation(): DirectiveLocationContext {
        var _localctx = DirectiveLocationContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 16, Rules.DirectiveLocation)

        try {
            this.state = 157
            errorHandler.sync(this)

            when (_input.LA(1)) {
                Tokens.EXECUTABLE_DIRECTIVE_LOCATION -> /*LL1AltBlock*/ {
                    enterOuterAlt(_localctx, 1)
                    this.state = 155
                    executableDirectiveLocation()

                }Tokens.TYPE_SYSTEM_DIRECTIVE_LOCATION -> /*LL1AltBlock*/ {
                    enterOuterAlt(_localctx, 2)
                    this.state = 156
                    typeSystemDirectiveLocation()

                }
                else -> throw NoViableAltException(this)
            }
        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class ExecutableDirectiveLocationContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.ExecutableDirectiveLocation

        public fun EXECUTABLE_DIRECTIVE_LOCATION(): TerminalNode = getToken(Tokens.EXECUTABLE_DIRECTIVE_LOCATION, 0)!!

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterExecutableDirectiveLocation(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitExecutableDirectiveLocation(this)
            }
        }
    }


    public fun executableDirectiveLocation(): ExecutableDirectiveLocationContext {
        var _localctx = ExecutableDirectiveLocationContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 18, Rules.ExecutableDirectiveLocation)

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 159
            match(Tokens.EXECUTABLE_DIRECTIVE_LOCATION)

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class TypeSystemDirectiveLocationContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.TypeSystemDirectiveLocation

        public fun TYPE_SYSTEM_DIRECTIVE_LOCATION(): TerminalNode = getToken(Tokens.TYPE_SYSTEM_DIRECTIVE_LOCATION, 0)!!

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterTypeSystemDirectiveLocation(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitTypeSystemDirectiveLocation(this)
            }
        }
    }


    public fun typeSystemDirectiveLocation(): TypeSystemDirectiveLocationContext {
        var _localctx = TypeSystemDirectiveLocationContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 20, Rules.TypeSystemDirectiveLocation)

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 161
            match(Tokens.TYPE_SYSTEM_DIRECTIVE_LOCATION)

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class DirectiveDefContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.DirectiveDef

        public fun K_DIRECTIVE(): TerminalNode = getToken(Tokens.K_DIRECTIVE, 0)!!
        public fun anyName(): AnyNameContext = getRuleContext(AnyNameContext::class, 0)!!
        public fun K_ON(): TerminalNode = getToken(Tokens.K_ON, 0)!!
        public fun directiveLocationList(): DirectiveLocationListContext = getRuleContext(DirectiveLocationListContext::class, 0)!!
        public fun description(): DescriptionContext? = getRuleContext(DescriptionContext::class, 0)
        public fun argList(): ArgListContext? = getRuleContext(ArgListContext::class, 0)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterDirectiveDef(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitDirectiveDef(this)
            }
        }
    }


    public fun directiveDef(): DirectiveDefContext {
        var _localctx = DirectiveDefContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 22, Rules.DirectiveDef)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 164
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.StringValue || _la == Tokens.BlockStringValue) {
                this.state = 163
                description()

            }
            this.state = 166
            match(Tokens.K_DIRECTIVE)

            this.state = 167
            match(Tokens.T__4)

            this.state = 168
            anyName()

            this.state = 170
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__5) {
                this.state = 169
                argList()

            }
            this.state = 172
            match(Tokens.K_ON)

            this.state = 173
            directiveLocationList()

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class DirectiveListContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.DirectiveList

        public fun directive(): List<DirectiveContext> = getRuleContexts(DirectiveContext::class)
        public fun directive(i: Int): DirectiveContext? = getRuleContext(DirectiveContext::class, i)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterDirectiveList(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitDirectiveList(this)
            }
        }
    }


    public fun directiveList(): DirectiveListContext {
        var _localctx = DirectiveListContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 24, Rules.DirectiveList)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 176 
            errorHandler.sync(this)
            _la = _input.LA(1)

            do {
                this.state = 175
                directive()

                this.state = 178 
                errorHandler.sync(this)
                _la = _input.LA(1)
            } while (_la == Tokens.T__4)
        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class DirectiveContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.Directive

        public fun anyName(): AnyNameContext = getRuleContext(AnyNameContext::class, 0)!!
        public fun directiveArgList(): DirectiveArgListContext? = getRuleContext(DirectiveArgListContext::class, 0)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterDirective(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitDirective(this)
            }
        }
    }


    public fun directive(): DirectiveContext {
        var _localctx = DirectiveContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 26, Rules.Directive)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 180
            match(Tokens.T__4)

            this.state = 181
            anyName()

            this.state = 183
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__5) {
                this.state = 182
                directiveArgList()

            }
        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class DirectiveArgListContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.DirectiveArgList

        public fun directiveArg(): List<DirectiveArgContext> = getRuleContexts(DirectiveArgContext::class)
        public fun directiveArg(i: Int): DirectiveArgContext? = getRuleContext(DirectiveArgContext::class, i)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterDirectiveArgList(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitDirectiveArgList(this)
            }
        }
    }


    public fun directiveArgList(): DirectiveArgListContext {
        var _localctx = DirectiveArgListContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 28, Rules.DirectiveArgList)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 185
            match(Tokens.T__5)

            this.state = 187 
            errorHandler.sync(this)
            _la = _input.LA(1)

            do {
                this.state = 186
                directiveArg()

                this.state = 189 
                errorHandler.sync(this)
                _la = _input.LA(1)
            } while ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and 34359730176L) != 0L))
            this.state = 191
            match(Tokens.T__6)

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class DirectiveArgContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.DirectiveArg

        public fun anyName(): AnyNameContext = getRuleContext(AnyNameContext::class, 0)!!
        public fun value(): ValueContext = getRuleContext(ValueContext::class, 0)!!

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterDirectiveArg(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitDirectiveArg(this)
            }
        }
    }


    public fun directiveArg(): DirectiveArgContext {
        var _localctx = DirectiveArgContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 30, Rules.DirectiveArg)

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 193
            anyName()

            this.state = 194
            match(Tokens.T__2)

            this.state = 195
            value()

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class TypeDefContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.TypeDef

        public fun K_TYPE(): TerminalNode = getToken(Tokens.K_TYPE, 0)!!
        public fun anyName(): AnyNameContext = getRuleContext(AnyNameContext::class, 0)!!
        public fun description(): DescriptionContext? = getRuleContext(DescriptionContext::class, 0)
        public fun implementationDef(): ImplementationDefContext? = getRuleContext(ImplementationDefContext::class, 0)
        public fun directiveList(): DirectiveListContext? = getRuleContext(DirectiveListContext::class, 0)
        public fun fieldDefs(): FieldDefsContext? = getRuleContext(FieldDefsContext::class, 0)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterTypeDef(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitTypeDef(this)
            }
        }
    }


    public fun typeDef(): TypeDefContext {
        var _localctx = TypeDefContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 32, Rules.TypeDef)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 198
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.StringValue || _la == Tokens.BlockStringValue) {
                this.state = 197
                description()

            }
            this.state = 200
            match(Tokens.K_TYPE)

            this.state = 201
            anyName()

            this.state = 203
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.K_IMPLEMENTS) {
                this.state = 202
                implementationDef()

            }
            this.state = 206
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__4) {
                this.state = 205
                directiveList()

            }
            this.state = 209
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__0) {
                this.state = 208
                fieldDefs()

            }
        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class TypeExtDefContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.TypeExtDef

        public fun K_EXTEND(): TerminalNode = getToken(Tokens.K_EXTEND, 0)!!
        public fun K_TYPE(): TerminalNode = getToken(Tokens.K_TYPE, 0)!!
        public fun anyName(): AnyNameContext = getRuleContext(AnyNameContext::class, 0)!!
        public fun description(): DescriptionContext? = getRuleContext(DescriptionContext::class, 0)
        public fun implementationDef(): ImplementationDefContext? = getRuleContext(ImplementationDefContext::class, 0)
        public fun directiveList(): DirectiveListContext? = getRuleContext(DirectiveListContext::class, 0)
        public fun fieldDefs(): FieldDefsContext? = getRuleContext(FieldDefsContext::class, 0)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterTypeExtDef(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitTypeExtDef(this)
            }
        }
    }


    public fun typeExtDef(): TypeExtDefContext {
        var _localctx = TypeExtDefContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 34, Rules.TypeExtDef)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 212
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.StringValue || _la == Tokens.BlockStringValue) {
                this.state = 211
                description()

            }
            this.state = 214
            match(Tokens.K_EXTEND)

            this.state = 215
            match(Tokens.K_TYPE)

            this.state = 216
            anyName()

            this.state = 218
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.K_IMPLEMENTS) {
                this.state = 217
                implementationDef()

            }
            this.state = 221
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__4) {
                this.state = 220
                directiveList()

            }
            this.state = 224
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__0) {
                this.state = 223
                fieldDefs()

            }
        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class FieldDefsContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.FieldDefs

        public fun fieldDef(): List<FieldDefContext> = getRuleContexts(FieldDefContext::class)
        public fun fieldDef(i: Int): FieldDefContext? = getRuleContext(FieldDefContext::class, i)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterFieldDefs(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitFieldDefs(this)
            }
        }
    }


    public fun fieldDefs(): FieldDefsContext {
        var _localctx = FieldDefsContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 36, Rules.FieldDefs)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 226
            match(Tokens.T__0)

            this.state = 228 
            errorHandler.sync(this)
            _la = _input.LA(1)

            do {
                this.state = 227
                fieldDef()

                this.state = 230 
                errorHandler.sync(this)
                _la = _input.LA(1)
            } while ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and 13228499263488L) != 0L))
            this.state = 232
            match(Tokens.T__1)

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class ImplementationDefContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.ImplementationDef

        public fun K_IMPLEMENTS(): TerminalNode = getToken(Tokens.K_IMPLEMENTS, 0)!!
        public fun Name(): List<TerminalNode> = getTokens(Tokens.Name)
        public fun Name(i: Int): TerminalNode? = getToken(Tokens.Name, i)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterImplementationDef(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitImplementationDef(this)
            }
        }
    }


    public fun implementationDef(): ImplementationDefContext {
        var _localctx = ImplementationDefContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 38, Rules.ImplementationDef)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 234
            match(Tokens.K_IMPLEMENTS)

            this.state = 236
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__7) {
                this.state = 235
                match(Tokens.T__7)

            }
            this.state = 238
            match(Tokens.Name)

            this.state = 243
            errorHandler.sync(this)
            _la = _input.LA(1)

            while (_la == Tokens.T__7) {
                this.state = 239
                match(Tokens.T__7)

                this.state = 240
                match(Tokens.Name)

                this.state = 245
                errorHandler.sync(this)
                _la = _input.LA(1)
            }
        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class InputTypeDefContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.InputTypeDef

        public fun K_INPUT(): TerminalNode = getToken(Tokens.K_INPUT, 0)!!
        public fun anyName(): AnyNameContext = getRuleContext(AnyNameContext::class, 0)!!
        public fun description(): DescriptionContext? = getRuleContext(DescriptionContext::class, 0)
        public fun directiveList(): DirectiveListContext? = getRuleContext(DirectiveListContext::class, 0)
        public fun inputValueDefs(): InputValueDefsContext? = getRuleContext(InputValueDefsContext::class, 0)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterInputTypeDef(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitInputTypeDef(this)
            }
        }
    }


    public fun inputTypeDef(): InputTypeDefContext {
        var _localctx = InputTypeDefContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 40, Rules.InputTypeDef)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 247
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.StringValue || _la == Tokens.BlockStringValue) {
                this.state = 246
                description()

            }
            this.state = 249
            match(Tokens.K_INPUT)

            this.state = 250
            anyName()

            this.state = 252
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__4) {
                this.state = 251
                directiveList()

            }
            this.state = 255
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__0) {
                this.state = 254
                inputValueDefs()

            }
        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class InputTypeExtDefContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.InputTypeExtDef

        public fun K_EXTEND(): TerminalNode = getToken(Tokens.K_EXTEND, 0)!!
        public fun K_INPUT(): TerminalNode = getToken(Tokens.K_INPUT, 0)!!
        public fun anyName(): AnyNameContext = getRuleContext(AnyNameContext::class, 0)!!
        public fun description(): DescriptionContext? = getRuleContext(DescriptionContext::class, 0)
        public fun directiveList(): DirectiveListContext? = getRuleContext(DirectiveListContext::class, 0)
        public fun inputValueDefs(): InputValueDefsContext? = getRuleContext(InputValueDefsContext::class, 0)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterInputTypeExtDef(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitInputTypeExtDef(this)
            }
        }
    }


    public fun inputTypeExtDef(): InputTypeExtDefContext {
        var _localctx = InputTypeExtDefContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 42, Rules.InputTypeExtDef)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 258
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.StringValue || _la == Tokens.BlockStringValue) {
                this.state = 257
                description()

            }
            this.state = 260
            match(Tokens.K_EXTEND)

            this.state = 261
            match(Tokens.K_INPUT)

            this.state = 262
            anyName()

            this.state = 264
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__4) {
                this.state = 263
                directiveList()

            }
            this.state = 267
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__0) {
                this.state = 266
                inputValueDefs()

            }
        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class InputValueDefsContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.InputValueDefs

        public fun inputValueDef(): List<InputValueDefContext> = getRuleContexts(InputValueDefContext::class)
        public fun inputValueDef(i: Int): InputValueDefContext? = getRuleContext(InputValueDefContext::class, i)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterInputValueDefs(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitInputValueDefs(this)
            }
        }
    }


    public fun inputValueDefs(): InputValueDefsContext {
        var _localctx = InputValueDefsContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 44, Rules.InputValueDefs)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 269
            match(Tokens.T__0)

            this.state = 271 
            errorHandler.sync(this)
            _la = _input.LA(1)

            do {
                this.state = 270
                inputValueDef()

                this.state = 273 
                errorHandler.sync(this)
                _la = _input.LA(1)
            } while ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and 13228499263488L) != 0L))
            this.state = 275
            match(Tokens.T__1)

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class InputValueDefContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.InputValueDef

        public fun anyName(): AnyNameContext = getRuleContext(AnyNameContext::class, 0)!!
        public fun typeSpec(): TypeSpecContext = getRuleContext(TypeSpecContext::class, 0)!!
        public fun description(): DescriptionContext? = getRuleContext(DescriptionContext::class, 0)
        public fun defaultValue(): DefaultValueContext? = getRuleContext(DefaultValueContext::class, 0)
        public fun directiveList(): DirectiveListContext? = getRuleContext(DirectiveListContext::class, 0)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterInputValueDef(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitInputValueDef(this)
            }
        }
    }


    public fun inputValueDef(): InputValueDefContext {
        var _localctx = InputValueDefContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 46, Rules.InputValueDef)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 278
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.StringValue || _la == Tokens.BlockStringValue) {
                this.state = 277
                description()

            }
            this.state = 280
            anyName()

            this.state = 281
            match(Tokens.T__2)

            this.state = 282
            typeSpec()

            this.state = 284
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__8) {
                this.state = 283
                defaultValue()

            }
            this.state = 287
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__4) {
                this.state = 286
                directiveList()

            }
        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class InterfaceDefContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.InterfaceDef

        public fun K_INTERFACE(): TerminalNode = getToken(Tokens.K_INTERFACE, 0)!!
        public fun anyName(): AnyNameContext = getRuleContext(AnyNameContext::class, 0)!!
        public fun description(): DescriptionContext? = getRuleContext(DescriptionContext::class, 0)
        public fun directiveList(): DirectiveListContext? = getRuleContext(DirectiveListContext::class, 0)
        public fun fieldDefs(): FieldDefsContext? = getRuleContext(FieldDefsContext::class, 0)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterInterfaceDef(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitInterfaceDef(this)
            }
        }
    }


    public fun interfaceDef(): InterfaceDefContext {
        var _localctx = InterfaceDefContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 48, Rules.InterfaceDef)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 290
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.StringValue || _la == Tokens.BlockStringValue) {
                this.state = 289
                description()

            }
            this.state = 292
            match(Tokens.K_INTERFACE)

            this.state = 293
            anyName()

            this.state = 295
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__4) {
                this.state = 294
                directiveList()

            }
            this.state = 298
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__0) {
                this.state = 297
                fieldDefs()

            }
        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class ScalarDefContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.ScalarDef

        public fun K_SCALAR(): TerminalNode = getToken(Tokens.K_SCALAR, 0)!!
        public fun anyName(): AnyNameContext = getRuleContext(AnyNameContext::class, 0)!!
        public fun description(): DescriptionContext? = getRuleContext(DescriptionContext::class, 0)
        public fun directiveList(): DirectiveListContext? = getRuleContext(DirectiveListContext::class, 0)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterScalarDef(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitScalarDef(this)
            }
        }
    }


    public fun scalarDef(): ScalarDefContext {
        var _localctx = ScalarDefContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 50, Rules.ScalarDef)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 301
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.StringValue || _la == Tokens.BlockStringValue) {
                this.state = 300
                description()

            }
            this.state = 303
            match(Tokens.K_SCALAR)

            this.state = 304
            anyName()

            this.state = 306
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__4) {
                this.state = 305
                directiveList()

            }
        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class UnionDefContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.UnionDef

        public fun K_UNION(): TerminalNode = getToken(Tokens.K_UNION, 0)!!
        public fun anyName(): AnyNameContext = getRuleContext(AnyNameContext::class, 0)!!
        public fun unionTypes(): UnionTypesContext = getRuleContext(UnionTypesContext::class, 0)!!
        public fun description(): DescriptionContext? = getRuleContext(DescriptionContext::class, 0)
        public fun directiveList(): DirectiveListContext? = getRuleContext(DirectiveListContext::class, 0)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterUnionDef(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitUnionDef(this)
            }
        }
    }


    public fun unionDef(): UnionDefContext {
        var _localctx = UnionDefContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 52, Rules.UnionDef)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 309
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.StringValue || _la == Tokens.BlockStringValue) {
                this.state = 308
                description()

            }
            this.state = 311
            match(Tokens.K_UNION)

            this.state = 312
            anyName()

            this.state = 314
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__4) {
                this.state = 313
                directiveList()

            }
            this.state = 316
            match(Tokens.T__8)

            this.state = 317
            unionTypes()

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class UnionExtDefContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.UnionExtDef

        public fun K_EXTEND(): TerminalNode = getToken(Tokens.K_EXTEND, 0)!!
        public fun K_UNION(): TerminalNode = getToken(Tokens.K_UNION, 0)!!
        public fun anyName(): AnyNameContext = getRuleContext(AnyNameContext::class, 0)!!
        public fun unionTypes(): UnionTypesContext = getRuleContext(UnionTypesContext::class, 0)!!
        public fun description(): DescriptionContext? = getRuleContext(DescriptionContext::class, 0)
        public fun directiveList(): DirectiveListContext? = getRuleContext(DirectiveListContext::class, 0)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterUnionExtDef(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitUnionExtDef(this)
            }
        }
    }


    public fun unionExtDef(): UnionExtDefContext {
        var _localctx = UnionExtDefContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 54, Rules.UnionExtDef)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 320
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.StringValue || _la == Tokens.BlockStringValue) {
                this.state = 319
                description()

            }
            this.state = 322
            match(Tokens.K_EXTEND)

            this.state = 323
            match(Tokens.K_UNION)

            this.state = 324
            anyName()

            this.state = 326
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__4) {
                this.state = 325
                directiveList()

            }
            this.state = 328
            match(Tokens.T__8)

            this.state = 329
            unionTypes()

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class UnionTypesContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.UnionTypes

        public fun anyName(): List<AnyNameContext> = getRuleContexts(AnyNameContext::class)
        public fun anyName(i: Int): AnyNameContext? = getRuleContext(AnyNameContext::class, i)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterUnionTypes(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitUnionTypes(this)
            }
        }
    }


    public fun unionTypes(): UnionTypesContext {
        var _localctx = UnionTypesContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 56, Rules.UnionTypes)

        try {
            var _alt: Int
            enterOuterAlt(_localctx, 1)
            this.state = 336
            errorHandler.sync(this)
            _alt = interpreter.adaptivePredict(_input, 42, context)

            while (_alt != 2 && _alt != INVALID_ALT_NUMBER) {
                if (_alt == 1 ) {
                    this.state = 331
                    anyName()

                    this.state = 332
                    match(Tokens.T__3)
             
                }

                this.state = 338
                errorHandler.sync(this)
                _alt = interpreter.adaptivePredict(_input, 42, context)
            }
            this.state = 339
            anyName()

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class EnumDefContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.EnumDef

        public fun K_ENUM(): TerminalNode = getToken(Tokens.K_ENUM, 0)!!
        public fun anyName(): AnyNameContext = getRuleContext(AnyNameContext::class, 0)!!
        public fun enumValueDefs(): EnumValueDefsContext = getRuleContext(EnumValueDefsContext::class, 0)!!
        public fun description(): DescriptionContext? = getRuleContext(DescriptionContext::class, 0)
        public fun directiveList(): DirectiveListContext? = getRuleContext(DirectiveListContext::class, 0)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterEnumDef(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitEnumDef(this)
            }
        }
    }


    public fun enumDef(): EnumDefContext {
        var _localctx = EnumDefContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 58, Rules.EnumDef)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 342
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.StringValue || _la == Tokens.BlockStringValue) {
                this.state = 341
                description()

            }
            this.state = 344
            match(Tokens.K_ENUM)

            this.state = 345
            anyName()

            this.state = 347
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__4) {
                this.state = 346
                directiveList()

            }
            this.state = 349
            enumValueDefs()

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class EnumValueDefsContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.EnumValueDefs

        public fun enumValueDef(): List<EnumValueDefContext> = getRuleContexts(EnumValueDefContext::class)
        public fun enumValueDef(i: Int): EnumValueDefContext? = getRuleContext(EnumValueDefContext::class, i)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterEnumValueDefs(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitEnumValueDefs(this)
            }
        }
    }


    public fun enumValueDefs(): EnumValueDefsContext {
        var _localctx = EnumValueDefsContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 60, Rules.EnumValueDefs)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 351
            match(Tokens.T__0)

            this.state = 353 
            errorHandler.sync(this)
            _la = _input.LA(1)

            do {
                this.state = 352
                enumValueDef()

                this.state = 355 
                errorHandler.sync(this)
                _la = _input.LA(1)
            } while ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and 13213466877952L) != 0L))
            this.state = 357
            match(Tokens.T__1)

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class EnumValueDefContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.EnumValueDef

        public fun nameTokens(): NameTokensContext = getRuleContext(NameTokensContext::class, 0)!!
        public fun description(): DescriptionContext? = getRuleContext(DescriptionContext::class, 0)
        public fun directiveList(): DirectiveListContext? = getRuleContext(DirectiveListContext::class, 0)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterEnumValueDef(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitEnumValueDef(this)
            }
        }
    }


    public fun enumValueDef(): EnumValueDefContext {
        var _localctx = EnumValueDefContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 62, Rules.EnumValueDef)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 360
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.StringValue || _la == Tokens.BlockStringValue) {
                this.state = 359
                description()

            }
            this.state = 362
            nameTokens()

            this.state = 364
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__4) {
                this.state = 363
                directiveList()

            }
        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class FieldDefContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.FieldDef

        public fun anyName(): AnyNameContext = getRuleContext(AnyNameContext::class, 0)!!
        public fun typeSpec(): TypeSpecContext = getRuleContext(TypeSpecContext::class, 0)!!
        public fun description(): DescriptionContext? = getRuleContext(DescriptionContext::class, 0)
        public fun argList(): ArgListContext? = getRuleContext(ArgListContext::class, 0)
        public fun directiveList(): DirectiveListContext? = getRuleContext(DirectiveListContext::class, 0)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterFieldDef(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitFieldDef(this)
            }
        }
    }


    public fun fieldDef(): FieldDefContext {
        var _localctx = FieldDefContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 64, Rules.FieldDef)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 367
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.StringValue || _la == Tokens.BlockStringValue) {
                this.state = 366
                description()

            }
            this.state = 369
            anyName()

            this.state = 371
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__5) {
                this.state = 370
                argList()

            }
            this.state = 373
            match(Tokens.T__2)

            this.state = 374
            typeSpec()

            this.state = 376
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__4) {
                this.state = 375
                directiveList()

            }
        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class ArgListContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.ArgList

        public fun argument(): List<ArgumentContext> = getRuleContexts(ArgumentContext::class)
        public fun argument(i: Int): ArgumentContext? = getRuleContext(ArgumentContext::class, i)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterArgList(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitArgList(this)
            }
        }
    }


    public fun argList(): ArgListContext {
        var _localctx = ArgListContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 66, Rules.ArgList)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 378
            match(Tokens.T__5)

            this.state = 380 
            errorHandler.sync(this)
            _la = _input.LA(1)

            do {
                this.state = 379
                argument()

                this.state = 382 
                errorHandler.sync(this)
                _la = _input.LA(1)
            } while ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and 13228499263488L) != 0L))
            this.state = 384
            match(Tokens.T__6)

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class ArgumentContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.Argument

        public fun anyName(): AnyNameContext = getRuleContext(AnyNameContext::class, 0)!!
        public fun typeSpec(): TypeSpecContext = getRuleContext(TypeSpecContext::class, 0)!!
        public fun description(): DescriptionContext? = getRuleContext(DescriptionContext::class, 0)
        public fun defaultValue(): DefaultValueContext? = getRuleContext(DefaultValueContext::class, 0)
        public fun directiveList(): DirectiveListContext? = getRuleContext(DirectiveListContext::class, 0)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterArgument(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitArgument(this)
            }
        }
    }


    public fun argument(): ArgumentContext {
        var _localctx = ArgumentContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 68, Rules.Argument)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 387
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.StringValue || _la == Tokens.BlockStringValue) {
                this.state = 386
                description()

            }
            this.state = 389
            anyName()

            this.state = 390
            match(Tokens.T__2)

            this.state = 391
            typeSpec()

            this.state = 393
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__8) {
                this.state = 392
                defaultValue()

            }
            this.state = 396
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__4) {
                this.state = 395
                directiveList()

            }
        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class TypeSpecContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.TypeSpec

        public fun typeName(): TypeNameContext? = getRuleContext(TypeNameContext::class, 0)
        public fun listType(): ListTypeContext? = getRuleContext(ListTypeContext::class, 0)
        public fun required(): RequiredContext? = getRuleContext(RequiredContext::class, 0)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterTypeSpec(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitTypeSpec(this)
            }
        }
    }


    public fun typeSpec(): TypeSpecContext {
        var _localctx = TypeSpecContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 70, Rules.TypeSpec)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 400
            errorHandler.sync(this)

            when (_input.LA(1)) {
                Tokens.EXECUTABLE_DIRECTIVE_LOCATION, Tokens.TYPE_SYSTEM_DIRECTIVE_LOCATION, Tokens.K_TYPE, Tokens.K_IMPLEMENTS, Tokens.K_INTERFACE, Tokens.K_SCHEMA, Tokens.K_ENUM, Tokens.K_UNION, Tokens.K_INPUT, Tokens.K_DIRECTIVE, Tokens.K_EXTEND, Tokens.K_SCALAR, Tokens.K_ON, Tokens.K_FRAGMENT, Tokens.K_QUERY, Tokens.K_MUTATION, Tokens.K_SUBSCRIPTION, Tokens.K_VALUE, Tokens.K_TRUE, Tokens.K_FALSE, Tokens.K_NULL, Tokens.Name -> /*LL1AltBlock*/ {
                    this.state = 398
                    typeName()

                }Tokens.T__9 -> /*LL1AltBlock*/ {
                    this.state = 399
                    listType()

                }
                else -> throw NoViableAltException(this)
            }
            this.state = 403
            errorHandler.sync(this)
            _la = _input.LA(1)

            if (_la == Tokens.T__11) {
                this.state = 402
                required()

            }
        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class TypeNameContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.TypeName

        public fun anyName(): AnyNameContext = getRuleContext(AnyNameContext::class, 0)!!

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterTypeName(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitTypeName(this)
            }
        }
    }


    public fun typeName(): TypeNameContext {
        var _localctx = TypeNameContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 72, Rules.TypeName)

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 405
            anyName()

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class ListTypeContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.ListType

        public fun typeSpec(): TypeSpecContext = getRuleContext(TypeSpecContext::class, 0)!!

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterListType(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitListType(this)
            }
        }
    }


    public fun listType(): ListTypeContext {
        var _localctx = ListTypeContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 74, Rules.ListType)

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 407
            match(Tokens.T__9)

            this.state = 408
            typeSpec()

            this.state = 409
            match(Tokens.T__10)

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class RequiredContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.Required


        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterRequired(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitRequired(this)
            }
        }
    }


    public fun required(): RequiredContext {
        var _localctx = RequiredContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 76, Rules.Required)

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 411
            match(Tokens.T__11)

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class DefaultValueContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.DefaultValue

        public fun value(): ValueContext = getRuleContext(ValueContext::class, 0)!!

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterDefaultValue(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitDefaultValue(this)
            }
        }
    }


    public fun defaultValue(): DefaultValueContext {
        var _localctx = DefaultValueContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 78, Rules.DefaultValue)

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 413
            match(Tokens.T__8)

            this.state = 414
            value()

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class AnyNameContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.AnyName

        public fun nameTokens(): NameTokensContext? = getRuleContext(NameTokensContext::class, 0)
        public fun K_TRUE(): TerminalNode? = getToken(Tokens.K_TRUE, 0)
        public fun K_FALSE(): TerminalNode? = getToken(Tokens.K_FALSE, 0)
        public fun K_NULL(): TerminalNode? = getToken(Tokens.K_NULL, 0)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterAnyName(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitAnyName(this)
            }
        }
    }


    public fun anyName(): AnyNameContext {
        var _localctx = AnyNameContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 80, Rules.AnyName)

        try {
            this.state = 420
            errorHandler.sync(this)

            when (_input.LA(1)) {
                Tokens.EXECUTABLE_DIRECTIVE_LOCATION, Tokens.TYPE_SYSTEM_DIRECTIVE_LOCATION, Tokens.K_TYPE, Tokens.K_IMPLEMENTS, Tokens.K_INTERFACE, Tokens.K_SCHEMA, Tokens.K_ENUM, Tokens.K_UNION, Tokens.K_INPUT, Tokens.K_DIRECTIVE, Tokens.K_EXTEND, Tokens.K_SCALAR, Tokens.K_ON, Tokens.K_FRAGMENT, Tokens.K_QUERY, Tokens.K_MUTATION, Tokens.K_SUBSCRIPTION, Tokens.K_VALUE, Tokens.Name -> /*LL1AltBlock*/ {
                    enterOuterAlt(_localctx, 1)
                    this.state = 416
                    nameTokens()

                }Tokens.K_TRUE -> /*LL1AltBlock*/ {
                    enterOuterAlt(_localctx, 2)
                    this.state = 417
                    match(Tokens.K_TRUE)

                }Tokens.K_FALSE -> /*LL1AltBlock*/ {
                    enterOuterAlt(_localctx, 3)
                    this.state = 418
                    match(Tokens.K_FALSE)

                }Tokens.K_NULL -> /*LL1AltBlock*/ {
                    enterOuterAlt(_localctx, 4)
                    this.state = 419
                    match(Tokens.K_NULL)

                }
                else -> throw NoViableAltException(this)
            }
        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class NameTokensContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.NameTokens

        public fun Name(): TerminalNode? = getToken(Tokens.Name, 0)
        public fun EXECUTABLE_DIRECTIVE_LOCATION(): TerminalNode? = getToken(Tokens.EXECUTABLE_DIRECTIVE_LOCATION, 0)
        public fun TYPE_SYSTEM_DIRECTIVE_LOCATION(): TerminalNode? = getToken(Tokens.TYPE_SYSTEM_DIRECTIVE_LOCATION, 0)
        public fun K_TYPE(): TerminalNode? = getToken(Tokens.K_TYPE, 0)
        public fun K_IMPLEMENTS(): TerminalNode? = getToken(Tokens.K_IMPLEMENTS, 0)
        public fun K_INTERFACE(): TerminalNode? = getToken(Tokens.K_INTERFACE, 0)
        public fun K_SCHEMA(): TerminalNode? = getToken(Tokens.K_SCHEMA, 0)
        public fun K_ENUM(): TerminalNode? = getToken(Tokens.K_ENUM, 0)
        public fun K_UNION(): TerminalNode? = getToken(Tokens.K_UNION, 0)
        public fun K_INPUT(): TerminalNode? = getToken(Tokens.K_INPUT, 0)
        public fun K_DIRECTIVE(): TerminalNode? = getToken(Tokens.K_DIRECTIVE, 0)
        public fun K_EXTEND(): TerminalNode? = getToken(Tokens.K_EXTEND, 0)
        public fun K_SCALAR(): TerminalNode? = getToken(Tokens.K_SCALAR, 0)
        public fun K_ON(): TerminalNode? = getToken(Tokens.K_ON, 0)
        public fun K_FRAGMENT(): TerminalNode? = getToken(Tokens.K_FRAGMENT, 0)
        public fun K_QUERY(): TerminalNode? = getToken(Tokens.K_QUERY, 0)
        public fun K_MUTATION(): TerminalNode? = getToken(Tokens.K_MUTATION, 0)
        public fun K_SUBSCRIPTION(): TerminalNode? = getToken(Tokens.K_SUBSCRIPTION, 0)
        public fun K_VALUE(): TerminalNode? = getToken(Tokens.K_VALUE, 0)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterNameTokens(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitNameTokens(this)
            }
        }
    }


    public fun nameTokens(): NameTokensContext {
        var _localctx = NameTokensContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 82, Rules.NameTokens)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 422
            _la = _input.LA(1)

            if (!((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and 19327344640L) != 0L))) {
                errorHandler.recoverInline(this)
            }
            else {
                if (_input.LA(1) == Tokens.EOF) {
                    isMatchedEOF = true
                }

                errorHandler.reportMatch(this)
                consume()
            }
        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class BooleanValueContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.BooleanValue

        public fun K_TRUE(): TerminalNode? = getToken(Tokens.K_TRUE, 0)
        public fun K_FALSE(): TerminalNode? = getToken(Tokens.K_FALSE, 0)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterBooleanValue(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitBooleanValue(this)
            }
        }
    }


    public fun booleanValue(): BooleanValueContext {
        var _localctx = BooleanValueContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 84, Rules.BooleanValue)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 424
            _la = _input.LA(1)

            if (!(_la == Tokens.K_TRUE || _la == Tokens.K_FALSE)) {
                errorHandler.recoverInline(this)
            }
            else {
                if (_input.LA(1) == Tokens.EOF) {
                    isMatchedEOF = true
                }

                errorHandler.reportMatch(this)
                consume()
            }
        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class ValueContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.Value

        public fun IntValue(): TerminalNode? = getToken(Tokens.IntValue, 0)
        public fun FloatValue(): TerminalNode? = getToken(Tokens.FloatValue, 0)
        public fun StringValue(): TerminalNode? = getToken(Tokens.StringValue, 0)
        public fun BlockStringValue(): TerminalNode? = getToken(Tokens.BlockStringValue, 0)
        public fun booleanValue(): BooleanValueContext? = getRuleContext(BooleanValueContext::class, 0)
        public fun nullValue(): NullValueContext? = getRuleContext(NullValueContext::class, 0)
        public fun enumValue(): EnumValueContext? = getRuleContext(EnumValueContext::class, 0)
        public fun arrayValue(): ArrayValueContext? = getRuleContext(ArrayValueContext::class, 0)
        public fun objectValue(): ObjectValueContext? = getRuleContext(ObjectValueContext::class, 0)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterValue(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitValue(this)
            }
        }
    }


    public fun value(): ValueContext {
        var _localctx = ValueContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 86, Rules.Value)

        try {
            this.state = 435
            errorHandler.sync(this)

            when (_input.LA(1)) {
                Tokens.IntValue -> /*LL1AltBlock*/ {
                    enterOuterAlt(_localctx, 1)
                    this.state = 426
                    match(Tokens.IntValue)

                }Tokens.FloatValue -> /*LL1AltBlock*/ {
                    enterOuterAlt(_localctx, 2)
                    this.state = 427
                    match(Tokens.FloatValue)

                }Tokens.StringValue -> /*LL1AltBlock*/ {
                    enterOuterAlt(_localctx, 3)
                    this.state = 428
                    match(Tokens.StringValue)

                }Tokens.BlockStringValue -> /*LL1AltBlock*/ {
                    enterOuterAlt(_localctx, 4)
                    this.state = 429
                    match(Tokens.BlockStringValue)

                }Tokens.K_TRUE, Tokens.K_FALSE -> /*LL1AltBlock*/ {
                    enterOuterAlt(_localctx, 5)
                    this.state = 430
                    booleanValue()

                }Tokens.K_NULL -> /*LL1AltBlock*/ {
                    enterOuterAlt(_localctx, 6)
                    this.state = 431
                    nullValue()

                }Tokens.EXECUTABLE_DIRECTIVE_LOCATION, Tokens.TYPE_SYSTEM_DIRECTIVE_LOCATION, Tokens.K_TYPE, Tokens.K_IMPLEMENTS, Tokens.K_INTERFACE, Tokens.K_SCHEMA, Tokens.K_ENUM, Tokens.K_UNION, Tokens.K_INPUT, Tokens.K_DIRECTIVE, Tokens.K_EXTEND, Tokens.K_SCALAR, Tokens.K_ON, Tokens.K_FRAGMENT, Tokens.K_QUERY, Tokens.K_MUTATION, Tokens.K_SUBSCRIPTION, Tokens.K_VALUE, Tokens.Name -> /*LL1AltBlock*/ {
                    enterOuterAlt(_localctx, 7)
                    this.state = 432
                    enumValue()

                }Tokens.T__9 -> /*LL1AltBlock*/ {
                    enterOuterAlt(_localctx, 8)
                    this.state = 433
                    arrayValue()

                }Tokens.T__0 -> /*LL1AltBlock*/ {
                    enterOuterAlt(_localctx, 9)
                    this.state = 434
                    objectValue()

                }
                else -> throw NoViableAltException(this)
            }
        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class EnumValueContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.EnumValue

        public fun nameTokens(): NameTokensContext = getRuleContext(NameTokensContext::class, 0)!!

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterEnumValue(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitEnumValue(this)
            }
        }
    }


    public fun enumValue(): EnumValueContext {
        var _localctx = EnumValueContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 88, Rules.EnumValue)

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 437
            nameTokens()

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class ArrayValueContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.ArrayValue

        public fun value(): List<ValueContext> = getRuleContexts(ValueContext::class)
        public fun value(i: Int): ValueContext? = getRuleContext(ValueContext::class, i)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterArrayValue(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitArrayValue(this)
            }
        }
    }


    public fun arrayValue(): ArrayValueContext {
        var _localctx = ArrayValueContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 90, Rules.ArrayValue)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 439
            match(Tokens.T__9)

            this.state = 443
            errorHandler.sync(this)
            _la = _input.LA(1)

            while ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and 13331578479618L) != 0L)) {
                this.state = 440
                value()

                this.state = 445
                errorHandler.sync(this)
                _la = _input.LA(1)
            }
            this.state = 446
            match(Tokens.T__10)

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class ObjectValueContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.ObjectValue

        public fun objectField(): List<ObjectFieldContext> = getRuleContexts(ObjectFieldContext::class)
        public fun objectField(i: Int): ObjectFieldContext? = getRuleContext(ObjectFieldContext::class, i)

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterObjectValue(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitObjectValue(this)
            }
        }
    }


    public fun objectValue(): ObjectValueContext {
        var _localctx = ObjectValueContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 92, Rules.ObjectValue)
        var _la: Int

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 448
            match(Tokens.T__0)

            this.state = 452
            errorHandler.sync(this)
            _la = _input.LA(1)

            while ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and 34359730176L) != 0L)) {
                this.state = 449
                objectField()

                this.state = 454
                errorHandler.sync(this)
                _la = _input.LA(1)
            }
            this.state = 455
            match(Tokens.T__1)

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class ObjectFieldContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.ObjectField

        public fun anyName(): AnyNameContext = getRuleContext(AnyNameContext::class, 0)!!
        public fun value(): ValueContext = getRuleContext(ValueContext::class, 0)!!

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterObjectField(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitObjectField(this)
            }
        }
    }


    public fun objectField(): ObjectFieldContext {
        var _localctx = ObjectFieldContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 94, Rules.ObjectField)

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 457
            anyName()

            this.state = 458
            match(Tokens.T__2)

            this.state = 459
            value()

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }

    public open class NullValueContext : ParserRuleContext {
        override val ruleIndex: Int = Rules.NullValue

        public fun K_NULL(): TerminalNode = getToken(Tokens.K_NULL, 0)!!

        public constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState) {
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.enterNullValue(this)
            }
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is GraphQLListener) {
                listener.exitNullValue(this)
            }
        }
    }


    public fun nullValue(): NullValueContext {
        var _localctx = NullValueContext(context, state)
        var _token: Token?
        var _ctx: RuleContext?

        enterRule(_localctx, 96, Rules.NullValue)

        try {
            enterOuterAlt(_localctx, 1)
            this.state = 461
            match(Tokens.K_NULL)

        }
        catch (re: RecognitionException) {
            _localctx.exception = re
            errorHandler.reportError(this, re)
            errorHandler.recover(this, re)
        }
        finally {
            exitRule()
        }

        return _localctx
    }
}
