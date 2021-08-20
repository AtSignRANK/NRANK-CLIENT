package io.github.rank.mod.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.rank.mod.PerspectiveModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GamingSettingsScreen extends Screen {
    private final Screen gamingScreen;

    public GamingSettingsScreen(Screen parentScreen) {
        super(Text.of("rankclient.gamingmodsettings"));
        this.gamingScreen = parentScreen;
    }

    @Override
    public void init() {
        super.init();

        this.addDrawableChild(new ButtonWidget(50, 30, 100, 20, getTextBoolean("Perspective",
                AutoConfig.getConfigHolder(PerspectiveModConfig.class).getConfig().main.holdMode), (button) ->
                AutoConfig.getConfigHolder(PerspectiveModConfig.class).getConfig().main.holdMode =
                        turnBoolean(AutoConfig.getConfigHolder(PerspectiveModConfig.class).getConfig().main.holdMode,"Perspective", button)));
    }

    @Override
    public void renderBackground(MatrixStack matrices) {
        super.renderBackground(matrices);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, new Identifier("rankclient", "textures/gui/modsettings_background.png"));
        int i = (this.width - 248) / 2;
        int j = (this.height - 166) / 2;
        this.drawTexture(matrices, i, j, 0, 0, 248, 166);
    }

    @Override
    public boolean shouldCloseOnEsc() { return true; }

    @Override
    public void onClose() { MinecraftClient.getInstance().setScreen(gamingScreen); }

    private int boolInt(boolean bool) { if (bool) return 1; return 0; }

    private boolean turnBoolean(boolean bool, String text, ButtonWidget buttonWidget) {
        buttonWidget.setMessage(getTextBoolean(text, !bool));
        return !bool;
    }

    private Text getTextBoolean(String text, boolean bool) {
        String[] boolText = new String[]{ "OFF", "ON" };
        return Text.of(text + " : " + boolText[boolInt(bool)]);
    }
}
