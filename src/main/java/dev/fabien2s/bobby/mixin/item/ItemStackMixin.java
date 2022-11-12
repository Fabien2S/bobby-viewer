package dev.fabien2s.bobby.mixin.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow
    public abstract boolean hasTag();

    @Shadow
    private @Nullable CompoundTag tag;

    @Inject(method = "getTooltipLines", at = @At("RETURN"))
    private void getTooltipLines(@Nullable Player player, TooltipFlag tooltipFlag, CallbackInfoReturnable<List<Component>> cir) {
        List<Component> list = cir.getReturnValue();

        if (tooltipFlag.isAdvanced() && hasTag()) {
            Component nbtComponent = NbtUtils.toPrettyComponent(this.tag);
            list.add(nbtComponent);
        }
    }

}
