package net.mcbrawls.collis.callback

import kotlin.reflect.KClass

class ColliCallbackHandler {
    private val callbacks = mutableMapOf<KClass<*>, MutableList<Any>>()

    fun <T : Any> registerCallback(type: KClass<T>, callback: T) {
        callbacks.computeIfAbsent(type) { mutableListOf() }.add(callback)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getCallbacks(type: KClass<T>): List<T> {
        return callbacks[type]?.map { it as T } ?: emptyList()
    }

    fun onEnter(callback: ColliEnterCallback) {
        registerCallback(ColliEnterCallback::class, callback)
    }

    fun onLeave(callback: ColliLeaveCallback) {
        registerCallback(ColliLeaveCallback::class, callback)
    }

    fun onTick(callback: ColliTickCallback) {
        registerCallback(ColliTickCallback::class, callback)
    }
}
