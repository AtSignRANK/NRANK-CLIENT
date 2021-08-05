package io.github.rank.mod;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public enum PerspectiveMod {
    INSTANCE;

    private KeyBinding perspectiveKey;

    private boolean held = false;

    public boolean pressing = false;

    public float cameraPitch;
    public float cameraYaw;

    public void initialize() {
        AutoConfig.register(
                PerspectiveModConfig.class,
                PartitioningSerializer.wrap(JanksonConfigSerializer::new)
        );

        perspectiveKey = new KeyBinding("Perspective", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, MainClient.MOD_NAME);

        KeyBindingHelper.registerKeyBinding(perspectiveKey);

        ClientTickEvents.START_CLIENT_TICK.register(e -> {
            if (MinecraftClient.getInstance().player != null) {
                if (AutoConfig.getConfigHolder(PerspectiveModConfig.class).getConfig().main.holdMode) {
                    this.pressing = perspectiveKey.isPressed();

                    if (this.pressing && !this.held) {
                        this.held = true;
                        this.cameraPitch = MinecraftClient.getInstance().player.getPitch();
                        this.cameraYaw = MinecraftClient.getInstance().player.getYaw();
                        MinecraftClient.getInstance().options.setPerspective(Perspective.THIRD_PERSON_BACK);
                    }
                } else {
                    if (perspectiveKey.wasPressed()) {
                        this.pressing = !this.pressing;

                        this.cameraPitch = MinecraftClient.getInstance().player.getPitch();
                        this.cameraYaw = MinecraftClient.getInstance().player.getYaw();

                        MinecraftClient.getInstance().options.setPerspective(this.pressing ? Perspective.THIRD_PERSON_BACK : Perspective.FIRST_PERSON);
                    }
                }

                if (!this.pressing && this.held) {
                    this.held = false;
                    MinecraftClient.getInstance().options.setPerspective(Perspective.FIRST_PERSON);
                }

                if (this.pressing && MinecraftClient.getInstance().options.getPerspective() != Perspective.THIRD_PERSON_BACK) this.pressing = false;
            }
        });
    }

    public KeyBinding getPerspectiveKey() { return perspectiveKey; }}
