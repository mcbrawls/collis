package net.mcbrawls.collis.callback

import net.mcbrawls.collis.Colli
import net.minecraft.entity.Entity

fun interface ColliTickCallback {
    /**
     * Called every tick when an entity is inside the collider.
     */
    fun onTick(colli: Colli, entity: Entity)
}
