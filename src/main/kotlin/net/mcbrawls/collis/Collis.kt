package net.mcbrawls.collis

import eu.pb4.polymer.core.api.entity.PolymerEntityUtils
import net.fabricmc.api.ModInitializer
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier

object Collis : ModInitializer {
    val ENTITY_TYPE: EntityType<ColliEntity> = registerEntity(
        "colli",
        EntityType.Builder.create(::ColliEntity, SpawnGroup.MISC)
            .dimensions(0.0f, 0.0f)
            .maxTrackingRange(2)
            .trackingTickInterval(10)
            .disableSaving()
            .disableSummon()
    )

    override fun onInitialize() {
    }

    private fun <E : Entity> registerEntity(id: String, builder: EntityType.Builder<E>): EntityType<E> {
        val key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of("brawls", id))
        val type = builder.build(key)
        PolymerEntityUtils.registerType(type)
        return Registry.register(Registries.ENTITY_TYPE, key, type)
    }
}
