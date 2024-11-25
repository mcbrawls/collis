package net.mcbrawls.collis.test

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback

class CollisTest : ModInitializer {
    override fun onInitialize() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            ColliCommand.register(dispatcher)
        }
    }
}
