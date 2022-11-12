package dev.fabien2s.bobby.util;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

public class EntityRendererPredicate {

    public static boolean skipCustomRenderingInput;

    private EntityRendererPredicate() {
    }

    public static boolean skipCustomRendering(LivingEntity entity) {
        // FIXME Should we support other entities?
        if (!(entity instanceof Player)) {
            return true;
        }

        if (EntityRendererPredicate.skipCustomRenderingInput) {
            return true;
        }

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.cameraEntity != entity) {
            return true;
        }

        Camera mainCamera = minecraft.gameRenderer.getMainCamera();
        if (mainCamera.isDetached()) {
            return true;
        }

        Pose pose = entity.getPose();
        return skipEntityPose(pose);
    }

    private static boolean skipEntityPose(Pose pose) {
        return pose != Pose.STANDING && pose != Pose.CROUCHING;
    }

}
