package net.mcbrawls.collis

import eu.pb4.polymer.core.api.entity.PolymerEntity
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnReason
import net.minecraft.entity.decoration.InteractionEntity
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import xyz.nucleoid.packettweaker.PacketContext

open class ColliEntity(
    colli: Colli,
    type: EntityType<*>,
    world: World
) : InteractionEntity(type, world), PolymerEntity {
    constructor(type: EntityType<*>, world: World) : this(Colli.EMPTY, type, world)

    var colli: Colli = colli
        set(value) {
            field = value

            val size = value.size
            setInteractionWidth(size.x)
            setInteractionHeight(size.y)
        }

    override fun tick() {
        super.tick()
        colli.collisionTick(this, world, boundingBox)
    }

    override fun getPolymerEntityType(context: PacketContext): EntityType<*> {
        return POLYMER_TYPE
    }

    companion object {
        val POLYMER_TYPE: EntityType<*> = if (FabricLoader.getInstance().isDevelopmentEnvironment) EntityType.INTERACTION else EntityType.MARKER

        fun create(world: World, pos: Vec3d, colli: Colli): ColliEntity? {
            return Collis.ENTITY_TYPE.create(world, SpawnReason.COMMAND)?.also { colliEntity ->
                colliEntity.setPosition(pos)
                colliEntity.colli = colli
                world.spawnEntity(colliEntity)
            }
        }
    }
}
