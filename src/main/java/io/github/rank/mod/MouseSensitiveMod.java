package io.github.rank.mod;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

public enum MouseSensitiveMod {
    INSTANCE;

    public static final MinecraftClient MC = MinecraftClient.getInstance();

    private KeyBinding sensitiveKey;

    public void initialize() {
        sensitiveKey = new KeyBinding("MouseSensitive", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Z, MainClient.MOD_NAME);

        KeyBindingHelper.registerKeyBinding(sensitiveKey);

        ClientTickEvents.START_CLIENT_TICK.register(e -> {
            if (MC.player != null) {
                if (sensitiveKey.isPressed()) scrollMouseSensitive(0.0);
            }
        });
    }

    private double getMouseSensitive() { return MC.options.mouseSensitivity; }
    private void setMouseSensitive(double sensitive) { MC.options.mouseSensitivity = sensitive; }

    public void scrollMouseSensitive(double amount) {
        double sensitive = getMouseSensitive();
        double times = 3;
        if (amount > 0) sensitive += times / 200;
        else if (amount < 0) sensitive -= times / 200;

        final int sensitiveMaximum = 1;
        sensitive = MathHelper.clamp(sensitive, 0, sensitiveMaximum);

        setMouseSensitive(sensitive);

        assert MC.player != null;
        MC.player.sendMessage(Text.of("§7Mouse Sensitive : " + (int) (sensitive * 2 * 100) + "%"), true);
    }

    public KeyBinding getSensitiveKey() { return sensitiveKey; }
}
