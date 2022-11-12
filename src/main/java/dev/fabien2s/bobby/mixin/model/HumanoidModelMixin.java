package dev.fabien2s.bobby.mixin.model;

import dev.fabien2s.bobby.util.EntityRendererPredicate;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin<T extends LivingEntity> {

    @Shadow
    protected abstract ModelPart getArm(HumanoidArm humanoidArm);

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("RETURN"))
    private void setupAnim(T player, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (EntityRendererPredicate.skipCustomRendering(player)) return;

        float playerRotation = player.getXRot();
        float rotationOffset = Mth.clamp(playerRotation, -45, 15f) * Mth.DEG_TO_RAD;

        HumanoidArm mainArm = player.getMainArm();
        if (player.hasItemInSlot(EquipmentSlot.MAINHAND)) {
            ModelPart armModel = getArm(mainArm);
            armModel.xRot += rotationOffset;
        }

        if (player.hasItemInSlot(EquipmentSlot.OFFHAND)) {
            HumanoidArm offArm = mainArm.getOpposite();
            ModelPart armModel = getArm(offArm);
            armModel.xRot += rotationOffset;
        }
    }

}
