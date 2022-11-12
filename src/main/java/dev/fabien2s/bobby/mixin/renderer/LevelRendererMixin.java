package dev.fabien2s.bobby.mixin.renderer;

import dev.fabien2s.bobby.util.EntityRendererPredicate;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {

    @Redirect(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;isDetached()Z"))
    private boolean renderLevel_isDetached_shouldRenderCameraEntity(Camera camera) {
        return camera.isDetached() || (camera.getEntity() instanceof LivingEntity livingEntity && !EntityRendererPredicate.skipCustomRendering(livingEntity));
    }

}
