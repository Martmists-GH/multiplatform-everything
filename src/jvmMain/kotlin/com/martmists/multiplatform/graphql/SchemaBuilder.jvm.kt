package com.martmists.multiplatform.graphql

import com.martmists.multiplatform.graphql.SchemaBuilder.TypeBuilder
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.KVisibility
import kotlin.reflect.typeOf

inline fun <reified T : Any> SchemaBuilder.fullType(noinline block: TypeBuilder<T>.() -> Unit = {}) = fullType(typeOf<T>(), T::class, block)
fun <T : Any> SchemaBuilder.fullType(type: KType, klass: KClass<T>, block: TypeBuilder<T>.() -> Unit = {}) {
    type(type) {
        for (member in klass.members) {
            if (member.visibility == KVisibility.PUBLIC) {
                property(member.name, member.returnType) {
                    if (member.parameters.isEmpty()) {
                        resolver { member.call(this) }
                    } else {
                        val thisParam = member.parameters.first()
                        val params = member.parameters.drop(1).associateWith { argument<Any?>(it.name!!, it.type) }
                        resolver { ctx ->
                            val args = params.mapValues { (_, v)-> ctx.v() }.toMutableMap()
                            args[thisParam] = this
                            member.callBy(args)
                        }
                    }
                }
            }
        }

        // Allow overriding any props defined above
        block()
    }
}
