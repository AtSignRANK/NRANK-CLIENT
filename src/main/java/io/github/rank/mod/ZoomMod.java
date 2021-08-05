package io.github.rank.mod;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import org.lwjgl.glfw.GLFW;

public enum ZoomMod {
    INSTANCE;

    public static final MinecraftClient MC = MinecraftClient.getInstance();

    private KeyBinding zoomKey;
    private final double defaultLevel = 3;
    private Double currentLevel;
    private Double defaultMouseSensitivity;

    public void initialize() {
        zoomKey = new KeyBinding("Zoom", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, MainClient.MOD_NAME);

        KeyBindingHelper.registerKeyBinding(zoomKey);
    }

    public double changeFovBasedOnZoom(double fov) {
        GameOptions gameOptions = MC.options;

        if(currentLevel == null) currentLevel = defaultLevel;

        if(!zoomKey.isPressed()) {
            currentLevel = defaultLevel;

            if(defaultMouseSensitivity != null) {
                gameOptions.mouseSensitivity = defaultMouseSensitivity;
                defaultMouseSensitivity = null;
            }

            return fov;
        }

        assert MC.player != null;
        MC.player.sendMessage(Text.of("§7Zoom : " + (int) (currentLevel * 100) + "%"), true);

        if(defaultMouseSensitivity == null) defaultMouseSensitivity = gameOptions.mouseSensitivity;

        gameOptions.mouseSensitivity = defaultMouseSensitivity * (fov / currentLevel / fov);

        return fov / currentLevel;
    }

    public void onMouseScroll(double amount) {
        if(!zoomKey.isPressed()) return;

        if(currentLevel == null) currentLevel = defaultLevel;

        if(amount > 0) currentLevel *= 10.0 / 9;
        else if(amount < 0) currentLevel *= 0.9;

        final int zoomMaximum = 100;
        currentLevel = MathHelper.clamp(currentLevel, 1, zoomMaximum);
    }

    public KeyBinding getZoomKey() { return zoomKey; }
}
