package io.github.rank.mod;

import io.github.rank.mod.screens.GamingSettingsScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public enum GamingSettings {
    INSTANCE;

    private KeyBinding gamingSettingKey;

    public void initialize() {
        gamingSettingKey = new KeyBinding("Gaming Settings", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, MainClient.MOD_NAME);

        KeyBindingHelper.registerKeyBinding(gamingSettingKey);

        ClientTickEvents.START_CLIENT_TICK.register(e -> {
            if (MinecraftClient.getInstance().player != null) {
                if (gamingSettingKey.isPressed()) {
                    GamingSettingsScreen screen = new GamingSettingsScreen(MinecraftClient.getInstance().currentScreen);

                    MinecraftClient.getInstance().setScreen(screen);
                }
            }
        });
    }

    public KeyBinding getGamingSettingKey() { return gamingSettingKey; }
}
