package dev.fabien2s.bobby.mixin.renderer;

import dev.fabien2s.bobby.util.EntityRendererPredicate;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Redirect(method = "renderLevel", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/GameRenderer;renderHand:Z", opcode = Opcodes.GETFIELD))
    private boolean renderLevel_renderHand(GameRenderer gameRenderer) {
        Camera mainCamera = gameRenderer.getMainCamera();
        Entity entity = mainCamera.getEntity();
        return entity instanceof LivingEntity livingEntity && EntityRendererPredicate.skipCustomRendering(livingEntity);
    }

}
