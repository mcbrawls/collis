package net.mcbrawls.collis.callback

import net.mcbrawls.collis.Colli
import net.minecraft.entity.Entity

fun interface ColliLeaveCallback {
    /**
     * Called when an entity leaves the collider.
     */
    fun onLeave(
        colli: Colli,

        /**
         * The id of the entity.
         */
        id: Int,

        /**
         * The entity that left the collider. If not present in the world, will be null.
         */
        entity: Entity?
    )
}
