package dev.fabien2s.bobby.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.commands.arguments.SlotArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TextComponentTagVisitor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.item.ItemStack;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public final class InfoCommand {

    private static final TextComponentTagVisitor TAG_VISITOR = new TextComponentTagVisitor("\t", 0);

    private InfoCommand() {
    }

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("info")
                .then(argument("slot", SlotArgument.slot())
                        .executes(context -> {
                            FabricClientCommandSource source = context.getSource();


                            int slot = context.getArgument("slot", Integer.class);
                            Entity entity = source.getEntity();
                            SlotAccess slotAccess = entity.getSlot(slot);

                            ItemStack itemStack = slotAccess.get();
                            if (!itemStack.hasTag()) {
                                source.sendError(Component.literal("Item does not have nbt data"));
                            }

                            CompoundTag tag = itemStack.getTag();
                            Component nbtComponent = TAG_VISITOR.visit(tag);
                            source.sendFeedback(Component.literal("Item has the following nbt: ").append(nbtComponent));
                            return Command.SINGLE_SUCCESS;
                        })
                )
        );
    }

}
