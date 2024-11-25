package net.mcbrawls.collis

import net.mcbrawls.collis.callback.ColliCallbackHandler
import net.mcbrawls.collis.callback.ColliEnterCallback
import net.mcbrawls.collis.callback.ColliLeaveCallback
import net.mcbrawls.collis.callback.ColliTickCallback
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityDimensions
import net.minecraft.util.math.Box
import net.minecraft.util.math.Vec2f
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

/**
 * An instance of a colli collider.
 */
open class Colli(
    /**
     * The size of the colli.
     */
    val size: Vec2f,
) {
    val dimensions: EntityDimensions = EntityDimensions.changing(size.x, size.y)

    private val callbackHandler: ColliCallbackHandler = ColliCallbackHandler()

    /**
     * The ids of the entities that collided with this collider in the last processed tick.
     */
    private val collidingEntities: MutableSet<Int> = mutableSetOf()

    /**
     * Builds the callback handler.
     */
    fun callbacks(builder: ColliCallbackHandler.() -> Unit) {
        callbackHandler.apply(builder)
    }

    /**
     * Creates a bounding box of the collider size at the given position.
     */
    fun getBoxAt(pos: Vec3d): Box {
        return dimensions.getBoxAt(pos)
    }

    open fun collisionTick(colliderEntity: Entity, world: World, boundingBox: Box) {
        val previousEntities = collidingEntities.toSet()
        collidingEntities.clear()

        val entities = world
            .getOtherEntities(colliderEntity, boundingBox)
            .associateBy(Entity::getId)

        entities.forEach { id, entity ->
            if (!previousEntities.contains(id)) {
                callbackHandler.getCallbacks(ColliEnterCallback::class).forEach { it.onEnter(this, entity) }
            }
        }

        entities.forEach(::onEntityCollision)

        previousEntities.forEach { id ->
            if (!entities.containsKey(id)) {
                val entity = world.getEntityById(id)
                callbackHandler.getCallbacks(ColliLeaveCallback::class).forEach { it.onLeave(this, id, entity) }
            }
        }
    }

    private fun onEntityCollision(id: Int, entity: Entity) {
        collidingEntities.add(id)
        callbackHandler.getCallbacks(ColliTickCallback::class).forEach { it.onTick(this, entity) }
    }

    companion object {
        val EMPTY = Colli(Vec2f.ZERO)

        inline fun colli(size: Vec2f, builder: Colli.() -> Unit): Colli {
            return Colli(size).apply(builder)
        }
    }
}
