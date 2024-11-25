package net.mcbrawls.collis.callback

import net.mcbrawls.collis.Colli
import net.minecraft.entity.Entity

fun interface ColliEnterCallback {
    /**
     * Called when an entity enters the collider.
     */
    fun onEnter(colli: Colli, entity: Entity)
}
