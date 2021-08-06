package io.github.rank.mod.screens;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class ModSettingsScreen extends Screen {
    private final Screen mainScreen;
    private boolean isSaved = true;

    public boolean perspectiveHold = false;

    public ModSettingsScreen(Screen parentScreen) { super(Text.of("rankclient.modsettings")); this.mainScreen = parentScreen; }

    @Override
    public void init() {
        super.init();

        ButtonWidget perspective = new ButtonWidget(80, 20, 80, 20, getTextBoolean("Perspective",
                perspectiveHold), (button) -> perspectiveHold = turnBoolean(perspectiveHold, "Perspective", button));
        addDrawableChild(perspective);



        ButtonWidget save = new ButtonWidget(this.width / 2 + 10, this.height - 40, 80, 20, Text.of("Save"), (button) ->
                isSaved = true);
        addDrawableChild(save);
    }

    @Override
    public void onClose() { assert this.client != null; this.client.setScreen(mainScreen); }

    @Override
    public boolean shouldCloseOnEsc() { return isSaved; }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) { renderBackground(matrices); super.render(matrices, mouseX, mouseY, delta); }

    private int boolInt(boolean bool) { if (bool) return 1; return 0; }

    private boolean turnBoolean(boolean bool, String text, ButtonWidget buttonWidget) {
        isSaved = false;
        buttonWidget.setMessage(getTextBoolean(text, !bool));
        return !bool;
    }

    private Text getTextBoolean(String text, boolean bool) {
        String[] boolText = new String[]{ "OFF", "ON" };
        return Text.of(text + " : " + boolText[boolInt(bool)]);
    }
}
