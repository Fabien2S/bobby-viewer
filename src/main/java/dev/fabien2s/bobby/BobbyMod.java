package dev.fabien2s.bobby;

import dev.fabien2s.bobby.util.EntityRendererPredicate;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.ToggleKeyMapping;
import org.lwjgl.glfw.GLFW;

public class BobbyMod implements ModInitializer {

    private KeyMapping toggleViewModel;

    @Override
    public void onInitialize() {
        toggleViewModel = KeyBindingHelper.registerKeyBinding(new ToggleKeyMapping(
                "key.bobby.toggle_view_model",
                GLFW.GLFW_KEY_K,
                KeyMapping.CATEGORY_MISC,
                () -> true
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            EntityRendererPredicate.skipCustomRenderingInput = toggleViewModel.isDown();
        });
    }

}
