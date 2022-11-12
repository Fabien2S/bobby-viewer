package dev.fabien2s.bobby.mixin.renderer;

import dev.fabien2s.bobby.util.EntityRendererPredicate;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> entityModel, float f) {
        super(context, entityModel, f);
    }

    @Inject(method = "getRenderOffset(Lnet/minecraft/client/player/AbstractClientPlayer;F)Lnet/minecraft/world/phys/Vec3;", at = @At("RETURN"), cancellable = true)
    private void getRenderOffset_adjustFirstPerson(AbstractClientPlayer player, float f, CallbackInfoReturnable<Vec3> cir) {
        if (EntityRendererPredicate.skipCustomRendering(player)) return;

        Vec3 baseOffset = cir.getReturnValue();

        float yaw = player.getViewYRot(f);
        Vec3 forward = Vec3.directionFromRotation(0, yaw);
        cir.setReturnValue(new Vec3(
                baseOffset.x + forward.x * -.333334f,
                baseOffset.y + forward.y * -.333334f,
                baseOffset.z + forward.z * -.333334f
        ));
    }

    @Inject(method = "setModelProperties", at = @At("RETURN"))
    private void setModelProperties(AbstractClientPlayer player, CallbackInfo ci) {
        if (EntityRendererPredicate.skipCustomRendering(player)) return;

        player.yBodyRot = player.yHeadRot;

        PlayerModel<AbstractClientPlayer> model = getModel();
        model.head.visible = false;
        model.hat.visible = false;
    }

}
