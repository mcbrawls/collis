package net.mcbrawls.collis.test

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.mcbrawls.collis.Colli.Companion.colli
import net.mcbrawls.collis.ColliEntity
import net.minecraft.command.argument.Vec2ArgumentType
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource

object ColliCommand {
    const val SIZE = "size"

    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        dispatcher.register(
            CommandManager.literal("colli")
                .then(
                    CommandManager.argument(SIZE, Vec2ArgumentType.vec2(false))
                        .executes(::execute)
                )
        )
    }

    fun execute(context: CommandContext<ServerCommandSource>): Int {
        val source = context.source
        val world = source.world
        val pos = source.position

        val size = Vec2ArgumentType.getVec2(context, SIZE)

        val colli = colli(size) {
            callbacks {
                onEnter { colli, entity ->
                    val name = entity.nameForScoreboard
                    println("Enter: $name")
                }

                onTick { colli, entity ->
                    val name = entity.nameForScoreboard
                    println("Tick: $name")
                }

                onLeave { colli, id, entity ->
                    val name = entity?.nameForScoreboard
                    println("Leave: $name, $id")
                }
            }
        }
        if (ColliEntity.create(world, pos, colli) != null) {
            return 1
        }

        return 0
    }
}
