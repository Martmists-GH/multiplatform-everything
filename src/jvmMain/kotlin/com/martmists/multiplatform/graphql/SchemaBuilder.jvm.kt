package com.martmists.multiplatform.graphql

import kotlin.reflect.*
import kotlin.reflect.full.declaredMembers

inline fun <reified T : Any> SchemaBuilder.fullType(noinline block: SchemaBuilder.TypeBuilder<T>.() -> Unit = {}) = fullType(typeOf<T>(), T::class, block)
fun <T : Any> SchemaBuilder.fullType(type: KType, klass: KClass<T>, block: SchemaBuilder.TypeBuilder<T>.() -> Unit = {}) {
    type<Any>(type) {
        @Suppress("UNCHECKED_CAST")
        this as SchemaBuilder.TypeBuilder<T>

        for (member in klass.members.filterIsInstance<KProperty<*>>()) {
            if (member.visibility == KVisibility.PUBLIC) {
                property<Any?>(member.name, member.returnType) {
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

inline fun <reified T : Any> SchemaBuilder.shallowType(noinline block: SchemaBuilder.TypeBuilder<T>.() -> Unit = {}) = shallowType(typeOf<T>(), T::class, block)
fun <T : Any> SchemaBuilder.shallowType(type: KType, klass: KClass<T>, block: SchemaBuilder.TypeBuilder<T>.() -> Unit = {}) {
    type<Any>(type) {
        @Suppress("UNCHECKED_CAST")
        this as SchemaBuilder.TypeBuilder<T>

        for (member in klass.declaredMembers.filterIsInstance<KProperty<*>>()) {
            if (member.visibility == KVisibility.PUBLIC) {
                property<Any?>(member.name, member.returnType) {
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

inline fun <reified T : Any> SchemaBuilder.fullInterfaceType(noinline block: SchemaBuilder.InterfaceTypeBuilder<T>.() -> Unit) = fullInterfaceType(typeOf<T>(), T::class, block)
fun <T : Any> SchemaBuilder.fullInterfaceType(type: KType, klass: KClass<T>, block: SchemaBuilder.InterfaceTypeBuilder<T>.() -> Unit) {
    interfaceType<Any>(type) {
        @Suppress("UNCHECKED_CAST")
        this as SchemaBuilder.InterfaceTypeBuilder<T>

        for (member in klass.members.filterIsInstance<KProperty<*>>()) {
            if (member.visibility == KVisibility.PUBLIC) {
                property<Any?>(member.name, member.returnType) {
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
